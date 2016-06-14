package spssoftware.opencare.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	@Bean
	public HikariDataSource dataSource(Config config) {

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(config.getDatabaseUrl());
		hikariConfig.setUsername(config.getDatabaseUsername());
		hikariConfig.setPassword(config.getDatabasePassword());
		hikariConfig.setDriverClassName(config.getDatabaseDriver());
		hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;
	}

	@Bean
	public DataSourceTransactionManager transactionManager(Config config) {
		return new DataSourceTransactionManager(dataSource(config));
	}

	@Bean
	public TransactionAwareDataSourceProxy transactionAwareDataSource(Config config) {
		return new TransactionAwareDataSourceProxy(dataSource(config));
	}

	@Bean
	public DataSourceConnectionProvider connectionProvider(Config config) {
		return new DataSourceConnectionProvider(transactionAwareDataSource(config));
	}

	@Bean
	public DefaultConfiguration dbConfig(Config config) {
		DefaultConfiguration dbConfig = new DefaultConfiguration();
		dbConfig.set(config.getDatabaseDialect());
		dbConfig.set(connectionProvider(config));
		return dbConfig;
	}

	@Bean
	public DSLContext dsl(Config config) {
		return new DefaultDSLContext(dbConfig(config));
	}
}