package com.github.l33m4n123.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

/**
 *
 * @author Leeman
 *
 */
public final class LoseFoodWhileSprinting implements Listener {
    /**
     *
     */
    public LoseFoodWhileSprinting() {
    }

    /**
     * @param event e
     */
    @EventHandler
    public void onSprint(final PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();
        if (!(player.isSprinting())) {
            player.sendMessage("Yes!");
            player.setFoodLevel(player.getFoodLevel());
        }
    }

}
