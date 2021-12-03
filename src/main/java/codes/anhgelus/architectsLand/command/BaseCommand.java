package main.java.codes.anhgelus.architectsLand.command;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.base.*;
import main.java.codes.anhgelus.architectsLand.command.faction.FactionList;
import main.java.codes.anhgelus.architectsLand.manager.PermissionManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class BaseCommand implements CommandExecutor {

    private final ArchitectsLand main;

    public static final String PERMISSION_BASE = ArchitectsLand.PERMISSION + "base.";
    public static final List<String> COMMANDS = Arrays.asList("list", "set", "tp", "home");

    public BaseCommand(ArchitectsLand main) {

        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && (s.equals("base") || s.equals("b"))) {
            if (strings.length != 0) {
                if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"list"}, BaseList.PERMISSION)) {
                    final BaseList baseList = new BaseList();
                    return baseList.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"set"}, BaseSet.PERMISSION)) {
                    final BaseSet baseSet = new BaseSet();
                    return baseSet.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"tp"}, BaseTp.PERMISSION)) {
                    final BaseTp baseTp = new BaseTp();
                    return baseTp.command(strings, commandSender, main);
                } else if (PermissionManager.permissionChecker(strings[0], commandSender, new String[]{"home"}, BaseHome.PERMISSION)) {
                    final BaseHome baseHome = new BaseHome();
                    return baseHome.command(strings, commandSender, main);
                }
                commandSender.sendMessage(Static.ERROR + "You don't have the permission to do this!");
                return true;
            }
            final BaseHelp baseHelp = new BaseHelp();
            return baseHelp.command(strings, commandSender, main);
        }
        return false;
    }
}
