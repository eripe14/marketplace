package com.eripe14.marketplace.inventory.queue;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryQueueController implements Listener {

    private final InventoryQueueService queueService;

    public InventoryQueueController(InventoryQueueService queueService) {
        this.queueService = queueService;
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        this.queueService.reset(event.getPlayer());
    }
}