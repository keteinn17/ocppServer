//package eu.chargetime.ocpp.jsonserverimplementation.config;
//
//import org.jooq.impl.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//
//import javax.sql.DataSource;
//
//public class JooqConfiguration {
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public DataSourceConnectionProvider connectionProvider() {
//        return new DataSourceConnectionProvider
//                (new TransactionAwareDataSourceProxy(dataSource));
//    }
//
//    @Bean
//    public DefaultDSLContext dsl() {
//        return new DefaultDSLContext(configuration());
//    }
//
//    public DefaultConfiguration configuration() {
//        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
//        jooqConfiguration.set(connectionProvider());
//        jooqConfiguration
//                .set(new DefaultConnectionProvider());
//
//        return jooqConfiguration;
//    }
//}
