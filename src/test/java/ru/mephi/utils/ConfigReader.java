package ru.mephi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties;

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            properties = new Properties();
            if (input == null) {
                throw new RuntimeException("Не удалось найти файл config.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки конфигурации", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getWebBaseUrl() {
        return getProperty("web.base.url");
    }

    public static String getBrowser() {
        return getProperty("web.browser");
    }

    public static String getTestLoginEmail() {
        return getProperty("test.login.email");
    }

    public static String getTestLoginPassword() {
        return getProperty("test.login.password");
    }
}
