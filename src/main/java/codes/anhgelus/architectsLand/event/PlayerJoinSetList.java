package main.java.codes.anhgelus.architectsLand.event;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import main.java.codes.anhgelus.architectsLand.manager.FileManager;
import main.java.codes.anhgelus.architectsLand.manager.HomeManager;
import main.java.codes.anhgelus.architectsLand.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerJoinSetList implements Listener {

    private final ArchitectsLand main;

    public PlayerJoinSetList(ArchitectsLand main) {
        this.main = main;
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent e) {
        File listFile = FileManager.getListData(this.main);
        final YamlConfiguration listConfig = YamlConfiguration.loadConfiguration(listFile);

        File playersFile = FileManager.getPlayersData(this.main);
        final YamlConfiguration playersConfig = YamlConfiguration.loadConfiguration(playersFile);

        final Player p = e.getPlayer();
        final String pName = String.valueOf(p.getUniqueId());

        final String playersString = listConfig.getString("players");

        if (playersString == null) {
            p.sendMessage(Static.SEPARATOR_COLOR + "Hey " + Static.EXAMPLE + p.getDisplayName() + Static.SEPARATOR_COLOR + ", welcome in Architects Land!");

            /* Set the default home in players.yml */
            setDefaultHome(pName, playersConfig, playersFile);

            listConfig.set("players", pName);

            FactionCommand.saveFile(listConfig, listFile); //save lists.yml

        } else if (!playersString.contains(pName)) {
            p.sendMessage(Static.SEPARATOR_COLOR + "Hey " + p.getDisplayName() + ", welcome in Architects Land!");

            /* Set the players' name in players.yml */
            setDefaultHome(pName, playersConfig, playersFile);

            listConfig.set("players", playersString + pName + FactionCommand.UUID_SEPARATOR);

            FactionCommand.saveFile(listConfig, listFile); //save lists.yml

        }
    }

    private void setDefaultHome(String uuid, YamlConfiguration conf, File file) {

        /* Set the default home in players.yml */
        conf.set(uuid + HomeManager.HOME_LOCATION + "x", 0);
        conf.set(uuid + HomeManager.HOME_LOCATION + "y", 0);
        conf.set(uuid + HomeManager.HOME_LOCATION + "z", 0);
        conf.set(uuid + HomeManager.HOME_LOCATION + "yaw", 0);
        conf.set(uuid + HomeManager.HOME_LOCATION + "pitch", 0);
        conf.set(uuid + HomeManager.HOME_LOCATION + "world", "undefined");

        FactionCommand.saveFile(conf, file);
    }
}
