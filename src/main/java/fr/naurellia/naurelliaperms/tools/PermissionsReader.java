package fr.naurellia.naurelliaperms.tools;

import fr.naurellia.naurelliaperms.utiles.Database;
import fr.naurellia.naurelliaperms.utiles.ExceptionsManager;
import org.bukkit.event.Listener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class PermissionsReader implements Listener {

    private static final Logger logger = Logger.getLogger(PermissionsReader.class.getName());
    private static final Map<UUID, String> permissionsMap = new HashMap<>();

    public static void readPermissions() {

        try (Connection conn = Database.getConnection();
             Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM `PlayersPermissions`")) {

            while (result.next()) {
                permissionsMap.put(UUID.fromString(result.getString("player_uuid")), result.getString("permission"));
            }

        } catch (SQLException e) {
            ExceptionsManager.sqlException(e);
        }
    }

    public static String getPermission(UUID uuid) {

        return permissionsMap.get(uuid);
    }

    public static void setPermission(UUID uuid, String permission) {

        if (permissionsMap.containsKey(uuid)) {
            permissionsMap.replace(uuid, permission);
        } else {
            permissionsMap.put(uuid, permission);
        }
    }

    public static Map<UUID, String> getPermissionsMap() {

        return permissionsMap;
    }
}
