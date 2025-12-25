package ru.mephi.pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumAcademyLoginPage {
    private final WebDriverWait wait;

    @FindBy(id = "log")
    private WebElement usernameField;

    @FindBy(id = "pwd")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"wpmem_login_form\"]/fieldset/div[3]/input[2]")
    private WebElement submitButton;

    @FindBy(id = "wpmem_login_form")
    private WebElement loginForm;

    public SeleniumAcademyLoginPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void waitForLoginFormLoad() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOf(loginForm),
                ExpectedConditions.visibilityOf(usernameField)
        ));
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
    }

    public boolean isLoginFormDisplayed() {
        try {
            return loginForm.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUsernameFieldDisplayed() {
        try {
            return usernameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordFieldDisplayed() {
        try {
            return passwordField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSubmitButtonDisplayed() {
        try {
            return submitButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

