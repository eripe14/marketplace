package com.eripe14.marketplace.inventory.item.impl;

import com.eripe14.marketplace.inventory.item.InventoryItem;
import com.eripe14.marketplace.inventory.item.ItemTransformer;
import com.eripe14.marketplace.inventory.queue.InventoryQueueService;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class QuitInventoryItem extends AbstractItem {

    private final InventoryItem inventoryItem;
    private final InventoryQueueService queueService;

    public QuitInventoryItem(InventoryItem inventoryItem, InventoryQueueService queueService) {
        this.inventoryItem = inventoryItem;
        this.queueService = queueService;
    }

    @Override
    public ItemProvider getItemProvider() {
        return ItemTransformer.transformToItemBuilder(this.inventoryItem);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        this.queueService.openLatest(player);
    }
}