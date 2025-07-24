package ru.academits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class OrderForm {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser");

        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        String url = "https://formdesigner.ru/examples/order.html";

        driver.get(url);
        driver.manage().window().maximize();

        System.out.println("Current url = " + driver.getCurrentUrl());
        Assertions.assertEquals(url, driver.getCurrentUrl());
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void checkErrorText() throws InterruptedException {
        // Закрыть всплывающее окно cookie
        WebElement accept = driver.findElement(By.xpath("//button[contains(text(),'Принять все')]"));
        accept.click();

        // Скролл к форме
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        new Actions(driver).scrollToElement(iframe).perform();

        // Переключение на фрейм
        driver.switchTo().frame(iframe);

        // Нажать кнопку "Отправить"
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Отправить')]"));
        submitButton.click();

        // Проверить текст ошибки
        String expectedError1 = "Необходимо заполнить поле ФИО:.";
        String expectedError2 = "Необходимо заполнить поле E-mail.";
        String expectedError3 = "Необходимо заполнить поле Количество.";
        String expectedError4 = "Необходимо заполнить поле Дата доставки.";

        verifyErrorText(expectedError1, 1);
        verifyErrorText(expectedError2, 2);
        verifyErrorText(expectedError3, 3);
        verifyErrorText(expectedError4, 4);

    }

    private void verifyErrorText(String expectedError, int i) {
        WebElement error = driver.findElement(By.xpath("//*[@id='form_1006']//ul/li[" + i + "]"));
        Assertions.assertEquals(expectedError, error.getText(), "Текст ошибки валидации не совпадает");
    }

}