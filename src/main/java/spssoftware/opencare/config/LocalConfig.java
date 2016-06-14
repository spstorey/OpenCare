package spssoftware.opencare.config;

import org.jooq.SQLDialect;

public class LocalConfig extends Config {

    public String getApplicationRootUrl() {
        return "http://localhost:" + getApplicationPort();
    }

    @Override
    public String getDatabaseUsername() {
        return "sa";
    }

    @Override
    public String getDatabasePassword() {
        return "";
    }

    @Override
    public String getDatabaseUrl() {
        return "jdbc:h2:./target/runtime-db;AUTO_SERVER=TRUE;CACHE_SIZE=131072;MVCC=true";
    }

    @Override
    public String getDatabaseDriver() {
        return "org.h2.Driver";
    }

    @Override
    public SQLDialect getDatabaseDialect() {
        return SQLDialect.H2;
    }
}