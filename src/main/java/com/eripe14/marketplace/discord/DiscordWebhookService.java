package com.eripe14.marketplace.discord;

import com.eripe14.marketplace.discord.model.DiscordAuthor;
import com.eripe14.marketplace.discord.model.DiscordEmbed;
import com.eripe14.marketplace.discord.model.DiscordMessage;
import com.eripe14.marketplace.marketplace.offer.Offer;
import com.eternalcode.multification.shared.Formatter;
import com.google.gson.Gson;
import org.asynchttpclient.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class DiscordWebhookService {

    public static final Gson GSON = new Gson();

    private final AsyncHttpClient client = Dsl.asyncHttpClient();
    private final DiscordWebhookSettings settings;

    public DiscordWebhookService(DiscordWebhookSettings settings) {
        this.settings = settings;
    }

    public void prepareMessage(Player buyer, Offer offer) {
        Formatter formatter = new Formatter();
        formatter.register("{player}", buyer.getName());
        formatter.register("{price}", offer.getPrice());
        formatter.register("{seller}", Bukkit.getOfflinePlayer(offer.getSellerUuid()).getName());
        formatter.register(
                "{item}",
                offer.getItem().getType().name().replace("_", " ").toLowerCase()
        );

        DiscordMessage discordMessage = DiscordMessage.builder()
                .content("")
                .username(this.settings.webhookUsername())
                .avatarUrl(this.settings.webhookAvatarUrl())
                .addEmbed(DiscordEmbed.builder()
                        .title(this.settings.embedTitle())
                        .description(formatter.format(this.settings.embedDescription()))
                        .color(this.settings.embedColor())
                        .author(DiscordAuthor.builder()
                                .url("")
                                .name(formatter.format(this.settings.authorName()))
                                .iconUrl(
                                        this.settings.showPlayerHead()
                                                ? "https://mc-heads.net/avatar/" + buyer.getUniqueId()
                                                : ""
                                ).build()
                        ).build()
                ).build();

        this.postMessage(discordMessage.serialize());
    }

    private void postMessage(String json) {
        BoundRequestBuilder postRequest = this.client.preparePost(this.settings.webhookUrl())
                .setBody(json)
                .setHeader("Content-Type", "application/json");

        postRequest.execute(new AsyncCompletionHandler<>() {
            @Override
            public Object onCompleted(Response response) {
                return response;
            }
        });
    }

}