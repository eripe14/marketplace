package com.eripe14.marketplace.marketplace;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "marketplace")
public class MarketplaceCommand {

    private final MarketplaceInventory marketplaceInventory;

    public MarketplaceCommand(MarketplaceInventory marketplaceInventory) {
        this.marketplaceInventory = marketplaceInventory;
    }

    @Execute
    void execute(@Context Player player) {
        this.marketplaceInventory.openInventory(player);
    }
}