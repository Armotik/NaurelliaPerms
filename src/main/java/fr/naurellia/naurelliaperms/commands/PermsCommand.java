package fr.naurellia.naurelliaperms.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class PermsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        for (PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()) {
            player.sendMessage(permissionAttachmentInfo.getPermission());
        }

        return true;
    }
}
