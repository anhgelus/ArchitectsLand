package main.java.codes.anhgelus.architectsLand.command.faction;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.manager.FactionManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import main.java.codes.anhgelus.architectsLand.util.SubCommandBase;
import org.bukkit.command.CommandSender;

public class FactionList implements SubCommandBase {
    private final String[] strings;
    private final CommandSender commandSender;
    private final ArchitectsLand main;

    public static final String PERMISSION = FactionCommand.PERMISSION_FACTION + "list";

    public FactionList (String[] strings, CommandSender commandSender, ArchitectsLand main) {
        this.strings = strings;
        this.commandSender = commandSender;
        this.main = main;
    }

    @Override
    public boolean command() {
        if (strings.length == 1) {
            final FactionManager fManager = new FactionManager(main);
            final String[] factions = fManager.getFactions();

            commandSender.sendMessage(Static.SUCCESS + "Factions' list:");
            for (String faction : factions) {
                commandSender.sendMessage(Static.EXAMPLE + "- " + faction);
            }
        } else {
            commandSender.sendMessage(Static.ERROR + "This command doesn't need args!");
        }
        return true;
    }
}
