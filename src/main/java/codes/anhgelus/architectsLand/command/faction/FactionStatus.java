package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.manager.FactionManager;
import main.java.codes.anhgelus.architectsLand.manager.FileManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import main.java.codes.anhgelus.architectsLand.util.SubCommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FactionStatus implements SubCommandBase {
    private final String[] strings;
    private final CommandSender commandSender;
    private final ArchitectsLand main;

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "modify";

    public FactionStatus (String[] strings, CommandSender commandSender, ArchitectsLand main) {
        this.strings = strings;
        this.commandSender = commandSender;
        this.main = main;
    }

    /**
     * Execute the command
     *
     * @return true
     */
    public boolean command() {
        if (strings.length > 1) {
            final File basesFile = FileManager.getFactionsData(this.main);
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            final String key = strings[1].toLowerCase();
            final String status = ".status.";

            // Check if the faction exist
            if (!FactionManager.doubleFaction(config, key)) {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

            // Get every args in the factions.yml
            final String fName = config.getString(key + status + "name");
            final String fPrefix = config.getString(key + status + "prefix");
            final String fColor = config.getString(key + status + "color");
            final String fDesc = config.getString(key + status + "description");
            String fWar = config.getString(key + status + "war").replace(",", ", ");
            String fAlliance = config.getString(key + status + "alliance").replace(",", ", ");
            if (fAlliance.equals(""))
                fAlliance = "Teaming with no one";
            if (fWar.equals(""))
                fWar = "Against no one";

            commandSender.sendMessage(Static.SEPARATOR + Static.EOL +
                    Static.IMPORTANT + strings[1].substring(0, 1).toUpperCase() + strings[1].substring(1) + " status:" + Static.EOL +
                    Static.SUCCESS + "Name: " + Static.EXAMPLE + fName + Static.EOL +
                    Static.SUCCESS + "Prefix: " + Static.EXAMPLE + fPrefix + Static.EOL +
                    Static.SUCCESS + "Color: " + Static.EXAMPLE + fColor + Static.EOL +
                    Static.SUCCESS + "Description: " + Static.EXAMPLE + fDesc + Static.EOL +
                    Static.SEPARATOR_COLOR + "---" + Static.EOL +
                    Static.SUCCESS + "In war against: " + fWar + Static.EOL +
                    Static.SUCCESS + "In alliance with: " + fAlliance + Static.EOL +
                    Static.SEPARATOR + Static.EOL);
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify the faction's name to delete a faction!");
        }
        return true;
    }
}
