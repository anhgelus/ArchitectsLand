package main.java.codes.anhgelus.architectsLand.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class HomeManager {
    private final YamlConfiguration config;
    private final Player sender;

    public static final String HOME_LOCATION = ".home.";

    public HomeManager(YamlConfiguration config, Player player) {
        this.config = config;
        this.sender = player;
    }

    /**
     * Get the home
     *
     * @param uuid uuid for the home
     * @return Location of the home
     */
    public Location getHome(String uuid) {
        final double x = config.getDouble(uuid + HOME_LOCATION + "x");
        final double y = config.getDouble(uuid + HOME_LOCATION + "y");
        final double z = config.getDouble(uuid + HOME_LOCATION + "z");
        final float yaw = (float) config.getDouble(uuid + HOME_LOCATION + "yaw");
        final float pitch = (float) config.getDouble(uuid + HOME_LOCATION + "pitch");
        final String world = config.getString(uuid + HOME_LOCATION + "world");

        if (coordsValid(x, y, z, yaw, pitch, world)) {
            return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        }
        return null;
    }

    /**
     * Set the home
     *
     * @param uuid uuid for the home
     * @param home Location of the home
     * @param file File of the config
     */
    public void setHome(String uuid, Location home, File file) {
        final DataManager data = new DataManager("yml");

        config.set(uuid + HOME_LOCATION + "x", home.getX());
        config.set(uuid + HOME_LOCATION + "y", home.getY());
        config.set(uuid + HOME_LOCATION + "z", home.getZ());
        config.set(uuid + HOME_LOCATION + "yaw", home.getYaw());
        config.set(uuid + HOME_LOCATION + "pitch", home.getPitch());
        config.set(uuid + HOME_LOCATION + "world", home.getWorld().getName());

        data.save(config, file);
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
