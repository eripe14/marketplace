package com.eripe14.marketplace.economy;

import org.bukkit.Server;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    private final Server server;
    private Economy economy;

    public VaultHook(Server server) {
        this.server = server;

        if (this.server.getPluginManager().isPluginEnabled("Vault")) {
            this.initialize();
        }
    }

    private void initialize() {
        RegisteredServiceProvider<Economy> economyProvider = this.server.getServicesManager().getRegistration(Economy.class);

        if (economyProvider == null) {
            throw new IllegalStateException("Vault founded, but you don't have a plugin that supports economy");
        }

        this.economy = economyProvider.getProvider();
    }

    public Economy getEconomy() {
        return this.economy;
    }
}