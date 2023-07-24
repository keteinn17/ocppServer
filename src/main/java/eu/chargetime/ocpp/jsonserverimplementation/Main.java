package eu.chargetime.ocpp.jsonserverimplementation;//package eu.chargetime.ocpp.jsonserverimplementation;

import eu.chargetime.ocpp.jsonserverimplementation.web.controller.ServerCoreProfileConfig;

public class Main {
    public static void main(String[] args) {
        /*String userName = "root";
        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/stevedb";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            System.out.println("Ok");
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            int res = Integer.parseInt(String.valueOf(create.select(CHARGE_BOX.CHARGE_BOX_ID).from(CHARGE_BOX).where(CHARGE_BOX.CHARGE_BOX_ID.equal("a")).execute()));
            System.out.println(res);
        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            System.out.println("Wrong");
            System.out.println(e.getMessage());
        }finally {

        }*/
        ServerCoreProfileConfig serverCoreProfileConfig = new ServerCoreProfileConfig();
        System.out.println(serverCoreProfileConfig);
    }
}
