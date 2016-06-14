package spssoftware.opencare.config;

public class LiveConfig extends Config {

    public String getApplicationRootUrl() {
        return "http://dozor.autotrader.co.uk";
    }

    @Override
    public String getDatabaseUrl() {
        return "jdbc:h2:./target/runtime-db;AUTO_SERVER=TRUE;CACHE_SIZE=131072;MVCC=true";
    }
}