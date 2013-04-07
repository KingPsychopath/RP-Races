package com.github.l33m4n123.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.l33m4n123.Races;

/**
 *
 * @author Leeman
 *
 */
public final class UnderWaterFoodRegeneration implements Listener {

    /**
     *
     */
    private Races plugin;

    /**
     *
     * @param pPlugin a
     *
     */
    public UnderWaterFoodRegeneration(final Races pPlugin) {
        this.plugin = pPlugin;
    }

    /**
     *
     * @param event a
     *
     */
    @EventHandler
    public void underWaterFoodRegeneration(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final long delay = 100;
        final Location loc = player.getLocation();
        final Block feet = loc.getBlock();
        final Block head = loc.add(0, 1, 0).getBlock();

        if ((feet.getType() == Material.WATER
                || feet.getType() == Material.STATIONARY_WATER)
                && (head.getType() == Material.WATER
                || head.getType() == Material.STATIONARY_WATER)) {
            // They are in water
            plugin.getServer().getScheduler()
                    .runTaskTimer(plugin, new Runnable() {
                        public void run() {
                            player.setFoodLevel(player.getFoodLevel() + 1);
                            plugin.getServer().getScheduler()
                            .cancelTasks(plugin);
                        }
                    }, delay, delay);
        }

    }
}
