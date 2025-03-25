package com.eripe14.marketplace.marketplace.offer;

import eu.okaeri.tasker.core.Tasker;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OfferService {

    private final OfferRepository offerRepository;
    private final Tasker tasker;

    private final Map<UUID, Offer> offers = new ConcurrentHashMap<>();

    public OfferService(OfferRepository offerRepository, Tasker tasker) {
        this.offerRepository = offerRepository;
        this.tasker = tasker;
        this.loadAllOffers();
    }

    public Offer create(UUID sellerUuid, ItemStack itemStack, double price) {
        Offer offer = this.offerRepository.findOrCreateByPath(UUID.randomUUID());

        offer.setItem(itemStack.clone());
        offer.setPrice(price);
        offer.setSold(false);
        offer.setCreatedAt(Instant.now());
        offer.setSellerUuid(sellerUuid);

        this.offers.put(offer.getUniqueId(), offer);
        offer.save();

        return offer;
    }

    public void remove(Offer offer) {
        this.offers.remove(offer.getUniqueId());
        this.offerRepository.deleteByPath(offer.getUniqueId());
    }

    public Collection<Offer> getOffers() {
        return Collections.unmodifiableCollection(this.offers.values());
    }

    private void loadAllOffers() {
        this.tasker.newChain()
                .async(() -> {
                    for (Offer offer : this.offerRepository.findAll()) {
                        this.offers.put(offer.getUniqueId(), offer);
                    }
                })
                .execute();
    }
}