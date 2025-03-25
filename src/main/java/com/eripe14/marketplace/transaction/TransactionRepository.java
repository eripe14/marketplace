package com.eripe14.marketplace.transaction;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import eu.okaeri.persistence.repository.annotation.DocumentIndex;
import eu.okaeri.persistence.repository.annotation.DocumentPath;

import java.util.List;
import java.util.UUID;

@DocumentCollection(path = "transactions", keyLength = 36, indexes = {
        @DocumentIndex(path = "buyer-name", maxLength = 36)
})
public interface TransactionRepository extends DocumentRepository<UUID, Transaction> {

    @DocumentPath("buyer-name")
    List<Transaction> findTransactionsByBuyerName(String buyerName);

}