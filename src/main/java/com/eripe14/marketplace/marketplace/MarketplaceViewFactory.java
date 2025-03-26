package com.eripe14.marketplace.marketplace;

import com.eripe14.marketplace.config.implementation.PluginConfig;
import com.eripe14.marketplace.discord.DiscordWebhookService;
import com.eripe14.marketplace.economy.EconomyService;
import com.eripe14.marketplace.inventory.ConfirmInventory;
import com.eripe14.marketplace.inventory.item.ItemTransformer;
import com.eripe14.marketplace.inventory.item.impl.PreviousPageItem;
import com.eripe14.marketplace.inventory.item.impl.QuitInventoryItem;
import com.eripe14.marketplace.inventory.queue.InventoryQueueService;
import com.eripe14.marketplace.marketplace.offer.Offer;
import com.eripe14.marketplace.marketplace.offer.OfferItem;
import com.eripe14.marketplace.marketplace.offer.OfferService;
import com.eripe14.marketplace.notice.NoticeService;
import com.eripe14.marketplace.transaction.TransactionService;
import com.eripe14.marketplace.transaction.TransactionSource;
import com.eternalcode.multification.shared.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class MarketplaceViewFactory {

    private final ConfirmInventory confirmInventory;
    private final InventoryQueueService queueService;
    private final OfferService offerService;
    private final TransactionService transactionService;
    private final DiscordWebhookService discordWebhookService;
    private final EconomyService economyService;
    private final PluginConfig config;
    private final NoticeService noticeService;

    public MarketplaceViewFactory(
            ConfirmInventory confirmInventory,
            InventoryQueueService queueService,
            OfferService offerService,
            TransactionService transactionService,
            DiscordWebhookService discordWebhookService,
            EconomyService economyService,
            PluginConfig config,
            NoticeService noticeService
    ) {
        this.confirmInventory = confirmInventory;
        this.queueService = queueService;
        this.offerService = offerService;
        this.transactionService = transactionService;
        this.discordWebhookService = discordWebhookService;
        this.economyService = economyService;
        this.config = config;
        this.noticeService = noticeService;
    }

    public PagedGui.Builder<Item> getOffersView() {
        PluginConfig.MarketPlaceConfig inventoryConfig = this.config.marketPlaceConfig;
        Formatter empty = new Formatter();

        return PagedGui.items()
                .setStructure(inventoryConfig.structure.toArray(new String[0]))
                .addIngredient('O', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('X', ItemTransformer.transform(inventoryConfig.fillerItem))
                .addIngredient('P', new PreviousPageItem(inventoryConfig.previousPageItem))
                .addIngredient('Q', new QuitInventoryItem(inventoryConfig.quitItem, this.queueService))
                .addIngredient('N', new PreviousPageItem(inventoryConfig.nextPageItem));
    }

    public List<Item> getGuiContent(boolean blackMarket, Consumer<InventoryClickEvent> clickAction) {
        List<Item> items = new ArrayList<>();
        List<Offer> offers = this.offerService.getOffers().stream()
                .filter(offer -> blackMarket == offer.isBlackMarket())
                .sorted(Comparator.comparing(Offer::getCreatedAt).reversed())
                .toList();

        for (Offer offer : offers) {
            ItemStack itemStack = offer.getItem();

            Formatter formatter = new Formatter();
            formatter.register("{price}", offer.getPrice());
            formatter.register("{seller}", Bukkit.getOfflinePlayer(offer.getSellerUuid()).getName());
            formatter.register("{item}", itemStack.getType().name().toLowerCase().replace("_", " "));

            OfferItem offerItem = new OfferItem(
                    this.config.marketPlaceConfig.offerItem,
                    offer,
                    formatter,
                    event -> {
                        Player player = (Player) event.getWhoClicked();

                        if (!this.economyService.hasEnoughMoney(player, offer.getPrice())) {
                            this.noticeService.create()
                                    .notice(messages -> messages.notEnoughMoney)
                                    .player(player.getUniqueId())
                                    .formatter(formatter)
                                    .sendAsync();
                            return;
                        }

                        this.confirmInventory.openInventory(
                                player,
                                offer,
                                offer.getPrice(),
                                this.confirmAction(player, offer, clickAction, event, formatter),
                                player::closeInventory
                        );
                    }
            );

            items.add(offerItem);
        }

        return items;
    }

    private Runnable confirmAction(
            Player player,
            Offer offer,
            Consumer<InventoryClickEvent> clickAction,
            InventoryClickEvent event,
            Formatter formatter
    ) {
        return () -> {
            formatter.register("{buyer}", player.getName());
            player.closeInventory();

            this.giveItemToPlayer(player, offer);
            this.processPayment(player, offer);
            this.discordWebhookService.prepareMessage(player, offer);
            this.offerService.remove(offer);
            this.transactionService.createTransaction(player, TransactionSource.MARKETPLACE, offer, offer.getPrice());

            this.noticeService.create()
                    .notice(messages -> messages.boughtItem)
                    .player(player.getUniqueId())
                    .formatter(formatter)
                    .sendAsync();

            formatter.register(
                    "{price}",
                    offer.isBlackMarket() ? offer.getPrice() * 4 : offer.getPrice()
            );

            this.noticeService.create()
                    .notice(messages -> messages.soldItem)
                    .player(offer.getSellerUuid())
                    .formatter(formatter)
                    .sendAsync();

            clickAction.accept(event);
        };
    }

    private void processPayment(Player player, Offer offer) {
        UUID sellerUuid = offer.getSellerUuid();
        OfflinePlayer seller = Bukkit.getOfflinePlayer(sellerUuid);

        // black market discount first price by 50% and seller has to get 2x profit of regular price
        this.economyService.withdrawMoney(player, offer.getPrice());
        this.economyService.depositMoney(
                seller,
                offer.isBlackMarket()
                        ? offer.getPrice() * 4
                        : offer.getPrice()
        );
    }

    private void giveItemToPlayer(Player player, Offer offer) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), offer.getItem());
            return;
        }

        player.getInventory().addItem(offer.getItem());
    }

}