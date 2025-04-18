package com.eripe14.marketplace.marketplace.offer;

import com.eripe14.marketplace.inventory.item.InventoryItem;
import com.eripe14.marketplace.inventory.item.ItemTransformer;
import com.eternalcode.multification.shared.Formatter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.function.Consumer;

public class OfferItem extends AbstractItem {

    private final InventoryItem inventoryItem;
    private final Offer offer;
    private final Formatter formatter;
    private final Consumer<InventoryClickEvent> clickAction;

    public OfferItem(
            InventoryItem inventoryItem,
            Offer offer,
            Formatter formatter,
            Consumer<InventoryClickEvent> clickAction
    ) {
        this.inventoryItem = inventoryItem;
        this.offer = offer;
        this.formatter = formatter;
        this.clickAction = clickAction;
    }

    @Override
    public ItemProvider getItemProvider() {
        ItemStack item = this.offer.getItem();
        return ItemTransformer.transformToItemBuilder(
                this.inventoryItem,
                item.getType(),
                this.formatter
        ).setAmount(item.getAmount());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        this.clickAction.accept(inventoryClickEvent);
    }
}