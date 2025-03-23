package com.eripe14.marketplace.marketplace.offer;

import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class Offer extends Document {

    @CustomKey("seller-uuid")
    private UUID sellerUuid;

    @CustomKey("item")
    private ItemStack item;

    @CustomKey("price")
    private double price;

    @CustomKey("sold")
    private boolean sold;

    @CustomKey("created-at")
    private Instant createdAt;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}