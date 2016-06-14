package spssoftware.opencare.cucumber;

import spssoftware.opencare.App;
import spssoftware.opencare.config.Environment;

public class AppRunners {
    private static AppRunners instance;
    private static boolean appRunning = false;

    public static AppRunners getInstance() {
        if (instance == null) {
            instance = new AppRunners();
        }
        return instance;
    }

    public void startApp() {
        if (Environment.LOCAL.equals(Environment.getEnvironment()) && !appRunning) {
            App.main();
        }

        appRunning = true;
    }
}