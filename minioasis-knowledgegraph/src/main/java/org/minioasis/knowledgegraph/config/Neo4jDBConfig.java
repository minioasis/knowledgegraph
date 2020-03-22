package org.minioasis.knowledgegraph.config;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan({"org.minioasis.knowledgegraph"})
@Configuration
@EnableNeo4jRepositories(basePackages = "org.minioasis.knowledgegraph.repository")
@EnableTransactionManagement
public class Neo4jDBConfig {

    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(configuration(), "org.minioasis.knowledgegraph.domain");
    }

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
    	ConfigurationSource properties = new ClasspathConfigurationSource("ogm.properties");
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder(properties).build();
        return configuration;	
    }


    
    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }
    
}
