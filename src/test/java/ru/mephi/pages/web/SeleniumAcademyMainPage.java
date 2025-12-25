package ru.mephi.pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumAcademyMainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "h1")
    private WebElement mainHeading;

    @FindBy(css = "div.wp-block-uagb-container.uagb-block-6edaf1d4")
    private WebElement mainContainer;

    @FindBy(linkText = "Log In")
    private WebElement loginLink;

    @FindBy(css = "a[href*='login']")
    private WebElement loginLinkAlternative;

    public SeleniumAcademyMainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        driver.get(url);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOf(mainContainer),
                ExpectedConditions.visibilityOf(mainHeading)
        ));
    }

    public boolean isMainHeadingDisplayed() {
        try {
            return mainHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getMainHeadingText() {
        wait.until(ExpectedConditions.visibilityOf(mainHeading));
        return mainHeading.getText();
    }

    public boolean isMainContainerDisplayed() {
        try {
            return mainContainer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clickLoginLink() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(loginLink));
            loginLink.click();
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(loginLinkAlternative));
            loginLinkAlternative.click();
        }
    }

    public boolean isLoginLinkDisplayed() {
        try {
            return loginLink.isDisplayed() || loginLinkAlternative.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
