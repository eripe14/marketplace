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


}