package com.eripe14.marketplace.transaction;

import com.eripe14.marketplace.marketplace.offer.Offer;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Player buyer, TransactionSource transactionSource, Offer offer, double price) {
        Transaction transaction = this.transactionRepository.findOrCreateByPath(UUID.randomUUID());
        transaction.setBuyerUuid(buyer.getUniqueId());
        transaction.setBuyerName(buyer.getName());
        transaction.setSellerUuid(offer.getSellerUuid());
        transaction.setPrice(price);
        transaction.setTransactionDate(Instant.now());
        transaction.setItem(offer.getItem());
        transaction.setTransactionSource(transactionSource);
        transaction.save();

        return transaction;
    }

    public List<Transaction> getTransactionsByBuyer(Player buyer) {
        return this.transactionRepository.findTransactionsByBuyerName(buyer.getName());
    }
}