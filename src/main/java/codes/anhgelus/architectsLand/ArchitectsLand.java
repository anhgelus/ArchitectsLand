package main.java.codes.anhgelus.architectsLand;

import main.java.codes.anhgelus.architectsLand.command.AnnouncementCommand;
import main.java.codes.anhgelus.architectsLand.command.BroadcastCommand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.tabCompleter.FactionCompleter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ArchitectsLand extends JavaPlugin {

    public static final Logger LOGGER = Logger.getLogger("Minecraft");

    private PluginManager plManager;

    public static final String PLUGIN_NAME = "Architects Land";
    public static final String PERMISSION = "architectsland.";

    /*public void onLoad() {

    }*/

    public void onEnable() {
        LOGGER.info("[" + PLUGIN_NAME +  "] The plugin has been activated"); //info

        plManager = Bukkit.getServer().getPluginManager();

        //listener

        //command
        getCommand("f").setExecutor(new FactionCommand(this));
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("announcement").setExecutor(new AnnouncementCommand());

        //tabCompleter
        getCommand("f").setTabCompleter(new FactionCompleter());

    }

    public void onDisable() {
        LOGGER.info("[" + PLUGIN_NAME + "] The plugin has been desactivated");
    }

}
