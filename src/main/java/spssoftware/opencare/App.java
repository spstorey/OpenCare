package spssoftware.opencare;

import com.google.common.collect.Maps;
import com.googlecode.flyway.core.Flyway;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import spssoftware.opencare.config.Config;
import spssoftware.opencare.config.Environment;

import java.util.Map;

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class App extends SpringApplication {

    public static void main(String... args) {
        SpringApplication application = new SpringApplication(App.class);

        if (Environment.LOCAL.equals(Environment.getEnvironment())) {
            Map<String, Object> properties = Maps.newHashMap();
            properties.put("endpoints.shutdown.enabled", true);
            application.setDefaultProperties(properties);
        }

        runFlywayMigrations();

        application.run(args);
    }

    public static void runFlywayMigrations() {
        Config config = getConfig();

        Flyway flyway = new Flyway();
        flyway.setDataSource(config.getDatabaseUrl(), config.getDatabaseUsername(), config.getDatabasePassword());
        flyway.setSchemas(config.getDatabaseSchema());
        flyway.setInitOnMigrate(true);
        flyway.setTable("CHANGELOG");
        flyway.migrate();
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new TomcatEmbeddedServletContainerFactory(getConfig().getApplicationPort());
    }

    @Bean
    public static Config getConfig() {
        return Environment.getEnvironment().getConfig();
    }

    @Bean
    public CloseableHttpClient getHttpClient() {

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setDefaultMaxPerRoute(20);
        cm.setMaxTotal(60);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000)
                .setSocketTimeout(30000)
                .build();

        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(cm)
                .build();
    }
}
