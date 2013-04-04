package com.github.l33m4n123.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

/**
 *
 * @author Leeman
 *
 */
public class EatFoodListener implements Listener {

    /**
     *
     */
    public EatFoodListener() {
    }

    /**
     *
     * @param event a
     */

    @EventHandler
    public final void onFoodEat(final PlayerItemConsumeEvent event) {
          Player p = event.getPlayer();
          if (p.getItemInHand().getType() == Material.COOKIE) {
            event.setCancelled(true);
        }
    }
}
