package com.eripe14.marketplace.inventory.item;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public class InventoryItemSerializer implements ObjectSerializer<InventoryItem> {

    @Override
    public boolean supports(@NonNull Class<? super InventoryItem> type) {
        return InventoryItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull InventoryItem object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("customModelData", object.getCustomModelData());
        data.add("slot", object.getSlot());
        data.add("material", object.getMaterial());
        data.add("name", object.getName());
        data.add("lore", object.getLore());
        data.add("flags", object.getFlags());
    }

    @Override
    public InventoryItem deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new InventoryItem(
                data.get("customModelData", Integer.class),
                data.get("slot", Integer.class),
                data.get("material", Material.class),
                data.get("name", String.class),
                data.getAsList("lore", String.class),
                data.getAsList("flags", ItemFlag.class)
        );
    }
}