package com.eripe14.marketplace.marketplace.blackmarket;

import com.eripe14.marketplace.config.implementation.PluginConfig;
import com.eripe14.marketplace.notice.NoticeService;

public class BlackMarketScheduler implements Runnable {

    private final BlackMarketService blackMarketService;
    private final PluginConfig pluginConfig;
    private final NoticeService noticeService;

    public BlackMarketScheduler(BlackMarketService blackMarketService, PluginConfig pluginConfig, NoticeService noticeService) {
        this.blackMarketService = blackMarketService;
        this.pluginConfig = pluginConfig;
        this.noticeService = noticeService;
    }

    @Override
    public void run() {
        this.blackMarketService.shuffleOffers().whenCompleteAsync((result, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                return;
            }

            if (!result) {
                return;
            }

            if (!this.pluginConfig.marketPlaceConfig.blackMarketShuffleNotification) {
                return;
            }

            this.noticeService.create()
                    .all()
                    .notice(messages -> messages.blackMarketShuffled)
                    .send();
        });
    }

}