package ru.mephi.tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mephi.pages.web.SeleniumAcademyMainPage;
import ru.mephi.utils.ConfigReader;

public class CoursesMenuTests extends BaseWebTest {

    @Test(priority = 0, description = "Проверка наличия меню Courses и пунктов выпадающего списка")
    public void testCoursesMenuPresence() {
        SeleniumAcademyMainPage mainPage = new SeleniumAcademyMainPage(driver);
        mainPage.open(ConfigReader.getWebBaseUrl());
        mainPage.waitForPageLoad();

        Assert.assertTrue(mainPage.isCoursesMenuDisplayed(),
                "Меню Courses должно отображаться на главной странице");
        System.out.println("Меню Courses найдено на главной странице");

        Assert.assertTrue(mainPage.isSeleniumCatalogLinkVisible(),
                "Ссылка Selenium Catalog должна быть видна в выпадающем меню");
        System.out.println("Ссылка Selenium Catalog найдена в выпадающем меню");

        Assert.assertTrue(mainPage.isAppiumCatalogLinkVisible(),
                "Ссылка Appium Catalog должна быть видна в выпадающем меню");
        System.out.println("Ссылка Appium Catalog найдена в выпадающем меню");
        System.out.println("Все элементы выпадающего меню Courses отображаются корректно");
    }

    @Test(priority = 1, description = "Проверка перехода на Selenium Catalog через выпадающее меню")
    public void testNavigationToSeleniumCatalog() {
        SeleniumAcademyMainPage mainPage = new SeleniumAcademyMainPage(driver);
        mainPage.open(ConfigReader.getWebBaseUrl());
        mainPage.waitForPageLoad();

        System.out.println("Шаг 1: Главная страница загружена");

        mainPage.clickSeleniumCatalog();

        System.out.println("Шаг 2: Клик на Selenium Catalog выполнен");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Ошибка при ожидании загрузки страницы Selenium Catalog", e);
        }

        String currentUrl = mainPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("selenium-catalog"), "URL должен содержать 'selenium-catalog'");
        Assert.assertEquals(currentUrl, "https://selenium.academy/selenium-catalog/",
                "Должны перейти на страницу Selenium Catalog");

        System.out.println("Шаг 3: Успешно перешли на Selenium Catalog: " + currentUrl);
    }

    @Test(priority = 2, description = "Проверка перехода на Appium Catalog через выпадающее меню")
    public void testNavigationToAppiumCatalog() {
        SeleniumAcademyMainPage mainPage = new SeleniumAcademyMainPage(driver);
        mainPage.open(ConfigReader.getWebBaseUrl());
        mainPage.waitForPageLoad();

        System.out.println("Шаг 1: Главная страница загружена");

        mainPage.clickAppiumCatalog();

        System.out.println("Шаг 2: Клик на Appium Catalog выполнен");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Ошибка при ожидании загрузки страницы Appium Catalog", e);
        }

        String currentUrl = mainPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("appium-catalog"), "URL должен содержать 'appium-catalog'");
        Assert.assertEquals(currentUrl, "https://selenium.academy/appium-catalog/",
                "Должны перейти на страницу Appium Catalog");
        System.out.println("Шаг 3: Успешно перешли на Appium Catalog: " + currentUrl);
    }
}