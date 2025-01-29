package org.chamchi.basicKit.managers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.chamchi.basicKit.inventories.BasicKitInventory;
import org.chamchi.basicKit.utils.SynapseUtils;

import java.util.Arrays;
import java.util.List;

import static org.chamchi.basicKit.utils.SynapseUtils.*;

public class BasicKitManager {
    private BasicKitInventory basicKitInventory;

    public BasicKitManager(BasicKitInventory basicKitInventory) {
        this.basicKitInventory = basicKitInventory;
    }

    public BasicKitInventory getBasicKitInventory() {
        return basicKitInventory;
    }

    public Inventory getInventory() {
        return basicKitInventory.getInventory();
    }

}
