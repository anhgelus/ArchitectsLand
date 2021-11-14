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
        File listFile = new FactionCommand(main).getListData();
        final YamlConfiguration listConfig = YamlConfiguration.loadConfiguration(listFile);

        final String factionsString = listConfig.getString("factions");

        if (factionsString == null) {
            return new String[]{"NoFactionWasCreated"};
        } else {
            return factionsString.split(FactionCommand.UUID_SEPARATOR);
        }
    }
}
