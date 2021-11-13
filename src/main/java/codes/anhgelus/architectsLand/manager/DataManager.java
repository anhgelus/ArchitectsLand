package main.java.codes.anhgelus.architectsLand.manager;

import main.java.codes.anhgelus.architectsLand.exception.FileNotFound;
import main.java.codes.anhgelus.architectsLand.exception.FileNotSupported;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager {

    public String type = null;

    private final static List<String> ALLOWED_TYPES = new ArrayList<String>(Arrays.asList("yaml", "yml", "mysql", "mariadb", "postgresql", "postgre"));

    /**
     * Set the type
     *
     * @param type Type of database (yaml, yml, mysql, mariadb, postgresql, postgre)
     */
    public DataManager(String type) {
        if (ALLOWED_TYPES.contains(type.toLowerCase())) {
            this.type = type;
        } else {
            try {
                throw new FileNotSupported("This type is not supported!");
            } catch (FileNotSupported e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save YML File
     *
     * @param config Config to save (YamlConfiguration)
     * @param basesFile File to save (File)
     */
    public void save(YamlConfiguration config, File basesFile) {
        if (ALLOWED_TYPES.indexOf(type.toLowerCase()) == 0 || ALLOWED_TYPES.indexOf(type.toLowerCase()) == 1) {
            try {
                config.save(basesFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Array get(YamlConfiguration config, String search) {
        if (ALLOWED_TYPES.indexOf(type.toLowerCase()) == 0 || ALLOWED_TYPES.indexOf(type.toLowerCase()) == 1) {
            return (Array) config.get(search);
        } else {
            try {
                throw new FileNotSupported("This type is not supported!");
            } catch (FileNotSupported e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
