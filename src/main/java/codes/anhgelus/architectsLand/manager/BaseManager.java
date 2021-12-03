package main.java.codes.anhgelus.architectsLand.manager;

import main.java.codes.anhgelus.architectsLand.ArchitectsLand;
import main.java.codes.anhgelus.architectsLand.command.FactionCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class BaseManager {
    private final YamlConfiguration config;
    private final Player sender;

    public static final String PUBLIC = "public";
    public static final String PRIVATE = "private";

    public BaseManager(YamlConfiguration config, Player player) {
        this.config = config;
        this.sender = player;
    }

    /**
     * Get the home
     *
     * @param faction faction's name for the base
     * @param type type of home (public or privat)e
     * @return Location of the home
     */
    public Location getBase(String faction, String type) {
        final double x = config.getDouble(faction + ".base." + type + "." + "x");
        final double y = config.getDouble(faction + ".base." + type + "." + "y");
        final double z = config.getDouble(faction + ".base." + type + "." + "z");
        final float yaw = (float) config.getDouble(faction + ".base." + type + "." + "yaw");
        final float pitch = (float) config.getDouble(faction + ".base." + type + "." + "pitch");
        final String world = config.getString(faction + ".base." + type + "." + "world");

        if (coordsValid(x, y, z, yaw, pitch, world)) {
            return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        }
        return null;
    }

    /**
     * Set the home
     *
     * @param faction faction's name for the base
     * @param type Type of base (use static string)
     * @param base Location of the base
     * @param file File of the config
     */
    public void setBase(String faction, String type, Location base, File file, ArchitectsLand main) {
        final DataManager data = new DataManager("yml");

        config.set(faction + ".base." + type + "." + "x", base.getX());
        config.set(faction + ".base." + type + "." + "y", base.getY());
        config.set(faction + ".base." + type + "." + "z", base.getZ());
        config.set(faction + ".base." + type + "." + "yaw", base.getYaw());
        config.set(faction + ".base." + type + "." + "pitch", base.getPitch());
        config.set(faction + ".base." + type + "." + "world", base.getWorld().getName());

        data.save(config, file);

        if (type.equals(PUBLIC)) {
            final File listFile = FileManager.getListData(main);
            final YamlConfiguration yml = YamlConfiguration.loadConfiguration(listFile);

            yml.set("publicbase", faction + FactionCommand.UUID_SEPARATOR);

            data.save(yml, listFile);
        }
    }

    public String[] getBaseList(ArchitectsLand main) {
        final File listFile = FileManager.getListData(main);
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(listFile);

        final String listBrute = yml.getString("publicbase");


        if (listBrute == null) {
            return new String[]{"NoBaseSet"};
        } else {
            return listBrute.split(FactionCommand.UUID_SEPARATOR);
        }
    }

    /**
     * Check if the coords is valid
     *
     * @param x double coords x
     * @param y double coords y
     * @param z double coords z
     * @param yaw double coords yaw
     * @param pitch double coords pitch
     * @param world double string world
     * @return true if the coords is valid
     */
    private boolean coordsValid(double x, double y, double z, float yaw, float pitch, String world) {
        return x != 0 && y != 0 && z != 0 && yaw != 0 && pitch != yaw && !Objects.equals(world, "undefined");
    }
}
