package com.eripe14.marketplace.config.implementation;

import com.eripe14.marketplace.discord.DiscordWebhookConfig;
import com.eripe14.marketplace.inventory.item.InventoryItem;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.util.List;

public class PluginConfig extends OkaeriConfig {

    @Comment("Configuration for the storage")
    public StorageConfig storage = new StorageConfig();

    @Comment("Configuration for the marketplace inventory")
    public MarketPlaceInventoryConfig marketPlaceInventory = new MarketPlaceInventoryConfig();

    public static class StorageConfig extends OkaeriConfig {
        public String prefix = "mp";

        public String mongoUri = "mongodb://localhost:27017/marketplace";

        public String username = "eripe";

        public String password = "Karol123";
    }

    @Comment("Discord webhook settings")
    public DiscordWebhookConfig discordWebhook = new DiscordWebhookConfig();

    public static class MarketPlaceInventoryConfig extends OkaeriConfig {
        public String title = "&eMarketplace";

        public int rows = 6;

        @Comment("Structure of the inventory")
        @Comment("O - offer")
        @Comment("X - filler")
        @Comment("P - previous page")
        @Comment("N - next page")
        @Comment("Q - close")
        @Comment("S - settings (profile)")
        public List<String> structure = List.of(
                "OOOOOOOOO",
                "OOOOOOOOO",
                "OOOOOOOOO",
                "OOOOOOOOO",
                "OOOOOOOOO",
                "XXXPQNXXS"
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

        @Comment("Declare how settings item should look like?")
        public InventoryItem settingsItem = new InventoryItem(
                0,
                0,
                Material.CLOCK,
                "&eSettings",
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

    }

}