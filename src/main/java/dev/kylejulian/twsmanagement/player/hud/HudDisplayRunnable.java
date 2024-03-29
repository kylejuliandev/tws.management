package dev.kylejulian.twsmanagement.player.hud;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HudDisplayRunnable extends BukkitRunnable {

    private final JavaPlugin plugin;
    private final UUID playerId;

    public HudDisplayRunnable(@NotNull JavaPlugin plugin, @NotNull UUID playerId) {
        this.playerId = playerId;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        final Player player = this.plugin.getServer().getPlayer(this.playerId);
        if (player == null) {
            return;
        }

        final Location location = player.getLocation();
        final Integer x = location.getBlockX();
        final Integer y = location.getBlockY();
        final Integer z = location.getBlockZ();

        final long playerWorldTime = player.getWorld().getTime();
        final float playerYaw = location.getYaw();

        String xDisplay = "\u00a7a\u00a7lX \u00a7r%s ";
        final Component xFormatted = Component.text(String.format(xDisplay, x));
        String yDisplay = "\u00a7a\u00a7lY \u00a7r%s ";
        final Component yFormatted = Component.text(String.format(yDisplay, y));
        String zDisplay = "\u00a7a\u00a7lZ \u00a7r%s ";
        final Component zFormatted = Component.text(String.format(zDisplay, z));
        String orientationDisplay = "\u00a7e\u00a7l%s ";
        final Component orientationFormatted = Component.text(String.format(orientationDisplay, Orientation.getOrientation(playerYaw)));
        String timeDisplay = "\u00a7c\u00a7l%s";
        final Component timeFormatted = Component.text(String.format(timeDisplay, Time.ticksToTime(playerWorldTime)));

        Component baseComponent = Component.empty()
            .append(xFormatted)
            .append(yFormatted)
            .append(zFormatted)
            .append(orientationFormatted)
            .append(timeFormatted);

        player.sendActionBar(baseComponent);
    }
}
