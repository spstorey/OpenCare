package spssoftware.opencare.config;

import org.jooq.SQLDialect;

public abstract class Config {

	public String getOwner() {
		return "SPS Software Ltd";
	}

	public abstract String getApplicationRootUrl();

	public int getApplicationPort() {
		return 8888;
	}

	public String getDatabaseUsername() {
		return "sa";
	}

	public String getDatabasePassword() {
		return "";
	}

	public abstract String getDatabaseUrl();

	public String getDatabaseDriver() {
		return "org.h2.Driver";
	}

	public SQLDialect getDatabaseDialect() {
		return SQLDialect.H2;
	}

	public String getDatabaseSchema() {
		return "OPENCARE";
	}
}