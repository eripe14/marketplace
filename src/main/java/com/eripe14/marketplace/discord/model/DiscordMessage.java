package com.eripe14.marketplace.discord.model;

import com.eripe14.marketplace.discord.DiscordWebhookService;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public record DiscordMessage(
        @SerializedName("content") String content,
        @SerializedName("username") String username,
        @SerializedName("avatar_url") String avatarUrl,
        @SerializedName("embeds") List<DiscordEmbed> embeds
) {

    public String serialize() {
        return DiscordWebhookService.GSON.toJson(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String content;
        private String username;
        private String avatarUrl;
        private final List<DiscordEmbed> embeds = new ArrayList<>();

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public Builder addEmbed(DiscordEmbed embed) {
            this.embeds.add(embed);
            return this;
        }

        public DiscordMessage build() {
            return new DiscordMessage(this.content, this.username, this.avatarUrl, this.embeds);
        }
    }

}