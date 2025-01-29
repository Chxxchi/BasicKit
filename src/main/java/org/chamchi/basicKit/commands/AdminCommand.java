package org.chamchi.basicKit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.chamchi.basicKit.Initialize;
import org.chamchi.basicKit.managers.BasicKitManager;
import org.chamchi.basicKit.managers.ConfigManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static org.chamchi.basicKit.utils.SynapseUtils.*;

public class AdminCommand implements @Nullable CommandExecutor {

    private Initialize instance;
    private ConfigManager config;

    public AdminCommand(Initialize instance, ConfigManager configManager) {
        this.instance = instance;
        this.config = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) {
            info(commandSender, "콘솔에서는 해당 기능을 이용하실 수 없습니다.");
            return false;
        }

        Player player = (Player) commandSender;
        String permission = config.<String>getConfigValue("permission");

        if (!player.hasPermission(permission)) {
            info(player, "해당 명령어에 접근할 권한이 존재하지 않습니다.");
        }

        BasicKitManager manager = instance.getBasicKitManager();
        player.openInventory(manager.getInventory());
        return false;
    }
}
