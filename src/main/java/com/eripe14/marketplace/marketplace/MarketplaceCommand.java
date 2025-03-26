package com.eripe14.marketplace.marketplace;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Command(name = "marketplace")
public class MarketplaceCommand {

    private final Plugin plugin;
    private final MarketplaceInventory marketplaceInventory;

    public MarketplaceCommand(Plugin plugin, MarketplaceInventory marketplaceInventory) {
        this.plugin = plugin;
        this.marketplaceInventory = marketplaceInventory;
    }

    @Execute
    @Permission("marketplace.view")
    void execute(@Context Player player) {
        this.marketplaceInventory.openInventory(player);
    }

}