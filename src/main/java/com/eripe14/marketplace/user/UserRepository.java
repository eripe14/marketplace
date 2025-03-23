package com.eripe14.marketplace.user;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@DocumentCollection(path = "users", keyLength = 36)
public interface UserRepository extends DocumentRepository<UUID, User> {

    default CompletableFuture<User> findOrCreate(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> this.findOrCreateByPath(uuid));
    }

}