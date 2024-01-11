package fr.naurellia.naurelliaperms.completer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SetRankCommandCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;
            if (player.hasPermission("naurellia.staff.admin")) {

                if (strings.length == 2) {
                    return Arrays.asList("player", "hero", "legend", "premium", "royal", "builder", "helper", "moderator", "admin");
                }

            }
        }

        return null;
    }
}
