package main.java.codes.anhgelus.architectsLand.command.base;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.BaseCommand;
import main.java.codes.anhgelus.architectsLand.manager.BaseManager;
import main.java.codes.anhgelus.architectsLand.manager.FactionManager;
import main.java.codes.anhgelus.architectsLand.manager.FileManager;
import main.java.codes.anhgelus.architectsLand.manager.HomeManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import main.java.codes.anhgelus.architectsLand.util.SubCommandBase;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class BaseSet implements SubCommandBase {

    public static final String PERMISSION = BaseCommand.PERMISSION_BASE + "set";

    @Override
    public boolean command(String[] strings, CommandSender commandSender, ArchitectsLand main) {

        final Player player = (Player) commandSender;

        if (!FactionManager.isInFaction(player, main)) {
            player.sendMessage(Static.ERROR + "You need to be in a faction to set a base!");
            return true;
        }

        final String faction = FactionManager.getFactionOfThePlayer(player, main);

        if (!FactionManager.isFactionOwner(player, faction, main)) {
            commandSender.sendMessage(Static.ERROR + "You're not the owner!");
            return true;
        }

        File file = FileManager.getFactionsData(main);
        final BaseManager baseManager = new BaseManager(YamlConfiguration.loadConfiguration(file), player);

        if (strings[1].equals("public")) {
            baseManager.setBase(faction, BaseManager.PUBLIC, player.getLocation(), file, main);

            player.sendMessage(Static.SUCCESS + "You've set the faction's public base!");
            return true;
        }

        if (strings[1].equals("private")) {
            baseManager.setBase(FactionManager.getFactionOfThePlayer(player, main), BaseManager.PRIVATE, player.getLocation(), file, main);
            player.sendMessage(Static.SUCCESS + "You've set the faction's private base!");
            return true;
        }

        player.sendMessage(Static.ERROR + "This type didn't exist! Valid types: \"public, private\"");

        return true;
    }
}
