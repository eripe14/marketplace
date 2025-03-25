package com.eripe14.marketplace.transaction;

import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class Transaction extends Document {

    @CustomKey("buyer-uuid")
    private UUID buyerUuid;

    @CustomKey("buyer-name")
    private String buyerName;

    @CustomKey("seller-uuid")
    private UUID sellerUuid;

    @CustomKey("price")
    private double price;

    @CustomKey("transaction-date")
    private Instant transactionDate;

    @CustomKey("item")
    private ItemStack item;

    @CustomKey("transaction-source")
    private TransactionSource transactionSource;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}