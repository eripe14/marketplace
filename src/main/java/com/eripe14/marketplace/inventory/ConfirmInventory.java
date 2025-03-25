package com.eripe14.marketplace.inventory;

import com.eripe14.marketplace.config.implementation.PluginConfig;
import com.eripe14.marketplace.inventory.item.ItemTransformer;
import com.eripe14.marketplace.marketplace.offer.Offer;
import com.eripe14.marketplace.notice.adventure.Legacy;
import com.eripe14.marketplace.scheduler.Scheduler;
import com.eternalcode.multification.shared.Formatter;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ConfirmInventory {

    private final Scheduler scheduler;
    private final PluginConfig.ConfirmInventoryConfig config;

    public ConfirmInventory(Scheduler scheduler, PluginConfig.ConfirmInventoryConfig config) {
        this.scheduler = scheduler;
        this.config = config;
    }

    public void openInventory(
            Player player,
            Offer offer,
            double price,
            Runnable confirmAction,
            Runnable cancelAction
    ) {
        this.scheduler.async(() -> {
            Gui gui = Gui.gui()
                    .title(Legacy.title(this.config.title))
                    .rows(this.config.rows)
                    .disableAllInteractions()
                    .create();

            if (this.config.fillEmptySlots) {
                gui.getFiller().fill(new GuiItem(ItemTransformer.build(this.config.fillerItem)));
            }

            gui.setItem(this.config.cancelItem.getSlot(), new GuiItem(
                    ItemTransformer.build(this.config.cancelItem),
                    event -> cancelAction.run()
            ));
            gui.setItem(this.config.confirmItem.getSlot(), new GuiItem(
                    ItemTransformer.build(this.config.confirmItem),
                    event -> confirmAction.run()
            ));

            Formatter formatter = new Formatter();
            formatter.register("{price}", price);
            formatter.register("{seller}", Bukkit.getOfflinePlayer(offer.getSellerUuid()).getName());

            gui.setItem(this.config.additionalItem.getSlot(), new GuiItem(
                    ItemTransformer.build(this.config.additionalItem, offer.getItem().getType(), formatter),
                    event -> {}
            ));

            this.scheduler.sync(() -> gui.open(player));
        });
    }
}