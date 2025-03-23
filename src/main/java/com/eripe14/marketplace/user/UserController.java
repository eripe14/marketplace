package com.eripe14.marketplace.user;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class UserController implements Listener {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventHandler
    void onPlayerJoin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        this.userRepository.findOrCreate(player.getUniqueId()).thenAccept(user -> {
            user.setName(player.getName());
            user.save();
        });
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        this.userRepository.findOrCreate(uniqueId).whenCompleteAsync((user, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                return;
            }

            this.userRepository.save(user);
        });
    }

}