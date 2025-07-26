package ru.academits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("errorSummary")));

        SoftAssertions softAssert = new SoftAssertions();
        softAssert.assertThat(driver.findElement(By.xpath("//*[@id='form_1006']//ul/li[1]")).getText()).isEqualTo(expectedError1);
        softAssert.assertThat(driver.findElement(By.xpath("//*[@id='form_1006']//ul/li[2]")).getText()).isEqualTo(expectedError2);
        softAssert.assertThat(driver.findElement(By.xpath("//*[@id='form_1006']//ul/li[3]")).getText()).isEqualTo(expectedError3);
        softAssert.assertThat(driver.findElement(By.xpath("//*[@id='form_1006']//ul/li[4]")).getText()).isEqualTo(expectedError4);
        softAssert.assertAll();

    }
}