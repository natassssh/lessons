package ru.academits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class OnlineStore {

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

        String url = "https://demowebshop.tricentis.com/";

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


    @ParameterizedTest
    @ValueSource(strings = {"Laptop", "Smartphone", "Fiction"})
    public void testAddToCart  (String searchQuery) throws InterruptedException {
        // Ввод поискового запроса
        WebElement searchBox = driver.findElement(By.xpath("//*[@id='small-searchterms']"));
        searchBox.sendKeys(searchQuery);
        searchBox.sendKeys(Keys.ENTER);

        // Нажатие кнопки "Add to cart" для первого товара и сохранение наименования товара
        WebElement addToCart = driver.findElement(By.xpath("//*[@value='Add to cart'][1]"));
        Thread.sleep(2000);
        String expectedItem = driver.findElement(By.xpath("//*[@class='item-box'][1]//h2/a")).getText();
        addToCart.click();
        Thread.sleep(2000);

        // Переход в корзину
        WebElement cart = driver.findElement(By.xpath("//*[contains(text(), 'Shopping cart')]"));
        Thread.sleep(2000);
        cart.click();

        // Проверка наличия в корзине добавленного товара
        WebElement elem = driver.findElement(By.xpath("//tbody/tr[1]//a"));
        Assertions.assertEquals(expectedItem, elem.getText(), "Товар не найден в корзине");
    }
}
