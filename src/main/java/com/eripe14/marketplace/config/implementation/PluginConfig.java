package com.eripe14.marketplace.config.implementation;

import com.eripe14.marketplace.discord.DiscordWebhookConfig;
import com.eripe14.marketplace.inventory.item.InventoryItem;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class PluginConfig extends OkaeriConfig {

    @Comment("Configuration for the storage")
    public StorageConfig storage = new StorageConfig();

    @Comment("Configuration for the marketplace inventory")
    public MarketPlaceConfig marketPlaceConfig = new MarketPlaceConfig();

    @Comment("Configuration for the confirm inventory")
    public ConfirmInventoryConfig confirmInventoryConfig = new ConfirmInventoryConfig();

    public static class StorageConfig extends OkaeriConfig {
        public String prefix = "mp";

        public String mongoUri = "mongodb://localhost:27017/marketplace";

        public String username = "eripe";

        public String password = "Karol123";
    }

    @Comment("Discord webhook settings")
    public DiscordWebhookConfig discordWebhook = new DiscordWebhookConfig();

    public static class MarketPlaceConfig extends OkaeriConfig {
        public String title = "&e&lMarketplace";
        public String blackMarketTitle = "&c&lBlack market";

        public int rows = 6;

        @Comment("Structure of the inventory")
        @Comment("O - offer")
        @Comment("X - filler")
        @Comment("P - previous page")
        @Comment("N - next page")
        @Comment("Q - close")
        public List<String> structure = List.of(
                "OOOOOOOOO",
                "OOOOOOOOO",
                "OOOOOOOOO",
                "OOOOOOOOO",
                "OOOOOOOOO",
                "XXXPQNXXX"
        );

        @Comment("Declare items icons for the structure")
        @Comment("Do not declare slots - slots will not be taken into account")

        @Comment("Declare how quit item should look like?")
        public InventoryItem quitItem = new InventoryItem(
                0,
                0,
                Material.BARRIER,
                "&cClose",
                List.of(),
                List.of(ItemFlag.HIDE_ATTRIBUTES)
        );

        @Comment("Declare how previous page item should look like?")
        public InventoryItem previousPageItem = new InventoryItem(
                0,
                0,
                Material.ARROW,
                "&ePrevious page",
                List.of(
                        "&7Page: &e{page}/{pages}"
                ),
                List.of(ItemFlag.HIDE_ATTRIBUTES)
        );

        @Comment("Declare how next page item should look like?")
        public InventoryItem nextPageItem = new InventoryItem(
                0,
                0,
                Material.ARROW,
                "&eNext page",
                List.of(
                        "&7Page: &e{page}/{pages}"
                ),
                List.of(ItemFlag.HIDE_ATTRIBUTES)
        );

        @Comment("Declare how filler item should look like?")
        public InventoryItem fillerItem = new InventoryItem(
                0,
                0,
                Material.GRAY_STAINED_GLASS_PANE,
                "",
                List.of(),
                List.of(ItemFlag.HIDE_ATTRIBUTES)
        );

        @Comment("Declare how offer should look like")
        @Comment("Material of item will be replaced with the item from the offer")
        public InventoryItem offerItem = new InventoryItem(
                0,
                0,
                Material.PAPER,
                "&eOffer",
                List.of(
                        "&7Price: &e{price}",
                        "&7Seller: &e{seller}"
                ),
                List.of(ItemFlag.HIDE_ATTRIBUTES)
        );

        @Comment("Time format")
        public String timeFormat = "dd/MM/yyyy HH:mm:ss";

        @Comment("Percentage of offers that will go to the black market")
        public double blackMarketPercentage = 50.0;

        @Comment("How often black market should be shuffled")
        public Duration blackMarketShuffleDuration = Duration.ofMinutes(2);

        @Comment("Should players get notification when black market got shuffled?")
        public boolean blackMarketShuffleNotification = true;
    }

    public static class ConfirmInventoryConfig extends OkaeriConfig {
        @Comment("Title of the inventory")
        public String title = "&eConfirm buy";

        @Comment("Rows of the inventory")
        public int rows = 1;

        @Comment("Fill empty slots")
        public boolean fillEmptySlots = true;

        @Comment("Filler item")
        @Comment("Slot will not be taken into account")
        public InventoryItem fillerItem = new InventoryItem(
                0,
                0,
                Material.GRAY_STAINED_GLASS_PANE,
                "",
                List.of(),
                List.of(ItemFlag.HIDE_ATTRIBUTES)
        );

        @Comment("Confirm item")
        public InventoryItem confirmItem = new InventoryItem(
                0,
                5,
                Material.GREEN_STAINED_GLASS_PANE,
                "&aConfirm",
                List.of(),
                List.of(ItemFlag.HIDE_ATTRIBUTES)
        );

        @Comment("Cancel item")
        public InventoryItem cancelItem = new InventoryItem(
                0,
                3,
                Material.RED_STAINED_GLASS_PANE,
                "&cCancel",
                List.of(),
                List.of(ItemFlag.HIDE_ATTRIBUTES)
        );

        @Comment("Additional info item")
        @Comment("Material of item will be replaced with the item from the offer")
        public InventoryItem additionalItem = new InventoryItem(
                0,
                4,
                Material.PAPER,
                "&eConfirm buy",
                List.of(
                        "&7Price: &e{price}",
                        "&7Seller: &e{seller}"
                ),
                List.of(ItemFlag.HIDE_ATTRIBUTES)
        );
    }

}