package ru.mephi.pages.modile;

import io.appium.java_client.android.AndroidDriver;

public abstract class BaseMobilePage {

    protected AndroidDriver driver;

    public BaseMobilePage(AndroidDriver driver) {
        this.driver = driver;
    }
}