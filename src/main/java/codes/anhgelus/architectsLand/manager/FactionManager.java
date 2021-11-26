package main.java.codes.anhgelus.architectsLand.manager;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Objects;

public class FactionManager {
    private final ArchitectsLand main;

    public FactionManager(ArchitectsLand main) {
        this.main = main;
    }

    public static final String LIST_KEY = "factions";

    /**
     * Get every factions created
     *
     * @return String[] of factions
     */
    public String[] getFactions() {
        File listFile = FileManager.getListData(this.main);
        final YamlConfiguration listConfig = YamlConfiguration.loadConfiguration(listFile);

        final String factionsString = listConfig.getString(LIST_KEY);

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

    /**
     *
     * @param player
     * @param faction
     * @param main
     * @return
     */
    public static boolean isFactionOwner(Player player, String faction, ArchitectsLand main) {
        final YamlConfiguration factions = YamlConfiguration.loadConfiguration(FileManager.getFactionsData(main));

        final String owner = factions.getString(faction + ".owner");

        if (Objects.equals(owner, player.getDisplayName())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isInFaction(Player player, ArchitectsLand main) {
        final YamlConfiguration list = YamlConfiguration.loadConfiguration(FileManager.getListData(main));

        final String faction = list.getString(player.getUniqueId() + ".faction");

        if (faction != null) {
            return true;
        } else {
            return false;
        }
    }
}
