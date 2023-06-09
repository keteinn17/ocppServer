package eu.chargetime.ocpp.jsonserverimplementation.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum DatabaseConfiguration {
    CONFIG;

    private final DB db;
    private final Url url;
    private final ChargerBox chargerBox;
    DatabaseConfiguration(){
        PropertiesFileLoader p = new PropertiesFileLoader("application.properties");

        db = DB.builder()
                .ip("db.ip")
                .port(p.getInt("db.port"))
                .schema(p.getString("db.schema"))
                .userName(p.getString("spring.datasource.username"))
                .password(p.getString("spring.datasource.password"))
                .sqlLogging(p.getBoolean("db.sql.logging"))
                .build();
        url=Url.builder().url(p.getString("spring.datasource.url")).build();
        chargerBox=new ChargerBox();
    }
    @Builder
    @Getter
    public static class DB {
        private final String ip;
        private final int port;
        private final String schema;
        private final String userName;
        private final String password;
        private final boolean sqlLogging;
    }

    @Builder
    @Getter
    public static class Url{
        private final String url;
    }

    @Getter
    @Setter
    public static class ChargerBox{
        private String chargeBox;
        public ChargerBox() {

        }
        public ChargerBox(String chargeBox){
            this.chargeBox =chargeBox;
        }
    }
}
