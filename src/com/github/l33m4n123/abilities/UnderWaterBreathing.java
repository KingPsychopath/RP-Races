package com.github.l33m4n123.abilities;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        Player p = event.getPlayer();
        ResultSet rs = null;
        try {
             rs = Races.sqlite.query("SELECT race FROM Races"
                    + " WHERE playername = '" + p.getName() + "'");

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        final int constant = 280;
        String race = rs.getString(1);
        if (race.equalsIgnoreCase("Human")) {
            int air = p.getRemainingAir();
            if (air < constant) {
                p.setRemainingAir(constant);
            }
        }

    }
}
