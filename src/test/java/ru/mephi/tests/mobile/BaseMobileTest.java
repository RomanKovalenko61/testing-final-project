package ru.mephi.tests.mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ru.mephi.utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseMobileTest {
    protected AndroidDriver driver;
    protected ConfigReader config;

    @BeforeClass
    public void setUp() throws MalformedURLException, InterruptedException {
        System.out.println("========================================");
        System.out.println("Cтартуем приложение википедии в эмуляторе...");
        System.out.println("========================================");

        config = new ConfigReader();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(config.getProperty("android.platform.name"));
        options.setPlatformVersion(config.getProperty("android.platform.version"));
        options.setDeviceName(config.getProperty("android.device.name"));
        options.setAppPackage(config.getProperty("android.app.package"));
        options.setAppActivity(config.getProperty("android.app.activity"));
        options.setAutomationName(config.getProperty("android.automation.name"));

        boolean noReset = Boolean.parseBoolean(config.getProperty("android.noReset"));
        boolean fullReset = Boolean.parseBoolean(config.getProperty("android.fullReset"));

        options.setNoReset(noReset);
        options.setFullReset(fullReset);
        options.setNewCommandTimeout(Duration.ofSeconds(300));

        options.setCapability("uiautomator2ServerInstallTimeout", 60000);
        options.setCapability("adbExecTimeout", 60000);

        String appiumUrl = config.getProperty("appium.server.url");

        System.out.println("Подключение к Appium: " + appiumUrl);
        System.out.println("Запуск приложения: " + options.getAppPackage());

        driver = new AndroidDriver(new URL(appiumUrl), options);
        Thread.sleep(2000);

        System.out.println("Приложение Wikipedia запущено");
        System.out.println("Текущая активность: " + driver.currentActivity());
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Тестирование приложения завершено.");
            System.out.println("========================================\n");
        }
    }
}