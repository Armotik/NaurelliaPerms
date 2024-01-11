package fr.naurellia.naurelliaperms.utiles;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.naurellia.naurelliaperms.NaurelliaPerms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class Database {

    private static final Logger logger = Logger.getLogger(Database.class.getName());
    private static HikariDataSource dataSource;
    private static final List<String> databaseStringList = NaurelliaPerms.getPlugin().getConfig().getStringList("DATABASE");

    private Database() {
        throw new IllegalStateException("Utility class");
    }

    private static synchronized void init() {

        if (dataSource != null && !dataSource.isClosed()) return;

        try {

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(databaseStringList.get(0));
            config.setUsername(databaseStringList.get(1));
            config.setPassword(databaseStringList.get(2));
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setMinimumIdle(10);
            config.setMaximumPoolSize(100);
            config.setMaxLifetime(30000L);

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {

            logger.severe("Error initializing the database connection pool \n" + e);
        }
    }

    public static Connection getConnection() {

        init();

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            ExceptionsManager.sqlException(e);
            return null;
        }
    }

    public static ResultSet executeQuery(String sqlQuery) {
        Connection conn = getConnection();
        PreparedStatement statement = null;
        ResultSet result = null;

        try {

            if (conn != null && !conn.isClosed()) {

                statement = conn.prepareStatement(sqlQuery);
                result = statement.executeQuery();
            } else {

                logger.warning("[NaurelliaPerms] -> Database.executeQuery() -> Connection is null or closed !");
            }

            return result;

        } catch (SQLException e) {
            ExceptionsManager.sqlException(e);
            return null;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {

                ExceptionsManager.sqlException(e);
            }
        }
    }

    public static int executeInsert(String sqlQuery) {

        Connection conn = getConnection();
        PreparedStatement statement = null;

        try {
            assert conn != null;
            statement = conn.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            ExceptionsManager.sqlException(e);
            return -1;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {

                ExceptionsManager.sqlException(e);
            }
        }
    }

    public static int executeUpdate(String sqlQuery) {

        Connection conn = getConnection();
        PreparedStatement statement = null;

        try {
            assert conn != null;
            statement = conn.prepareStatement(sqlQuery);
            return statement.executeUpdate();

        } catch (SQLException e) {
            ExceptionsManager.sqlException(e);
            return -1;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {

                ExceptionsManager.sqlException(e);
            }
        }
    }

    public static void close() {

        if (dataSource != null && !dataSource.isClosed()) {

                dataSource.close();
        }

    }

    public static void closeAll() {


        if (dataSource == null) return;

        dataSource.close();
    }

    /**
     * Test de la connexion à la base de données
     */
    public static void databaseTest() {

        Connection conn = getConnection();

        PreparedStatement statement = null;

        try {
            assert conn != null;
            statement = conn.prepareStatement("SELECT 2+2 as solution");
            ResultSet res = statement.executeQuery();

            if (res.next()) {

                assert res.getInt("solution") == 4;
                logger.info("[NaurelliaPerms] -> Database -  Database test : OK !");

                res.close();
            }

        } catch (SQLException e) {
            ExceptionsManager.sqlException(e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {

                ExceptionsManager.sqlException(e);
            }
        }
    }
}
