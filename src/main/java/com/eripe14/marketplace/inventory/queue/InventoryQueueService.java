package com.eripe14.marketplace.inventory.queue;

import org.bukkit.entity.Player;
import xyz.xenondevs.invui.window.Window;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class InventoryQueueService {

    private final Map<Player, Deque<Window>> playerLastGuis = new HashMap<>();

    public void addWindow(Player player, Window window) {
        Deque<Window> windows = this.playerLastGuis.computeIfAbsent(player, k -> new LinkedList<>());

        for (Window iteratorWindow : windows) {
            if (iteratorWindow.equals(window)) {
                return;
            }
        }

        windows.offer(window);
    }

    public void reset(Player player) {
        this.playerLastGuis.remove(player);
    }

    public void openLatest(Player player) {
        Deque<Window> windows = this.playerLastGuis.get(player);

        if (windows == null) {
            return;
        }

        if (windows.isEmpty()) {
            player.closeInventory();
            return;
        }

        windows.removeLast();
        Window secondLastWindow = windows.peekLast();

        if (secondLastWindow == null) {
            player.closeInventory();
            return;
        }

        secondLastWindow.open();
    }

}