package org.chamchi.basicKit.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.chamchi.basicKit.managers.BasicKitManager;
import org.chamchi.basicKit.managers.ConfigManager;
import org.chamchi.basicKit.managers.CooldownManager;
import org.chamchi.basicKit.modules.Cooldown;
import org.chamchi.basicKit.utils.CooldownFormatter;
import org.chamchi.basicKit.utils.SynapseUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

import static org.chamchi.basicKit.utils.SynapseUtils.*;

public class UserCommand implements @Nullable CommandExecutor {
    private final ConfigManager configManager;
    private final BasicKitManager basicKitManager;
    private final CooldownManager cooldownManager;

    public UserCommand(ConfigManager configManager, BasicKitManager basicKitManager, CooldownManager cooldownManager) {
        this.configManager = configManager;
        this.basicKitManager = basicKitManager;
        this.cooldownManager = cooldownManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) {
            info(commandSender, "콘솔에서 해당 명령어 접근이 불가능합니다.");
            return false;
        }

        giveKit((Player) commandSender);
        return false;
    }

    public void giveKit(Player player) {
        ItemStack[] kits = basicKitManager.getInventory().getStorageContents();

        if (kits.length == 0) {
            info(player, "기본템이 생성되지 않았습니다. 관리진에게 문의하여주세요.");
            return;
        }

        cooldownManager.removeExpired();

        Optional<Cooldown> cooldown = Optional.ofNullable(cooldownManager.get(player));
        if (cooldown.isPresent()) {
            String message = SynapseUtils.color(
                    configManager.getConfigValue("message-fail"));

            CooldownFormatter formatter = new CooldownFormatter(cooldown.get());
            formatter.setMessage(message);
            if (!cooldown.get().isExpired()) { send(player, formatter.format()); }
            return;
        }

        for (ItemStack itemStack : kits) {
            if (!SynapseUtils.hasEnoughSpace(player.getInventory(), itemStack)) {
                info(player, "인벤토리에 공간이 부족합니다!");
                return;
            }
        }

        cooldownManager.create(player);
        Arrays.stream(kits).filter(item -> (item != null && item.getType() != Material.AIR)).forEach(item -> player.getInventory().addItem(item));
        send(player, SynapseUtils.color(
                configManager.getConfigValue("message-success")));
    }
}
