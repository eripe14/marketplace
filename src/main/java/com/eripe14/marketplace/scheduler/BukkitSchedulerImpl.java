package com.eripe14.marketplace.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.time.Duration;

public class BukkitSchedulerImpl implements Scheduler {

    private final Plugin plugin;
    private final BukkitScheduler rootScheduler;

    public BukkitSchedulerImpl(Plugin plugin) {
        this.plugin = plugin;
        this.rootScheduler = plugin.getServer().getScheduler();
    }

    @Override
    public void sync(Runnable task) {
        this.rootScheduler.runTask(this.plugin, task);
    }

    @Override
    public void async(Runnable task) {
        this.rootScheduler.runTaskAsynchronously(this.plugin, task);
    }

    @Override
    public void laterSync(Runnable task, Duration delay) {
        this.rootScheduler.runTaskLater(this.plugin, task, this.toTick(delay));
    }

    @Override
    public void laterAsync(Runnable task, Duration delay) {
        this.rootScheduler.runTaskLaterAsynchronously(this.plugin, task, this.toTick(delay));
    }

    @Override
    public void timerSync(Runnable task, Duration delay, Duration period) {
        this.rootScheduler.runTaskTimer(this.plugin, task, this.toTick(delay), this.toTick(period));
    }

    @Override
    public void timerAsync(Runnable task, Duration delay, Duration period) {
        this.rootScheduler.runTaskTimerAsynchronously(this.plugin, task, this.toTick(delay), this.toTick(period));
    }

    private long toTick(Duration duration) {
        return duration.toMillis() / 50L;
    }

}