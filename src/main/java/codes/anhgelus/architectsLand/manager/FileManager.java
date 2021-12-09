package main.java.codes.anhgelus.architectsLand.manager;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;

import java.io.File;

public class FileManager {

    /**
     * Get the factions' data (factions.yml)
     *
     * @return Factions data file
     */
    public static File getFactionsData(ArchitectsLand main) { return new File(main.getDataFolder(), "data/factions.yml"); }

    /**
     * Get the players' data (player.yml)
     *
     * @return Players data file
     */
    public static File getPlayersData(ArchitectsLand main) { return new File(main.getDataFolder(), "data/players.yml"); }

    /**
     * Get the lists' data (lists.yml)
     *
     * @return Lists data file
     */
    public static File getListData(ArchitectsLand main) {
        return new File(main.getDataFolder(), "data/lists.yml");
    }

    /**
     * Get the config (config.yml)
     *
     * @return Config file
     */
    public static File getConfig(ArchitectsLand main) {
        return new File(main.getDataFolder(), "config.yml");
    }
}
