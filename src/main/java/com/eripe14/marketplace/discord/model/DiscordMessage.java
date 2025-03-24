package com.eripe14.marketplace.discord.model;

import com.eripe14.marketplace.discord.DiscordWebhookService;
import com.google.gson.annotations.SerializedName;

public record DiscordMessage(
        @SerializedName("content") String content,
        @SerializedName("username") String username,
        @SerializedName("avatar_url") String avatarUrl
) {

    @Override
    public String content() {
        return this.content;
    }

    @Override
    public String username() {
        return this.username;
    }

    @Override
    public String avatarUrl() {
        return this.avatarUrl;
    }

    public String serialize() {
        return DiscordWebhookService.GSON.toJson(this);
    }

}
