package main.java.codes.anhgelus.architectsLand.manager;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    public static final String VERSION = "0.1";
    public static final String ENABLE_HOME = "enable-home";
    public static final String ENABLE_BASE = "enable-base";

    //private final ArchitectsLand main;
    private final File config;
    private final YamlConfiguration yml;
    private final DataManager data;

    public ConfigManager(ArchitectsLand main) {
        //this.main = main;
        this.config = FileManager.getConfig(main);
        this.yml = YamlConfiguration.loadConfiguration(this.config);
        this.data = new DataManager("yml");
    }

    /**
     * Load the main configuration
     *
     * @return Main configuration
     */
    public YamlConfiguration load() {
        final String version = yml.getString("version");

        if (version == null) {
            yml.set("version", VERSION + " #DO NOT CHANGE THIS");
            yml.set(ENABLE_HOME, true);
            yml.set(ENABLE_BASE, true);

            data.save(yml, config);

            ChatManager.sendConsoleMessage("The configuration has been created");
        } else {
            ChatManager.sendConsoleMessage("The configuration has been loaded");
        }

        return yml;
    }

}
