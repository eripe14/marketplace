package com.eripe14.marketplace.discord.model;

import com.google.gson.annotations.SerializedName;

public record DiscordEmbed(
        @SerializedName("title") String title,
        @SerializedName("description") String description,
        @SerializedName("color") String color,
        @SerializedName("timestamp") String timestamp,
        @SerializedName("author") DiscordAuthor author
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String description;
        private String color;
        private String timestamp;
        private DiscordAuthor author;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder author(DiscordAuthor author) {
            this.author = author;
            return this;
        }

        public DiscordEmbed build() {
            return new DiscordEmbed(
                    this.title,
                    this.description,
                    this.color,
                    this.timestamp,
                    this.author
            );
        }
    }

}