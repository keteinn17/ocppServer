package eu.chargetime.ocpp.jsonserverimplementation.utils;

import eu.chargetime.ocpp.jsonserverimplementation.config.DatabaseConfiguration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static DSLContext context=null;

    public static DSLContext getConnection() throws SQLException {
        if(context==null){
            DatabaseConfiguration.DB database=DatabaseConfiguration.CONFIG.getDb();
            DatabaseConfiguration.Url url =DatabaseConfiguration.CONFIG.getUrl();
            Connection connection= DriverManager.getConnection(url.getUrl(),database.getUserName(),database.getPassword());
            context= DSL.using(connection, SQLDialect.MYSQL);
        }
        return context;
    }
}
