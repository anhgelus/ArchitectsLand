package main.java.codes.anhgelus.architectsLand.command;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.manager.FileManager;
import main.java.codes.anhgelus.architectsLand.manager.HomeManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class HomeCommand implements CommandExecutor {
    private final ArchitectsLand main;

    public HomeCommand(ArchitectsLand main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (s.equals("home") || s.equals("sethome") && commandSender instanceof Player) {

            final Player player = (Player) commandSender;

            File basesFile = FileManager.getPlayersData(this.main);
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(basesFile);
            final HomeManager homeManager = new HomeManager(config, player);

            if (s.equals("home")) {
                final Location home = homeManager.getHome(String.valueOf(player.getUniqueId()));

                if (home != null) {
                    player.teleport(home);
                    player.sendMessage(Static.SUCCESS + "You've teleported to your home!");
                    return true;
                }

                player.sendMessage(Static.ERROR + "You don't have set a home yet!");
            }

            if (s.equals("sethome")) {
                homeManager.setHome(String.valueOf(player.getUniqueId()), player.getLocation(), basesFile);
                player.sendMessage(Static.SUCCESS + "You've set your home!");
            }

            return true;
        }
        return false;
    }
}
