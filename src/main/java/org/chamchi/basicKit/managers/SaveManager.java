package org.chamchi.basicKit.managers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.chamchi.basicKit.inventories.BasicKitInventory;
import org.chamchi.basicKit.modules.Cooldown;
import org.chamchi.basicKit.utils.SynapseUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

public class SaveManager {
    private final CooldownManager cooldownManager;
    private final ConfigManager configManager;
    private final BasicKitInventory inventory;
    private final YamlConfiguration data;
    private final YamlConfiguration user;

    public SaveManager(CooldownManager cooldownManager, ConfigManager configManager, BasicKitInventory inventory) {
        this.cooldownManager = cooldownManager;
        this.configManager = configManager;
        this.inventory = inventory;

        this.data = configManager.getFileMaker().getData();
        this.user = configManager.getFileMaker().getUser();
    }

    public void save() {
        saveKit();
        saveCooldown();
    }

    public void load() {
        loadKit();
        loadCooldown();
    }

    private void loadCooldown() {
        ConfigurationSection section = configManager.getFileMaker().getUser().getConfigurationSection("cooldowns.");
        if (section == null) return;

        for (String key : section.getKeys(false)) {

            String start = "cooldowns." + key + ".";
            OfflinePlayer player = Bukkit.getPlayer(key);
            Calendar startTime = SynapseUtils.stringToCalendar(user.getString(start + "startTime"));
            Calendar expirTime = SynapseUtils.stringToCalendar(user.getString(start + "expirTime"));

            Cooldown cooldown = new Cooldown(player, configManager.<Integer>getConfigValue("cooldown"));
            cooldown.setStartTime(startTime);
            cooldown.setExpirTime(expirTime);

            cooldownManager.getCooldownMap().put(player, cooldown);
        }
    }

    private void saveCooldown() {
        user.set("cooldowns", "");
        if (cooldownManager.getCooldownMap().isEmpty()) return;

        for (Map.Entry<OfflinePlayer, Cooldown> entry : cooldownManager.getCooldownMap().entrySet()) {

            String key = "cooldowns." + entry.getKey().getUniqueId();
            Cooldown cooldown = entry.getValue();

            user.set(key + ".owner", entry.getKey());
            user.set(key + ".startTime", cooldown.getStartTime());
            user.set(key + ".expirTime", cooldown.getExpirTime());
        }
        try {
            user.save(configManager.getFileMaker().FILE_USER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadKit() {
        ConfigurationSection section = data.getConfigurationSection("basic_kits");

        if (section == null) return;
        for (String key : section.getKeys(false)) {
            ItemStack item = section.getItemStack(key);
            inventory.getInventory().setItem(Integer.parseInt(key), item);
        }
    }

    private void saveKit() {
        ItemStack[] kits = inventory.getInventory().getStorageContents();

        if (kits.length == 0) return;
        int count = 0;
        for (ItemStack item : kits) {
            data.set("basic_kits." + count++, item);
        }
        try {
            data.save(configManager.getFileMaker().FILE_DATA);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
