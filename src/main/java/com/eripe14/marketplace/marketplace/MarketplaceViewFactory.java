package com.eripe14.marketplace.marketplace;

import com.eripe14.marketplace.config.implementation.PluginConfig;
import com.eripe14.marketplace.inventory.item.ItemTransformer;
import com.eripe14.marketplace.inventory.item.impl.PreviousPageItem;
import com.eripe14.marketplace.inventory.item.impl.QuitInventoryItem;
import com.eripe14.marketplace.inventory.queue.InventoryQueueService;
import com.eripe14.marketplace.marketplace.offer.Offer;
import com.eripe14.marketplace.marketplace.offer.OfferItem;
import com.eripe14.marketplace.marketplace.offer.OfferService;
import com.eternalcode.multification.shared.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class MarketplaceViewFactory {

    private final InventoryQueueService queueService;
    private final OfferService offerService;
    private final PluginConfig config;

    public MarketplaceViewFactory(
            InventoryQueueService queueService,
            OfferService offerService,
            PluginConfig config
    ) {
        this.queueService = queueService;
        this.offerService = offerService;
        this.config = config;
    }

    public PagedGui.Builder<Item> getOffersView() {
        PluginConfig.MarketPlaceInventoryConfig inventoryConfig = this.config.marketPlaceInventory;
        Formatter empty = new Formatter();

        return PagedGui.items()
                .setStructure(inventoryConfig.structure.toArray(new String[0]))
                .addIngredient('O', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('X', ItemTransformer.transform(inventoryConfig.fillerItem))
                .addIngredient('S', ItemTransformer.transform(inventoryConfig.settingsItem))
                .addIngredient('P', new PreviousPageItem(inventoryConfig.previousPageItem))
                .addIngredient('Q', new QuitInventoryItem(inventoryConfig.quitItem, this.queueService))
                .addIngredient('N', new PreviousPageItem(inventoryConfig.nextPageItem));
    }

    public List<Item> getGuiContent(Consumer<InventoryClickEvent> clickAction) {
        List<Item> items = new ArrayList<>();
        List<Offer> offers = this.offerService.getOffers().stream()
                .sorted(Comparator.comparing(Offer::getCreatedAt).reversed())
                .toList();

        for (Offer offer : offers) {
            ItemStack itemStack = offer.getItem();

            Formatter formatter = new Formatter();
            formatter.register("{price}", offer.getPrice());
            formatter.register("{seller}", Bukkit.getOfflinePlayer(offer.getSellerUuid()).getName());

            OfferItem offerItem = new OfferItem(
                    this.config.marketPlaceInventory.offerItem,
                    itemStack.getType(),
                    formatter,
                    event -> {
                        event.getWhoClicked().sendMessage("You clicked on an offer!");
                        event.getWhoClicked().sendMessage("TODO: Implement offer click action (buying)");
                        clickAction.accept(event);
                    }
            );

            items.add(offerItem);
        }

        return items;
    }

}