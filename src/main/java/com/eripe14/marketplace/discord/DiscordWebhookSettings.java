package com.eripe14.marketplace.discord;

public interface DiscordWebhookSettings {

    String webhookUrl();

    String webhookUsername();

    String webhookAvatarUrl();

    String embedTitle();

    String embedDescription();

    String embedColor();

    String authorName();

    boolean showPlayerHead();

}