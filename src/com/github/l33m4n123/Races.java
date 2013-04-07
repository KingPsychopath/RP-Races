package com.github.l33m4n123;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.l33m4n123.abilities.EatFoodListener;
import com.github.l33m4n123.abilities.LoseFoodWhileSprinting;
import com.github.l33m4n123.abilities.RegisterIntoDatabase;
import com.github.l33m4n123.abilities.UnderWaterBreathing;
import com.github.l33m4n123.abilities.UnderWaterFoodRegeneration;


/**
 *
 * Main Class for the Plugin RP-Races.
 * This Plugin is meant for adding new Races
 * into the experience of Minecraft with
 * special abilitys.
 *
 */
/**
 *
 * @author Leeman
 *
 */
public class Races extends JavaPlugin {
    /**
     *
     */
    public static Database sqlite;

    /**
     *
     */
    private UnderWaterBreathing underWaterBreathingEvent =
            new UnderWaterBreathing();

    /**
     *
     */
    private LoseFoodWhileSprinting onSprintEvent =
            new LoseFoodWhileSprinting();

    /**
     *
     */
    private UnderWaterFoodRegeneration underWaterFoodRegeneration =
            new UnderWaterFoodRegeneration(this);

    /**
     *
     */
    private EatFoodListener onEatFood =
            new EatFoodListener();

    /**
     *
     */
    private RegisterIntoDatabase onPlayerJoin =
            new RegisterIntoDatabase();
    /**
     *
     */
    private final Logger logger = Logger.getLogger("Minecraft");

    @Override
    public final void onDisable() {
        PluginDescriptionFile p = this.getDescription();
        this.logger.info(p.getName() + " V"
        + p.getVersion() + " has been disabled");
        sqlite.close(); // This closes the connection.
    }

    @Override
    public final void onEnable() {

        PluginDescriptionFile p = this.getDescription();
        this.logger.info(p.getName() + " V"
        + p.getVersion() + " has been enabled!");
        getConfig().options().copyDefaults(true);
        saveConfig();



        // To structure it a bit

        sqlite = new SQLite(Logger.getLogger("Minecraft"), "[RP-Races] ",
                getDataFolder().getAbsolutePath(), "Races", ".sqlite");

        sqlite.open();
        try {
            sqlTableCheck();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // Registering Custom Events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(underWaterBreathingEvent, this);
        pm.registerEvents(onEatFood, this);
        pm.registerEvents(onPlayerJoin, this);
        pm.registerEvents(underWaterFoodRegeneration, this);
        pm.registerEvents(onSprintEvent, this);
    }

    /**
     *
     * @throws SQLException a
     */
    public final void sqlTableCheck() throws SQLException {
        if (!sqlite.isTable("Races")) {
            sqlite.query("CREATE TABLE Races (id INT PRIMARY KEY,"
                    + "playername VARCHAR(50),"
                    + "race VARCHAR(50))");
           System.out.println("Races has been created!");
        }
    }

    /**
     *
     * @param sender Player that uses this command
     * @param cmd the actuall command itself
     * @param cmdLabel Name of the command
     * @param args Arguments following after the command
     * @return if the command worked or not
     */
    public final boolean onCommand(final CommandSender sender,
            final Command cmd, final String cmdLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("This command is meant for players only!");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("race")) {
            if (args.length >= 1 && args[0].equalsIgnoreCase("help")) {
                player.sendMessage(getConfig().getString("help"));
                return true;
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("i")
                    || args.length >= 1 && args[0].equalsIgnoreCase("info")) {
                player.sendMessage(getConfig().getString("info"));
                return true;
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("self")) {
                final int food = 5;
                player.setFoodLevel(food);
                try {
                    ResultSet rs = sqlite.query("SELECT race FROM Races"
                + " WHERE playername = '" + player.getName() + "'");


                    while (rs.next()) {
                        player.sendMessage(rs.getString(1));
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("time")) {
                player.sendMessage(getConfig().getString("time"));
                return true;
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("list")) {
                player.sendMessage(getConfig().getString("list"));
                return true;
            } else if (args.length >= 2 && args[0].equalsIgnoreCase("b")
                    || args.length >= 2 && args[0].equalsIgnoreCase("be")) {

                if (args[1].equalsIgnoreCase("Human")) {
                    try {
                        sqlite.query("UPDATE Races SET race"
                  + " = 'Human' WHERE playername = '" + player.getName() + "'");
                        player.sendMessage("§6Your race was set to Human");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            } else if (args.length >= 1) {
                player.sendMessage(getConfig().getString("help"));
            } else if (args.length == 0) {
                // Text that shows up when you type /race
                player.sendMessage(getConfig().getString("help"));
                return true;
            }
        }
        return false;
    }

}
