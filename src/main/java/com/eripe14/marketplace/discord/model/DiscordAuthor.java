package com.eripe14.marketplace.discord.model;

import com.google.gson.annotations.SerializedName;

public record DiscordAuthor(
        @SerializedName("name") String name,
        @SerializedName("url") String url,
        @SerializedName("icon_url") String iconUrl
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String url;
        private String iconUrl;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder iconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
            return this;
        }

        public DiscordAuthor build() {
            return new DiscordAuthor(this.name, this.url, this.iconUrl);
        }
    }

}