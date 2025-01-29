package org.chamchi.basicKit.managers;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.chamchi.basicKit.modules.Cooldown;

import java.util.HashMap;
import java.util.Map;

public class CooldownManager {
    private Map<OfflinePlayer, Cooldown> cooldownMap;
    private int coolTime;

    public CooldownManager(ConfigManager configManager) {
        cooldownMap = new HashMap<>();
        this.coolTime = configManager.<Integer>getConfigValue("cooldown");
    }

    public void create(Player p) {
        cooldownMap.putIfAbsent(p, new Cooldown(p, coolTime));
    }

    public Cooldown get(Player p) {
        return cooldownMap.get(p);
    }

    public void removeExpired() {
        cooldownMap.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    public Map<OfflinePlayer, Cooldown> getCooldownMap() {
        return cooldownMap;
    }
}
