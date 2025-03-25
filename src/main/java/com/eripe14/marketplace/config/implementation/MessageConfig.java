package com.eripe14.marketplace.config.implementation;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;

@Header("## MarketPlace (Message-Config) ##")
public class MessageConfig extends OkaeriConfig {

    public Notice offerItemCannotBeAir = Notice.chat(
         "<color:#FF2222>❌</color> <color:#FF4444>You can't sell air!</color>"
    );

    public Notice offerCreated = Notice.chat(
         "<color:#22DD22>✔</color> <color:#00FF7F>Offer successfully created with price: <color:#FFFF55>{price}$</color>!</color>"
    );

    public Notice noTransactions = Notice.chat(
         "<color:#FF2222>❌</color> <color:#FF4444>You don't have any transactions!</color>"
    );

    public Notice transactionsHeader = Notice.chat(
            "<color:#2299FF>ℹ</color> <color:#00AAFF>Your transactions:</color>"
    );

    public Notice transactionEntry = Notice.chat(
            "<color:#2299FF>•</color> <color:#00AAFF>You bought " +
                    "<color:#FFAA00>{item}</color> for <color:#FFAA00>{price}$</color> from " +
                    "<color:#FFAA00>{seller}</color> at <color:#FFAA00>{date}</color> " +
                    "in <color:#FFAA00>{source}</color>!</color>"
    );

    public Notice boughtItem = Notice.chat(
            "<color:#22DD22>✔</color> <color:#00FF7F>You bought <color:#FFFF55>{item}</color> " +
                    "for <color:#FFFF55>{price}$</color> from <color:#FFFF55>{seller}</color>!</color>"
    );

    public Notice soldItem = Notice.chat(
            "<color:#22DD22>✔</color> <color:#00FF7F>You sold <color:#FFFF55>{item}</color> " +
                    "for <color:#FFFF55>{price}$</color> to <color:#FFFF55>{buyer}</color>!</color>"
    );

}