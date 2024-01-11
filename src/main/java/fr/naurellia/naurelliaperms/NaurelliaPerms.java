package fr.naurellia.naurelliaperms;

import fr.naurellia.naurelliaperms.commands.PermsCommand;
import fr.naurellia.naurelliaperms.commands.SetRankCommand;
import fr.naurellia.naurelliaperms.completer.SetRankCommandCompleter;
import fr.naurellia.naurelliaperms.listeners.EventManager;
import fr.naurellia.naurelliaperms.utiles.Database;
import fr.naurellia.naurelliaperms.tools.PermissionsReader;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class NaurelliaPerms extends JavaPlugin {

    private static NaurelliaPerms plugin;
    private static final Logger logger = Logger.getLogger(NaurelliaPerms.class.getName());

    /**
     * @return plugin
     */
    public static NaurelliaPerms getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        logger.info("[NaurelliaPerms] -> NaurelliaPerms is starting...");

        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        Database.databaseTest();
        Database.close();

        this.getServer().getPluginManager().registerEvents(new EventManager(), this);
        this.getServer().getPluginManager().registerEvents(new PermissionsReader(), this);

        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.addAttachment(this).setPermission("naurellia.staff.admin", true);

        PermissionsReader.readPermissions();

        if (PermissionsReader.getPermissionsMap().isEmpty()) {
            logger.severe("[NaurelliaPerms] -> PermissionsMap is empty !");
        } else {
            logger.info("[NaurelliaPerms] -> PermissionsMap loaded !");
        }

        Objects.requireNonNull(getCommand("perms")).setExecutor(new PermsCommand());
        Objects.requireNonNull(getCommand("setrank")).setExecutor(new SetRankCommand());
        Objects.requireNonNull(getCommand("setrank")).setTabCompleter(new SetRankCommandCompleter());

        logger.info("[NaurelliaPerms] -> NaurelliaPerms is started !");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("[NaurelliaPerms] -> NaurelliaPerms is stopping...");

        Database.closeAll();

        logger.info("[NaurelliaPerms] -> NaurelliaPerms is stopped !");
    }
}

