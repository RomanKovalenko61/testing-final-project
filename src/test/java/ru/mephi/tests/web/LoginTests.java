package ru.mephi.tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mephi.pages.web.SeleniumAcademyLoginPage;
import ru.mephi.pages.web.SeleniumAcademyMainPage;
import ru.mephi.utils.ConfigReader;

public class LoginTests extends BaseWebTest {

    @Test(priority = 0, description = "Проверка наличия кнопки Log In на главной странице")
    public void testLoginLinkPresence() {
        SeleniumAcademyMainPage mainPage = new SeleniumAcademyMainPage(driver);
        mainPage.open(ConfigReader.getWebBaseUrl());
        mainPage.waitForPageLoad();

        boolean loginLinkExists = mainPage.isLoginLinkDisplayed();

        Assert.assertTrue(loginLinkExists,"Ссылка Log In должна отображаться на главной странице");

        System.out.println("Ссылка Log In найдена на главной странице");
    }

    @Test(priority = 1, description = "Проверка отображения формы логина")
    public void testLoginFormElements() {
        SeleniumAcademyMainPage mainPage = new SeleniumAcademyMainPage(driver);
        mainPage.open(ConfigReader.getWebBaseUrl());
        mainPage.waitForPageLoad();

        mainPage.clickLoginLink();

        SeleniumAcademyLoginPage loginPage = new SeleniumAcademyLoginPage(driver);
        loginPage.waitForLoginFormLoad();

        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(),"Поле username/email должно отображаться");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(),"Поле password должно отображаться");
        Assert.assertTrue(loginPage.isSubmitButtonDisplayed(),"Кнопка Submit должна отображаться");

        System.out.println("Все элементы формы логина отображаются корректно");
    }

    @Test(priority = 2, description = "Проверка полного сценария логина: заполнение формы + отправка")
    public void testLoginFlow() {
        SeleniumAcademyMainPage mainPage = new SeleniumAcademyMainPage(driver);
        mainPage.open(ConfigReader.getWebBaseUrl());
        mainPage.waitForPageLoad();

        System.out.println("Шаг 1: Главная страница загружена");

        mainPage.clickLoginLink();

        System.out.println("Шаг 2: Кликнули на Log In");

        SeleniumAcademyLoginPage loginPage = new SeleniumAcademyLoginPage(driver);
        loginPage.waitForLoginFormLoad();

        Assert.assertTrue(loginPage.isLoginFormDisplayed() || loginPage.isUsernameFieldDisplayed(),
                "Форма логина должна отображаться");

        System.out.println("Шаг 3: Страница логина загружена");

        String email = ConfigReader.getTestLoginEmail();
        String password = ConfigReader.getTestLoginPassword();

        loginPage.enterUsername(email);
        System.out.println("Шаг 4 часть 1: Email введен: " + email);

        loginPage.enterPassword(password);
        System.out.println("Шаг 4 часть 2: Пароль введен");

        loginPage.clickSubmit();
        System.out.println("Шаг 5: Форма отправлена");
    }
}

