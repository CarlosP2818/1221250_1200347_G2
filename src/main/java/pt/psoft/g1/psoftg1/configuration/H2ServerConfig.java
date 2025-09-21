package pt.psoft.g1.psoftg1.configuration;

import lombok.extern.log4j.Log4j2;
import org.h2.tools.Server;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Log4j2
public class H2ServerConfig {

    // H2 TCP Server
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        log.info("Starting H2 TCP Server...");
        Server server = Server.createTcpServer(
                "-tcp", "-tcpAllowOthers", "-ifNotExists", "-baseDir", "./data"
        );
        log.info("H2 TCP Server started at: {}", server.getURL());
        return server;
    }

    // DataSource depends on H2 server
    @Bean
    @DependsOn("h2Server")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:tcp://localhost/psoft-g1;IGNORECASE=TRUE")
                .username("sa")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

    // H2 Web Console
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebConsole() throws SQLException {
        log.info("Starting H2 Web Console...");
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    }
}
