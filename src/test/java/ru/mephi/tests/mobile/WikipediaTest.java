package ru.mephi.tests.mobile;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mephi.pages.modile.WikipediaMainPage;

public class WikipediaTest extends BaseMobileTest {

    @Test(priority = 1, description = "Проверка что приложение Wikipedia запущено")
    public void testCheckAppStarted() throws InterruptedException {
        System.out.println("ТЕСТ 1: Проверка запуска приложения...");

        System.out.println("Ожидание загрузки приложения (1 сек)...");
        Thread.sleep(1000);

        System.out.println("Проверка 1: Название пакета");
        String packageName = driver.getCurrentPackage();
        System.out.println("   Текущий пакет: " + packageName);

        Assert.assertEquals(packageName, "org.wikipedia",
                "Должно быть запущено приложение Wikipedia");
        System.out.println("Пакет корректный: org.wikipedia");

        System.out.println("Проверка 2: Текущая активность");
        String currentActivity = driver.currentActivity();
        System.out.println("   Текущая активность: " + currentActivity);
        System.out.println("   Активность определена");

        System.out.println("Проверка 3: Доступность контекста");
        String context = driver.getContext();
        System.out.println("   Контекст: " + context);
        Assert.assertNotNull(context, "Контекст приложения должен быть доступен");
        System.out.println("   Контекст доступен");

        System.out.println("Проверка 4: Параметры экрана");
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        System.out.println("   Размер экрана: " + width + "x" + height);
        Assert.assertTrue(width > 0 && height > 0, "Размер экрана должен быть больше 0");
        System.out.println("   Экран доступен");

        System.out.println("\n" + "=".repeat(50));
        System.out.println("ВСЕ ПРОВЕРКИ ПРОЙДЕНЫ УСПЕШНО!");
        System.out.println("Приложение Wikipedia запущено корректно");
        System.out.println("=".repeat(50));
    }

    @Test(priority = 2, description = "Поиск статьи про язык программирования Java")
    public void testSearchJavaWithPageObject() throws InterruptedException {
        System.out.println("ТЕСТ 2: Поиск статьи про язык Java");

        WikipediaMainPage mainPage = new WikipediaMainPage(driver);

        System.out.println("    Шаг 1: Пропускаем онбординг (если есть)");
        mainPage.skip();

        System.out.println("    Шаг 2: Выполняем поиск 'Java'");
        mainPage.search("Java");

        System.out.println("    Шаг 3: Кликаем по второму результату (Java - язык программирования)");
        mainPage.clickSearchResult(1); // 0-based: 0 = первый, 1 = второй

        System.out.println("    Шаг 4: Проверяем что открыта статья про Java");
        boolean isJavaArticle = mainPage.isJavaArticleOpen();

        Assert.assertTrue(isJavaArticle,
                "Должна быть открыта статья про Java (язык программирования)");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("✅ ТЕСТ УСПЕШНО ЗАВЕРШЕН!");
        System.out.println("✅ Найдена и открыта статья про Java");
        System.out.println("✅ Подтверждено наличие характерного текста в статье");
        System.out.println("=".repeat(60));
    }
}