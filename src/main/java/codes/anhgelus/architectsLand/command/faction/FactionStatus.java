package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.manager.BaseManager;
import main.java.codes.anhgelus.architectsLand.manager.FactionManager;
import main.java.codes.anhgelus.architectsLand.manager.FileManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import main.java.codes.anhgelus.architectsLand.util.SubCommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class FactionStatus implements SubCommandBase {

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "modify";

    /**
     * Execute the command
     *
     * @return true
     */
    @Override
    public boolean command(String[] strings, CommandSender commandSender, ArchitectsLand main) {
        if (strings.length > 1) {
            final File basesFile = FileManager.getFactionsData(main);
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);

            final String key = strings[1].toLowerCase();
            final String status = ".status.";

            // Check if the faction exist
            if (!FactionManager.doubleFaction(config, key)) {
                commandSender.sendMessage(Static.ERROR + "This faction doesn't exist!");
                return true;
            }

            final BaseManager baseManager = new BaseManager(config, (Player) commandSender);

            // Get every args in the factions.yml
            final String fName = config.getString(key + status + "name");
            final String fPrefix = config.getString(key + status + "prefix");
            final String fColor = config.getString(key + status + "color");
            final String fDesc = config.getString(key + status + "description");
            final String fBase = baseManager.getBaseCoordsInString(key, BaseManager.PUBLIC);
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
                    Static.SUCCESS + "Public Base: " + Static.EXAMPLE + fBase + Static.EOL +
                    Static.SEPARATOR_COLOR + "---" + Static.EOL +
                    Static.SUCCESS + "In war against: " + Static.EXAMPLE + fWar + Static.EOL +
                    Static.SUCCESS + "In alliance with: " + Static.EXAMPLE + fAlliance + Static.EOL +
                    Static.SEPARATOR + Static.EOL);
        } else {
            commandSender.sendMessage(Static.ERROR + "You need to specify the faction's name to delete a faction!");
        }
        return true;
    }
}
