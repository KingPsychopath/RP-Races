package com.github.l33m4n123.abilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.l33m4n123.Races;


/**
 *
 * @author Leeman
 *
 */
public final class RegisterIntoDatabase implements Listener {
    /**
     *
     */
    public RegisterIntoDatabase() {
    }
    /**
     * @param event :D
     * @throws SQLException If something goes wrong
     */
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event)
            throws SQLException {
        Player player = event.getPlayer();

        ResultSet rs = Races.sqlite.query("SELECT playername FROM Races"
        + " WHERE playername='" + player.getName() + "'");
        if (!(rs.next())) {
            Races.sqlite.query("INSERT INTO Races(playername"
        + ", race) VALUES('" + player.getName() + "', 'Default')");
            }

    }

}
