package com.eripe14.marketplace.inventory.item;

import com.eripe14.marketplace.notice.adventure.Legacy;
import com.eripe14.marketplace.notice.adventure.LegacyColorProcessor;
import com.eternalcode.multification.shared.Formatter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.List;

public final class ItemTransformer {

    private static final Formatter EMPTY_FORMATTER = new Formatter();
    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

    private ItemTransformer() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static ItemStack build(InventoryItem inventoryItem, Material additionalMaterial, Formatter formatter) {
        String name = formatter.format(inventoryItem.getName());
        List<ComponentWrapper> lore = inventoryItem.getLore().stream()
                .map(formatter::format)
                .map(line -> (ComponentWrapper) new AdventureComponentWrapper(Legacy.RESET_ITALIC.append(MINI_MESSAGE.deserialize(line))))
                .toList();

        Material newMaterial = inventoryItem.getMaterial();
        if (additionalMaterial != Material.AIR) {
            newMaterial = additionalMaterial;
        }

        return new ItemBuilder(newMaterial)
                .setDisplayName(new AdventureComponentWrapper(Legacy.RESET_ITALIC.append(MINI_MESSAGE.deserialize(name))))
                .setLore(lore)
                .setItemFlags(inventoryItem.getFlags())
                .setCustomModelData(inventoryItem.getCustomModelData())
                .get();
    }

    public static ItemStack build(InventoryItem inventoryItem, Material additionalMaterial) {
        return build(inventoryItem, additionalMaterial, EMPTY_FORMATTER);
    }

    public static ItemStack build(InventoryItem inventoryItem) {
        return build(inventoryItem, Material.AIR, EMPTY_FORMATTER);
    }

    public static Item transform(InventoryItem inventoryItem, Material additionalMaterial, Formatter formatter) {
        ItemStack itemStack = build(inventoryItem, additionalMaterial, formatter);
        return new SimpleItem(itemStack);
    }

    public static Item transform(InventoryItem inventoryItem) {
        ItemStack itemStack = build(inventoryItem, Material.AIR, EMPTY_FORMATTER);
        return new SimpleItem(itemStack);
    }

    public static ItemBuilder transformToItemBuilder(InventoryItem item, Material additionalMaterial, Formatter formatter) {
        ItemStack build = build(item, additionalMaterial, formatter);
        return new ItemBuilder(build);
    }

    public static ItemBuilder transformToItemBuilder(InventoryItem item, Formatter formatter) {
        return transformToItemBuilder(item, Material.AIR, formatter);
    }

    public static ItemBuilder transformToItemBuilder(InventoryItem item) {
        return transformToItemBuilder(item, EMPTY_FORMATTER);
    }
}