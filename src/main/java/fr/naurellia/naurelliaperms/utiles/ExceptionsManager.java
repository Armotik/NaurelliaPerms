package fr.naurellia.naurelliaperms.utiles;

import java.sql.SQLException;
import java.util.logging.Logger;

public class ExceptionsManager {

    private static final Logger logger = Logger.getLogger(ExceptionsManager.class.getName());

    private ExceptionsManager() {
        throw new IllegalStateException("Utility class");
    }

    private static void getStackTrace(StackTraceElement[] stackTraceElements) {

        for (StackTraceElement stackTraceElement : stackTraceElements) {

            logger.info(stackTraceElement + "\n");
        }
    }

    public static void sqlException(SQLException e) {

        logger.severe("SQLException \nError Code N°" + e.getErrorCode() +  "\nSQLState : " + e.getSQLState() + "\nMessage : " +e.getMessage() + "\n");
        getStackTrace(e.getStackTrace());
    }


}
