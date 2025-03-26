package com.eripe14.marketplace.marketplace.blackmarket;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "black-market")
public class BlackMarketCommand {

    private final BlackMarketInventory blackMarketInventory;

    public BlackMarketCommand(BlackMarketInventory blackMarketInventory) {
        this.blackMarketInventory = blackMarketInventory;
    }

    @Execute
    void execute(@Context Player player) {
        this.blackMarketInventory.openInventory(player);
    }

}