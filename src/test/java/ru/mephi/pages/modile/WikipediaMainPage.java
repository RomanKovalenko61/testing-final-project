package ru.mephi.pages.modile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WikipediaMainPage extends BaseMobilePage {

    private static final By SKIP_BUTTON = AppiumBy.id("org.wikipedia:id/fragment_onboarding_skip_button");
    private static final By SEARCH_CONTAINER = AppiumBy.id("org.wikipedia:id/search_container");
    private static final By SCROLLVIEW_POPUP = AppiumBy.className("android.widget.ScrollView");
    private static final By DIALOG_CONTAINER = AppiumBy.id("org.wikipedia:id/dialogContainer");
    private static final By ARTICLE_POPUP_CONTAINER = AppiumBy.id("org.wikipedia:id/container");
    private static final By CLOSE_BY_ACCESSIBILITY_ID = AppiumBy.accessibilityId("Close");
    private static final By CLOSE_BY_ID = AppiumBy.id("org.wikipedia:id/closeButton");
    private static final By SEARCH_RESULTS = AppiumBy.id("org.wikipedia:id/page_list_item_title");
    private static final By JAVA_ARTICLE_TEXT =
            AppiumBy.xpath("//android.widget.TextView[@text=' Java applications are typically compiled to ']");

    public WikipediaMainPage(AndroidDriver driver) {
        super(driver);
    }

    public WikipediaMainPage skip() {
        List<WebElement> elements = driver.findElements(SKIP_BUTTON);
        if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
            elements.get(0).click();
            System.out.println("Найден и нажат Skip");
            sleep(2500);
        } else {
            System.out.println("Skip не найден, продолжаем...");
        }
        return this;
    }

    private boolean clickIfPresent(By locator, String description) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                elements.get(0).click();
                System.out.println("Закрыто всплывающее окно " + description);
                sleep(200);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Элемент не найден или не кликабелен");
        }
        return false;
    }

    public WikipediaMainPage closeDialogContainerIfPresent() {
        List<WebElement> dialogs = driver.findElements(DIALOG_CONTAINER);
        if (!dialogs.isEmpty() && dialogs.get(0).isDisplayed()) {
            System.out.println("Найден dialogContainer ");
            if (clickIfPresent(CLOSE_BY_ACCESSIBILITY_ID, "dialogContainer by accessibility 'Close'")) {
                return this;
            }
            if (clickIfPresent(CLOSE_BY_ID, "dialogContainer by closeButton id")) {
                return this;
            }

            System.out.println("dialogContainer найден, но кнопка Close не найдена");
        }
        return this;
    }

    public WikipediaMainPage closeScrollViewPopupIfPresent() {
        List<WebElement> scrollViews = driver.findElements(SCROLLVIEW_POPUP);
        if (!scrollViews.isEmpty() && scrollViews.get(0).isDisplayed()) {
            System.out.println("Найден ScrollView");
            WebElement scrollView = scrollViews.get(0);
            try {
                List<WebElement> buttons = scrollView.findElements(AppiumBy.className("android.widget.Button"));
                System.out.println("Найдено кнопок в ScrollView: " + buttons.size());

                if (buttons.size() >= 2) {
                    WebElement targetButton = buttons.get(1);
                    System.out.println("Пробуем кликнуть по второй кнопке (index=1)...");

                    int x = targetButton.getLocation().getX() + (targetButton.getSize().getWidth() / 2);
                    int y = targetButton.getLocation().getY() + (targetButton.getSize().getHeight() / 2);

                    new io.appium.java_client.TouchAction<>(driver)
                            .tap(io.appium.java_client.touch.offset.PointOption.point(x, y))
                            .perform();

                    System.out.println("ScrollView закрыт кликом по кнопке index=1 (координаты: " + x + "," + y + ")");
                    sleep(500);
                    return this;
                }
            } catch (Exception e) {
                System.out.println("Не удалось кликнуть по кнопке index=1: " + e.getMessage());
            }

            System.out.println("Закрываем ScrollView через Back...");
            driver.navigate().back();
            sleep(500);

            List<WebElement> stillThere = driver.findElements(SCROLLVIEW_POPUP);
            if (stillThere.isEmpty() || !stillThere.get(0).isDisplayed()) {
                System.out.println("ScrollView успешно закрыт через Back");
            } else {
                System.out.println("ScrollView всё ещё виден после Back");
            }
        }
        return this;
    }

    public WikipediaMainPage closeArticlePopupIfPresent() {
        List<WebElement> containers = driver.findElements(ARTICLE_POPUP_CONTAINER);
        if (!containers.isEmpty() && containers.get(0).isDisplayed()) {
            System.out.println("Найден попап container при открытии статьи");

            driver.navigate().back();
            sleep(500);

            containers = driver.findElements(ARTICLE_POPUP_CONTAINER);
            if (containers.isEmpty() || !containers.get(0).isDisplayed()) {
                System.out.println("container закрыт через Back");
            } else {
                System.out.println("container всё ещё виден");
            }
        }
        return this;
    }

    public WikipediaMainPage closePopupsIfPresent() {
        System.out.println("Проверка и закрытие всплывающих окон...");
        closeDialogContainerIfPresent();
        closeScrollViewPopupIfPresent();
        closeArticlePopupIfPresent();
        return this;
    }

    public WikipediaMainPage ensureOnMainPage() {
        System.out.println("Проверка: на главном экране?");

        sleep(500);

        List<WebElement> searchContainers = driver.findElements(SEARCH_CONTAINER);
        if (!searchContainers.isEmpty() && searchContainers.get(0).isDisplayed()) {
            System.out.println("Мы на главном экране (search_container виден)");
            return this;
        }

        System.out.println("search_container не найден, пытаемся вернуться...");

        for (int i = 0; i < 3; i++) {
            driver.navigate().back();
            sleep(1000); // Увеличено с 500

            searchContainers = driver.findElements(SEARCH_CONTAINER);
            if (!searchContainers.isEmpty() && searchContainers.get(0).isDisplayed()) {
                System.out.println("Вернулись на главный экран (попытка " + (i + 1) + ")");
                return this;
            }
        }

        System.out.println("Не удалось вернуться на главный экран!");
        return this;
    }

    public WikipediaMainPage search(String text) {
        System.out.println("Начинаем поиск: '" + text + "'");

        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        By searchTextView = AppiumBy.xpath("//android.widget.TextView[@text='Search Wikipedia']");
        WebElement searchElement = null;

        try {
            searchElement = shortWait.until(ExpectedConditions.visibilityOfElementLocated(searchTextView));
            System.out.println("Найден TextView 'Search Wikipedia'");
        } catch (Exception e) {
            System.out.println("TextView не найден, пробуем search_container...");
            searchElement = shortWait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_CONTAINER));
        }

        searchElement.click();
        System.out.println("Клик по элементу поиска");
        sleep(500);

        closePopupsIfPresent();

        By searchInput = AppiumBy.id("org.wikipedia:id/search_src_text");
        System.out.println("Поиск search_src_text (AutoCompleteTextView)...");

        WebElement inputField = null;
        try {
            inputField = shortWait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
            System.out.println("search_src_text найден");
        } catch (Exception e) {
            System.out.println("search_src_text не найден после первой попытки");

            System.out.println("Повторный клик по Search Wikipedia...");
            try {
                searchElement = shortWait.until(ExpectedConditions.visibilityOfElementLocated(searchTextView));
                searchElement.click();
                sleep(500);

                inputField = shortWait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
                System.out.println("search_src_text найден после повторного клика");
            } catch (Exception ex) {
                throw new RuntimeException("Не удалось найти поле поиска search_src_text: " + ex.getMessage());
            }
        }

        inputField.sendKeys(text);
        System.out.println("Текст '" + text + "' введен в search_src_text");
        sleep(800);

        return this;
    }

    public WikipediaMainPage clickSearchResult(int index) {
        System.out.println("Клик по результату поиска #" + (index + 1));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> results = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(SEARCH_RESULTS)
        );

        if (results.size() <= index) {
            throw new RuntimeException("Недостаточно результатов поиска. Найдено: " + results.size());
        }

        String resultText = results.get(index).getText();
        System.out.println("Выбран результат: " + resultText);

        results.get(index).click();
        sleep(1500);

        System.out.println("Закрытие попапов после открытия статьи...");
        closePopupsIfPresent();
        sleep(300);

        System.out.println("Клик по экрану для убирания подсказок...");
        tapScreenCenter();
        sleep(300);
        tapScreenCenter();
        sleep(300);

        System.out.println("Статья открыта, попапы и подсказки убраны");

        return this;
    }

    public boolean isJavaArticleOpen() {
        System.out.println("Проверка: это статья про Java?");

        try {
            // Ищем характерный текст из статьи про Java
            List<WebElement> elements = driver.findElements(JAVA_ARTICLE_TEXT);
            if (!elements.isEmpty()) {
                System.out.println("Найден текст: 'Java applications are typically compiled to'");
                System.out.println("Это статья про Java!");
                return true;
            }
        } catch (Exception e) {
            // Игнорируем ошибки
        }

        System.out.println("Характерный текст статьи про Java не найден");
        return false;
    }

    private void tapScreenCenter() {
        try {
            int width = driver.manage().window().getSize().getWidth();
            int height = driver.manage().window().getSize().getHeight();

            new io.appium.java_client.TouchAction<>(driver)
                    .tap(io.appium.java_client.touch.offset.PointOption.point(width / 2, height / 2))
                    .perform();

            System.out.println("Клик по центру экрана");
            sleep(200);
        } catch (Exception e) {
            // Игнорируем ошибки
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}