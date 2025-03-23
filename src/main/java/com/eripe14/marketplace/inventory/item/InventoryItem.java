package com.eripe14.marketplace.inventory.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.util.List;

@AllArgsConstructor
@Getter
public class InventoryItem {

    private final int customModelData;
    private final int slot;
    private final Material material;
    private final String name;
    private final List<String> lore;
    private final List<ItemFlag> flags;

}