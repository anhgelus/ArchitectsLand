package main.java.codes.anhgelus.architectsLand.command.base;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.BaseCommand;
import main.java.codes.anhgelus.architectsLand.manager.BaseManager;
import main.java.codes.anhgelus.architectsLand.manager.FileManager;
import main.java.codes.anhgelus.architectsLand.manager.HomeManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import main.java.codes.anhgelus.architectsLand.util.SubCommandBase;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class BaseTp implements SubCommandBase {

    public static final String PERMISSION = BaseCommand.PERMISSION_BASE + "tp";

    @Override
    public boolean command(String[] strings, CommandSender commandSender, ArchitectsLand main) {

        final Player player = (Player) commandSender;

        if (strings.length != 2) {
            player.sendMessage(Static.ERROR + "You need to specify a faction to teleport to its base!");
            return true;
        }

        File file = FileManager.getFactionsData(main);
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        final BaseManager baseManager = new BaseManager(config, player);

        final Location base = baseManager.getBase(strings[1], BaseManager.PUBLIC);

        if (base != null) {
            player.teleport(base);
            player.sendMessage(Static.SUCCESS + "You've teleported to the faction's public base!");
            return true;
        }

        player.sendMessage(Static.ERROR + "This faction didn't set a public base!");

        return true;
    }
}
