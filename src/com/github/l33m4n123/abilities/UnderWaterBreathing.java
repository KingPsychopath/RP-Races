package com.github.l33m4n123.abilities;

import java.sql.ResultSet;
import java.sql.SQLException;

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
public class UnderWaterBreathing implements Listener {

    /**
     *
     */
    public UnderWaterBreathing() {
    }

    /**
     *
     * @param event a
     * @throws SQLException a
     *
     */
    @EventHandler
    public final void onDrownEvent(final PlayerMoveEvent event)
            throws SQLException {
        final Player p = event.getPlayer();
        ResultSet rs = null;
        try {
            rs = Races.sqlite.query("SELECT race FROM Races"
                    + " WHERE playername = '" + p.getName() + "'");

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        if (!(rs == null)) {
            String race = rs.getString(1);
            if (race.equalsIgnoreCase("Human")) {
                final Location loc = p.getLocation();
                final Block feet = loc.getBlock();
                final Block head = loc.add(0, 1, 0).getBlock();

                if ((feet.getType() == Material.WATER
                        || feet.getType() == Material.STATIONARY_WATER)
                        && (head.getType() == Material.WATER
                        || head.getType() == Material.STATIONARY_WATER)) {
                //They are in water
                    p.setRemainingAir(p.getMaximumAir());
                }

            }
        }

    }
}
