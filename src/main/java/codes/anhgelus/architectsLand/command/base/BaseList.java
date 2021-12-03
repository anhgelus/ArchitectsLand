package main.java.codes.anhgelus.architectsLand.command.base;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.BaseCommand;
import main.java.codes.anhgelus.architectsLand.manager.BaseManager;
import main.java.codes.anhgelus.architectsLand.manager.FactionManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import main.java.codes.anhgelus.architectsLand.util.SubCommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class BaseList implements SubCommandBase {

    public final static String PERMISSION = BaseCommand.PERMISSION_BASE + "list";

    @Override
    public boolean command(String[] strings, CommandSender commandSender, ArchitectsLand main) {
        if (strings.length == 1) {
            final BaseManager bManager = new BaseManager(new YamlConfiguration(),((Player)commandSender));
            final String[] bases = bManager.getBaseList(main);

            commandSender.sendMessage(Static.SUCCESS + "Bases' list:");
            for (String base : bases) {
                commandSender.sendMessage(Static.EXAMPLE + "- " + base);
            }
        } else {
            commandSender.sendMessage(Static.ERROR + "This command doesn't need args!");
        }
        return true;
    }
}
