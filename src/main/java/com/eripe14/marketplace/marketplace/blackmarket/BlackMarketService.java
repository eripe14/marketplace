package com.eripe14.marketplace.marketplace.blackmarket;

import com.eripe14.marketplace.config.implementation.PluginConfig;
import com.eripe14.marketplace.marketplace.offer.Offer;
import com.eripe14.marketplace.marketplace.offer.OfferService;
import com.eripe14.marketplace.notice.NoticeService;
import com.eripe14.marketplace.scheduler.Scheduler;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BlackMarketService {

    private final OfferService offerService;
    private final PluginConfig pluginConfig;

    public BlackMarketService(
            Scheduler scheduler,
            NoticeService noticeService,
            OfferService offerService,
            PluginConfig pluginConfig
    ) {
        this.offerService = offerService;
        this.pluginConfig = pluginConfig;

        scheduler.timerAsync(
                new BlackMarketScheduler(this, this.pluginConfig, noticeService),
                Duration.ZERO,
                this.pluginConfig.marketPlaceConfig.blackMarketShuffleDuration
        );
    }

    public CompletableFuture<Boolean> shuffleOffers() {
        return CompletableFuture.supplyAsync(() -> {
            List<Offer> allOffers = this.offerService.getOffers().stream().toList();
            if (allOffers.isEmpty()) {
                return false;
            }

            List<Offer> regularOffers = allOffers.stream()
                    .filter(offer -> !offer.isBlackMarket())
                    .collect(Collectors.toList());

            List<Offer> blackMarketOffers = allOffers.stream()
                    .filter(Offer::isBlackMarket)
                    .toList();

            blackMarketOffers.forEach(offer -> {
                offer.setPrice(offer.getPrice() * 2);
                offer.setBlackMarket(false);
                offer.save();
            });

            Collections.shuffle(regularOffers);

            int limit = (int) Math.ceil(regularOffers.size() *
                    (this.pluginConfig.marketPlaceConfig.blackMarketPercentage / 100.0));

            regularOffers.stream()
                    .limit(limit)
                    .forEach(offer -> {
                        offer.setBlackMarket(true);
                        offer.setPrice(offer.getPrice() / 2);
                        offer.save();
                    });
            return true;
        });
    }
}