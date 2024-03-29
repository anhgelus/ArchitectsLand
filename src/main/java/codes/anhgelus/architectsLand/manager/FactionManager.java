package main.java.codes.anhgelus.architectsLand.manager;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
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
     * Check if it's the faction owner or not
     *
     * @param player player to check
     * @param faction faction who he's the owner
     * @param main ArchitectsLand main file
     * @return boolean
     */
    public static boolean isFactionOwner(Player player, String faction, ArchitectsLand main) {
        final YamlConfiguration factions = YamlConfiguration.loadConfiguration(FileManager.getFactionsData(main));

        final String owner = factions.getString(faction + ".owner");

        return Objects.equals(owner, String.valueOf(player.getUniqueId()));
    }

    /**
     * Check if the player is in a faction
     *
     * @param player player to check
     * @param main ArchitectsLand main file
     * @return boolean
     */
    public static boolean isInFaction(Player player, ArchitectsLand main) {
        final YamlConfiguration list = YamlConfiguration.loadConfiguration(FileManager.getPlayersData(main));

        final String faction = list.getString(player.getUniqueId() + ".faction");

        return faction != null;
    }

    public static String getFactionOfThePlayer(Player player, ArchitectsLand main) {
        final YamlConfiguration list = YamlConfiguration.loadConfiguration(FileManager.getPlayersData(main));

        return list.getString(player.getUniqueId() + ".faction");
    }
}
