package com.eripe14.marketplace.discord;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

public class DiscordWebhookConfig extends OkaeriConfig implements DiscordWebhookSettings {

    @Comment("Webhook URL")
    private String webhookUrl = "https://discord.com/api/webhooks/1353635834385137734/sv-mQs4QYZnGpLKvYvcTEgEwuWdJqddCxQAoZh2khdrMRXWAWsrMGi7VNriR99BrLmXg";

    @Comment("Webhook username")
    private String webhookUsername = "Marketplace";

    @Comment("Webhook avatar URL")
    private String webhookAvatarUrl = "https://i.imgur.com/oBPXx0D.png";

    @Comment("Embed title")
    private String embedTitle = "New sale";

    @Comment("Embed description")
    @Comment("Available placeholders: ")
    @Comment("{player} - player who bought the item")
    @Comment("{item} - item name")
    @Comment("{price} - price of the item")
    @Comment("{seller} - seller of the item")
    private String embedDescription = "Player **{player}** bought {item} for {price}$ from **{seller}**";

    @Comment("Embed color")
    private String embedColor = "5592575";

    @Comment("Author name")
    @Comment("Available placeholders: ")
    @Comment("{player} - player who bought the item")
    private String authorName = "{player}";

    @Comment("Show player head in the message")
    private boolean showPlayerHead = true;

    @Override
    public String webhookUrl() {
        return this.webhookUrl;
    }

    @Override
    public String webhookUsername() {
        return this.webhookUsername;
    }

    @Override
    public String webhookAvatarUrl() {
        return this.webhookAvatarUrl;
    }

    @Override
    public String embedTitle() {
        return this.embedTitle;
    }

    @Override
    public String embedDescription() {
        return this.embedDescription;
    }

    @Override
    public String embedColor() {
        return this.embedColor;
    }

    @Override
    public String authorName() {
        return this.authorName;
    }

    @Override
    public boolean showPlayerHead() {
        return this.showPlayerHead;
    }

}