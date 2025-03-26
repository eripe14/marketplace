package com.eripe14.marketplace.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EconomyService {

    private final Economy economy;

    public EconomyService(Economy economy) {
        this.economy = economy;
    }

    public boolean hasEnoughMoney(Player player, double amount) {
        return this.economy.has(player, amount);
    }

    public void withdrawMoney(OfflinePlayer player, double amount) {
        this.economy.withdrawPlayer(player, amount);
    }

    public void depositMoney(OfflinePlayer player, double amount) {
        this.economy.depositPlayer(player, amount);
    }

}