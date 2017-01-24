package spssoftware.opencare.cucumber;

import spssoftware.opencare.Application;

public class AppRunners {
    private static AppRunners instance;
    private static boolean appRunning = false;

    static AppRunners getInstance() {
        if (instance == null) {
            instance = new AppRunners();
        }
        return instance;
    }

    void startApp() {
        if (!appRunning) {
            Application.main();
        }

        appRunning = true;
    }
}