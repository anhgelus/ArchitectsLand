package main.java.codes.anhgelus.architectsLand.manager;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Array;

public class FactionManager {
    private final ArchitectsLand main;

    public FactionManager(ArchitectsLand main) {
        this.main = main;
    }

    /**
     * Get every factions created
     *
     * @return String[] of factions
     */
    public String[] getFactions() {
        File listFile = FileManager.getListData(this.main);
        final YamlConfiguration listConfig = YamlConfiguration.loadConfiguration(listFile);

        final String factionsString = listConfig.getString("factions");

        if (factionsString == null) {
            return new String[]{"NoFactionWasCreated"};
        } else {
            return factionsString.split(FactionCommand.UUID_SEPARATOR);
        }
    }

    /**
     * Detect if the faction exist or not
     *
     * @param config Config file (YamlConfiguration)
     * @param faction Detect this faction
     * @return true -> faction exist | false -> faction doesn't exist
     */
    public static boolean doubleFaction(YamlConfiguration config, String faction) {
        boolean toReturn = false;
        if (config.getString(faction.toLowerCase() + FactionCommand.FACTION_CHECKER_TARGET) != null) {
            toReturn = true;
        }
        return toReturn;
    }
}
