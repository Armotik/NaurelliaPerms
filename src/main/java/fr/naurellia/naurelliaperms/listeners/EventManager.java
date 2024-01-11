package fr.naurellia.naurelliaperms.listeners;

import fr.naurellia.naurelliaperms.NaurelliaPerms;
import fr.naurellia.naurelliaperms.tools.PermissionsReader;
import fr.naurellia.naurelliaperms.utiles.Database;
import fr.naurellia.naurelliaperms.utiles.ExceptionsManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.xml.crypto.Data;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

public class EventManager implements Listener {

    private final Logger logger = Logger.getLogger(EventManager.class.getName());

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        player.addAttachment(NaurelliaPerms.getPlugin(), PermissionsReader.getPermission(player.getUniqueId()), true);

        player.updateCommands();
    }

    @EventHandler
    public void onPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {

        int res = 0;

        try (Connection conn = Database.getConnection()) {
            assert conn != null;
            try (Statement statement = conn.createStatement();
                 ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM `Players` WHERE `uuid` = '" + event.getUniqueId() + "'")) {

                while (result.next()) {
                    res = result.getInt(1);
                }

            }
        } catch (SQLException e) {
            ExceptionsManager.sqlException(e);
        }

        if (res <= 0) {

            Date date = new Date();
            String dateFormatted = new SimpleDateFormat("yyyy-MM-dd").format(date);

            try (Connection conn = Database.getConnection();
                 PreparedStatement statement = conn.prepareStatement("INSERT INTO `Players` (`uuid`, `ign`, `first_connection`, is_online) VALUES ('" + event.getUniqueId() + "', '" + event.getName() + "', '" + dateFormatted + "', '1')")) {

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated <= 0) {
                    logger.severe("[NaurelliaPerms] -> " + event.getName() + " has not been updated in the database !");
                }

            } catch (SQLException e) {
                ExceptionsManager.sqlException(e);
            }

            try (Connection conn = Database.getConnection();
                 PreparedStatement statement = conn.prepareStatement("INSERT INTO `PlayersPermissions` (`player_uuid`, `permission`) VALUES ('" + event.getUniqueId() + "', 'naurellia.player.player')")) {

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated <= 0) {
                    logger.severe("[NaurelliaPerms] -> " + event.getName() + " has not been updated in the database !");
                }

            } catch (SQLException e) {
                ExceptionsManager.sqlException(e);
            }

            PermissionsReader.setPermission(event.getUniqueId(), "naurellia.player.player");

            return;
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement statement = conn.prepareStatement("UPDATE `Players` SET `is_online` = '1' WHERE `uuid` = '" + event.getUniqueId() + "'")) {

            int rowsUpdated = statement.getUpdateCount();

            if (rowsUpdated <= 0) {
                logger.severe("[NaurelliaPerms] -> " + event.getName() + " has not been updated in the database !");
            }

        } catch (SQLException e) {
            ExceptionsManager.sqlException(e);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        try (Connection conn = Database.getConnection()) {

            assert conn != null;
            try (PreparedStatement statement = conn.prepareStatement("UPDATE `Players` SET `is_online` = '0' WHERE `uuid` = '" + player.getUniqueId() + "'")) {

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated <= 0) {
                    logger.severe("[NaurelliaPerms] -> " + player.getName() + " has not been updated in the database !");
                }
            }

        } catch (SQLException e) {
            ExceptionsManager.sqlException(e);
        }
    }
}
