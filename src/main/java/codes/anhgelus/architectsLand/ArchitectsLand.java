package main.java.codes.anhgelus.architectsLand;

import main.java.codes.anhgelus.architectsLand.command.*;
import main.java.codes.anhgelus.architectsLand.event.PlayerJoinSetList;
import main.java.codes.anhgelus.architectsLand.tabCompleter.AnnouncementCompleter;
import main.java.codes.anhgelus.architectsLand.tabCompleter.BaseCompleter;
import main.java.codes.anhgelus.architectsLand.tabCompleter.BroadcastCompleter;
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
        plManager.registerEvents(new PlayerJoinSetList(this), this);

        //command
        getCommand("f").setExecutor(new FactionCommand(this));
        getCommand("broadcast").setExecutor(new BroadcastCommand(this));
        getCommand("announcement").setExecutor(new AnnouncementCommand());
        getCommand("home").setExecutor(new HomeCommand(this));
        getCommand("sethome").setExecutor(new HomeCommand(this));
        getCommand("base").setExecutor(new BaseCommand(this));

        //tabCompleter
        getCommand("f").setTabCompleter(new FactionCompleter(this));
        getCommand("broadcast").setTabCompleter(new BroadcastCompleter());
        getCommand("announcement").setTabCompleter(new AnnouncementCompleter());
        getCommand("base").setTabCompleter(new BaseCompleter(this));

    }

    public void onDisable() {
        LOGGER.info("[" + PLUGIN_NAME + "] The plugin has been desactivated");
    }

}
