package org.chamchi.basicKit.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.chamchi.basicKit.Initialize;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    Initialize instance;
    FileMaker fileMaker;

    public ConfigManager(Initialize instance) {
        this.instance = instance;
        this.fileMaker = new FileMaker(instance);
    }

    public <T> T getConfigValue(String key) {
        return (T) fileMaker.getConfig().get(key);
    }

    public FileMaker getFileMaker() {
        return fileMaker;
    }
}

class FileMaker {
    final File PLUGIN_DIR;
    final File FILE_CONFIG;
    final File FILE_DATA;
    final File FILE_USER;

    Initialize instance;

    public FileMaker(Initialize instance) {
        this.instance = instance;

        this.PLUGIN_DIR = instance.getDataFolder();
        this.FILE_CONFIG = new File(PLUGIN_DIR + "\\" + "config.yml");
        this.FILE_DATA = new File(PLUGIN_DIR + "\\" + "data.yml");
        this.FILE_USER = new File(PLUGIN_DIR + "\\" + "user.yml");
        createFiles();
    }

    private void createFiles() {
        if (!PLUGIN_DIR.exists()) PLUGIN_DIR.mkdir();

        if (!FILE_CONFIG.exists()) {
            createFile(FILE_CONFIG);
            configDefaultSet();
        }

        if (!FILE_DATA.exists()) {
            createFile(FILE_DATA);
        }

        if (!FILE_USER.exists()) {
            createFile(FILE_USER);
        }
    }

    private void createFile(File f) {
        try {
            f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void configDefaultSet() {
        YamlConfiguration config = getConfig();
        config.set("inv-title", "&8&l[기본템] &8기본 아이템 설정하기");
        config.set("inv-size", 27);
        config.set("cooldown", 30);
        config.set("permission", "basic.kit.admin");
        config.set("message-success", "&6&l[알림] &f기본템을 성공적으로 지급받으셨습니다!");
        config.set("message-fail", "&6&l[알림] &f아직 기본템을 받을 수 없습니다. &7(%cooldown% 더 기다려주세요!)");
        try {
            config.save(FILE_CONFIG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public YamlConfiguration getConfig() { return YamlConfiguration.loadConfiguration(FILE_CONFIG); }
    public YamlConfiguration getData() { return YamlConfiguration.loadConfiguration(FILE_DATA); }
    public YamlConfiguration getUser() { return YamlConfiguration.loadConfiguration(FILE_USER); }

}

