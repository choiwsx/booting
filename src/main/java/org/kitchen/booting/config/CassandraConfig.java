//package org.kitchen.booting.config;
//
//import com.datastax.driver.core.Cluster;
//import com.datastax.driver.core.QueryLogger;
//import com.datastax.oss.driver.api.core.CqlSession;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//@Configuration
//public class CassandraConfig {
//
//    //     * Use the standard Cassandra driver API to create a com.datastax.oss.driver.api.core.CqlSession instance.
//    @Bean
//    public Cluster cluster() {
//        try {
//            return Cluster.builder().addContactPoints(InetAddress.getLocalHost()).build();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public @Bean
//    CqlSession session(Cluster cluster) {
//        return CqlSession.builder().withKeyspace("kitchen").build();
//    }
//
//    @Bean
//    public QueryLogger queryLogger(Cluster cluster) {
//        QueryLogger queryLogger = QueryLogger.builder(cluster).build();
//        cluster.register(queryLogger);
//        return queryLogger;
//    }
//}