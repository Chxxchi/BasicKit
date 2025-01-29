package org.chamchi.basicKit.inventories;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.chamchi.basicKit.Initialize;
import org.chamchi.basicKit.managers.ConfigManager;
import org.chamchi.basicKit.utils.SynapseUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BasicKitInventory implements InventoryHolder {

    private String title;
    private int size;
    private Inventory inventory;

    private ConfigManager config;
    private List<ItemStack> basicKitItems;

    public BasicKitInventory(ConfigManager configManager) {
        config = configManager;
        basicKitItems = new ArrayList<>();

        title = SynapseUtils.color(config.<String>getConfigValue("inv-title"));
        size = config.<Integer>getConfigValue("inv-size");
        createInv();
    }

    private void createInv() {
        inventory = Bukkit.createInventory(this, size, Component.text(title));
        basicKitItems.forEach(item -> inventory.addItem(item));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
