package com.eripe14.marketplace.marketplace.offer;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import eu.okaeri.persistence.repository.annotation.DocumentIndex;
import eu.okaeri.persistence.repository.annotation.DocumentPath;
import org.bukkit.OfflinePlayer;

import java.util.UUID;
import java.util.stream.Stream;

@DocumentCollection(path = "offers", keyLength = 36, indexes = {
        @DocumentIndex(path = "seller-uuid", maxLength = 36),
})
public interface OfferRepository extends DocumentRepository<UUID, Offer> {

    @DocumentPath("seller-uuid")
    Stream<Offer> findBySeller(UUID sellerUuid);

    default Stream<Offer> findBySeller(OfflinePlayer seller) {
        return this.findBySeller(seller.getUniqueId());
    }

}