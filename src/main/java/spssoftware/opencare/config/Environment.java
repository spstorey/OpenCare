package spssoftware.opencare.config;

public enum Environment {
	
	LOCAL(new LocalConfig()),
	LIVE(new LiveConfig());
	
	private final Config config;

	public static final String ENVIRONMENTAL_VARIABLE = "OPENCARE_ENVIRONMENT";

	public static Environment getEnvironment() {
		String environmentName = System.getenv(ENVIRONMENTAL_VARIABLE);
		return Environment.getByName(environmentName);
	}
	
	Environment(Config config) {
		this.config = config;
	}
	
	public Config getConfig() {
		return this.config;
	}
	
	public static Environment getByName(String environmentName) {
		for (Environment environment : Environment.values()) {
    		if (environment.name().equalsIgnoreCase(environmentName)) {
    			return environment;
    		}
    	}
		return LOCAL;
	}

}
