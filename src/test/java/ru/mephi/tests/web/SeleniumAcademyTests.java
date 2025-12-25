package ru.mephi.tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mephi.pages.web.SeleniumAcademyMainPage;
import ru.mephi.utils.ConfigReader;

public class SeleniumAcademyTests extends BaseWebTest {

    @Test(description = "Проверка открытия главной страницы Selenium Academy")
    public void testMainPageOpens() {
        SeleniumAcademyMainPage mainPage = new SeleniumAcademyMainPage(driver);

        mainPage.open(ConfigReader.getWebBaseUrl());
        mainPage.waitForPageLoad();

        String currentUrl = mainPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("selenium.academy"), "URL должен содержать 'selenium.academy'");

        System.out.println("Главная страница успешно открылась: " + currentUrl);
    }

    @Test(description = "Проверка отображения основных элементов страницы")
    public void testMainPageElements() {
        SeleniumAcademyMainPage mainPage = new SeleniumAcademyMainPage(driver);

        mainPage.open(ConfigReader.getWebBaseUrl());
        mainPage.waitForPageLoad();

        Assert.assertTrue(mainPage.isMainContainerDisplayed(), "Контейнер с основной инфой должен отображаться на странице");

        System.out.println("Основные элементы страницы отображаются корректно");
    }

    @Test(description = "Проверка заголовка страницы. Он довольно длинный")
    public void testPageTitle() {
        SeleniumAcademyMainPage mainPage = new SeleniumAcademyMainPage(driver);

        mainPage.open(ConfigReader.getWebBaseUrl());
        mainPage.waitForPageLoad();

        String pageTitle = mainPage.getPageTitle();

        Assert.assertNotNull(pageTitle, "Заголовок страницы не должен быть null");
        Assert.assertFalse(pageTitle.isEmpty(), "Заголовок страницы не должен быть пустым");

        System.out.println("Заголовок страницы: " + pageTitle);
    }

    @Test(description = "Проверка наличия главного заголовка на странице. Расположен на фоне картинки")
    public void testMainHeading() {
        SeleniumAcademyMainPage mainPage = new SeleniumAcademyMainPage(driver);

        mainPage.open(ConfigReader.getWebBaseUrl());
        mainPage.waitForPageLoad();

        Assert.assertTrue(mainPage.isMainHeadingDisplayed(), "Главный заголовок должен отображаться");

        String headingText = mainPage.getMainHeadingText();
        Assert.assertFalse(headingText.isEmpty(), "Текст главного заголовка не должен быть пустым");

        System.out.println("Главный заголовок: " + headingText);
    }
}

