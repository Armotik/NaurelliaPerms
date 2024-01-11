package fr.naurellia.naurelliaperms.commands;

import fr.naurellia.naurelliaperms.NaurelliaPerms;
import fr.naurellia.naurelliaperms.tools.PermissionsReader;
import fr.naurellia.naurelliaperms.utiles.Database;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SetRankCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (strings.length < 1) {
            commandSender.sendMessage("§cUsage: /setrank <player> <rank>");
            return false;
        }

        Player target = commandSender.getServer().getPlayer(strings[0]);

        if (target == null) {
            commandSender.sendMessage("§cPlayer not found");
            return false;
        }

        String rank = strings[1];

        switch (rank) {
            case "player":
                Database.executeInsert("UPDATE `PlayersPermissions` SET `permission` = 'naurellia.player.player' WHERE `player_uuid` = '" + target.getUniqueId() + "'");
                commandSender.sendMessage("§a" + target.getName() + " is now a player");
                target.addAttachment(Objects.requireNonNull(NaurelliaPerms.getPlugin()), "naurellia.player.player", true);
                PermissionsReader.setPermission(target.getUniqueId(), "naurellia.player.player");
                target.updateCommands();
                Database.close();
                break;
            case "hero":
                Database.executeInsert("UPDATE `PlayersPermissions` SET `permission` = 'naurellia.player.hero' WHERE `player_uuid` = '" + target.getUniqueId() + "'");
                commandSender.sendMessage("§a" + target.getName() + " is now a hero");
                target.addAttachment(Objects.requireNonNull(NaurelliaPerms.getPlugin()), "naurellia.player.hero", true);
                PermissionsReader.setPermission(target.getUniqueId(), "naurellia.player.hero");
                target.updateCommands();
                Database.close();
                break;
            case "legend":
                Database.executeInsert("UPDATE `PlayersPermissions` SET `permission` = 'naurellia.player.legend' WHERE `player_uuid` = '" + target.getUniqueId() + "'");
                commandSender.sendMessage("§a" + target.getName() + " is now a legend");
                target.addAttachment(Objects.requireNonNull(NaurelliaPerms.getPlugin()), "naurellia.player.legend", true);
                PermissionsReader.setPermission(target.getUniqueId(), "naurellia.player.legend");
                target.updateCommands();
                Database.close();
                break;
            case "premium":
                Database.executeInsert("UPDATE `PlayersPermissions` SET `permission` = 'naurellia.player.premium' WHERE `player_uuid` = '" + target.getUniqueId() + "'");
                commandSender.sendMessage("§a" + target.getName() + " is now a premium");
                target.addAttachment(Objects.requireNonNull(NaurelliaPerms.getPlugin()), "naurellia.player.premium", true);
                target.updateCommands();
                Database.close();
                break;
            case "royal":
                Database.executeInsert("UPDATE `PlayersPermissions` SET `permission` = 'naurellia.player.royal' WHERE `player_uuid` = '" + target.getUniqueId() + "'");
                commandSender.sendMessage("§a" + target.getName() + " is now a royal");
                target.addAttachment(Objects.requireNonNull(NaurelliaPerms.getPlugin()), "naurellia.player.royal", true);
                PermissionsReader.setPermission(target.getUniqueId(), "naurellia.player.royal");
                target.updateCommands();
                Database.close();
                break;
            case "helper":
                Database.executeInsert("UPDATE `PlayersPermissions` SET `permission` = 'naurellia.staff.helper' WHERE `player_uuid` = '" + target.getUniqueId() + "'");
                commandSender.sendMessage("§a" + target.getName() + " is now a helper");
                target.addAttachment(Objects.requireNonNull(NaurelliaPerms.getPlugin()), "naurellia.staff.helper", true);
                PermissionsReader.setPermission(target.getUniqueId(), "naurellia.staff.helper");
                target.updateCommands();
                Database.close();
                break;
            case "moderator":
                Database.executeInsert("UPDATE `PlayersPermissions` SET `permission` = 'naurellia.staff.moderator' WHERE `player_uuid` = '" + target.getUniqueId() + "'");
                commandSender.sendMessage("§a" + target.getName() + " is now a moderator");
                target.addAttachment(Objects.requireNonNull(NaurelliaPerms.getPlugin()), "naurellia.staff.moderator", true);
                PermissionsReader.setPermission(target.getUniqueId(), "naurellia.staff.moderator");
                target.updateCommands();
                Database.close();
                break;
            case "srmod":
                Database.executeInsert("UPDATE `PlayersPermissions` SET `permission` = 'naurellia.staff.srmod' WHERE `player_uuid` = '" + target.getUniqueId() + "'");
                commandSender.sendMessage("§a" + target.getName() + " is now a srmod");
                target.addAttachment(Objects.requireNonNull(NaurelliaPerms.getPlugin()), "naurellia.staff.srmod", true);
                PermissionsReader.setPermission(target.getUniqueId(), "naurellia.staff.srmod");
                target.updateCommands();
                Database.close();
                break;
            case "admin":
                Database.executeInsert("UPDATE `PlayersPermissions` SET `permission` = 'naurellia.staff.admin' WHERE `player_uuid` = '" + target.getUniqueId() + "'");
                commandSender.sendMessage("§a" + target.getName() + " is now a admin");
                target.addAttachment(Objects.requireNonNull(NaurelliaPerms.getPlugin()), "naurellia.staff.admin", true);
                PermissionsReader.setPermission(target.getUniqueId(), "naurellia.staff.admin");
                target.updateCommands();
                Database.close();
                break;
            case "builder":
                Database.executeInsert("UPDATE `PlayersPermissions` SET `permission` = 'naurellia.staff.builder' WHERE `player_uuid` = '" + target.getUniqueId() + "'");
                commandSender.sendMessage("§a" + target.getName() + " is now a builder");
                target.addAttachment(Objects.requireNonNull(NaurelliaPerms.getPlugin()), "naurellia.staff.builder", true);
                PermissionsReader.setPermission(target.getUniqueId(), "naurellia.staff.builder");
                target.updateCommands();
                Database.close();
                break;
            default:
                commandSender.sendMessage("§cRank not found");
                break;
        }

        return true;
    }
}
