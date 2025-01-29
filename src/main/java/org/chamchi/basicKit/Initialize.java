package org.chamchi.basicKit;

import org.bukkit.plugin.java.JavaPlugin;
import org.chamchi.basicKit.commands.AdminCommand;
import org.chamchi.basicKit.commands.UserCommand;
import org.chamchi.basicKit.inventories.BasicKitInventory;
import org.chamchi.basicKit.managers.BasicKitManager;
import org.chamchi.basicKit.managers.ConfigManager;
import org.chamchi.basicKit.managers.CooldownManager;
import org.chamchi.basicKit.managers.SaveManager;

public class Initialize extends JavaPlugin {

    private Initialize instance;
    private ConfigManager configManager;
    private BasicKitManager basicKitManager;
    private SaveManager saveManager;
    private CooldownManager cooldownManager;

    public BasicKitManager getBasicKitManager() {
        return basicKitManager;
    }

    @Override
    public void onEnable() {

        instance = this;
        configManager = new ConfigManager(this);

        cooldownManager = new CooldownManager(configManager);
        basicKitManager = new BasicKitManager(new BasicKitInventory(configManager));
        saveManager = new SaveManager(cooldownManager, configManager, basicKitManager.getBasicKitInventory());

        getCommand("기본템설정").setExecutor(new AdminCommand(instance, configManager));
        getCommand("기본템").setExecutor(new UserCommand(configManager, basicKitManager, cooldownManager));

        saveManager.load();
        getLogger().info("기본템 플러그인이 활성화 되었습니다.");
    }

    @Override
    public void onDisable() {
        saveManager.save();
        getLogger().info("기본템 플러그인이 비활성화 되었습니다.");
    }

}
