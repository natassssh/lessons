package ru.academits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import java.nio.file.Paths;


public class DemoQA {

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

        String url = "https://demoqa.com/automation-practice-form";

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
    @CsvSource({

            "Иван, Иванов, ivan@test.com, Male, 1234567890, 25, December, 2000, Maths, 'Sports, Reading, Music', test_image_1.jpg, Пушкина, NCR, Noida",
            "Мария, Петрова, maria@test.com, Female, 9876543210, 21, October, 2002, 'History, Hindi', Music, test_image_2.jpg, Советская, Uttar Pradesh, Agra",
            "Алексей, Сидоров, alex@test.com, Other, 5555555555, 09, March, 1995, 'Chemistry, Biology', Sports, test_image_3.jpg, Ленина, Rajasthan, Jaipur"
    })
    public void testPracticeForm  (String firstName, String lastName,
                                   String email, String gender, String mobile,
                                   String birthDay, String birthMonth, String birthYear,
                                   String subjects, String hobbies, String picture,
                                   String currentAddress, String state, String city) throws InterruptedException {

        // Заполнение полей ввода
        fillInputField("//*[@id='firstName']", firstName);
        fillInputField("//*[@id='lastName']", lastName);
        fillInputField("//*[@id='userEmail']", email);
        fillInputField("//*[@id='userNumber']", mobile);
        fillInputField("//*[@id='currentAddress']", currentAddress);

        // Заполнение радио-баттонов
        fillRadioButton(gender);

        // Заполнение даты рождения (пример)
        fillDatePicker("//*[@id='dateOfBirthInput']", birthDay, birthMonth, birthYear);

        // Заполнение автокомплитов
        fillAutocompleteField("//*[@id='subjectsInput']", subjects);
        // Заполнение чек-боксов
        selectCheckBoxField(hobbies);

        // Заполнение выпадающих списков
        fillDropDownList("//*[@id='state']", state);
        fillDropDownList("//*[@id='city']", city);

        // Добавление фотографии
        fillPicture(picture);

        // Отправка формы
        clickSubmit();

        // Проверка результатов
        verifySubmittedData("//tbody/tr[1]/td[2]",firstName + " " + lastName);
        verifySubmittedData("//tbody/tr[2]/td[2]", email);
        verifySubmittedData("//tbody/tr[3]/td[2]", gender);
        verifySubmittedData("//tbody/tr[4]/td[2]", mobile);
        verifySubmittedData("//tbody/tr[5]/td[2]", birthDay + " " + birthMonth + "," + birthYear);
        verifySubmittedData("//tbody/tr[6]/td[2]", subjects);
        verifySubmittedData("//tbody/tr[7]/td[2]", hobbies);
        verifySubmittedData("//tbody/tr[8]/td[2]", picture);
        verifySubmittedData("//tbody/tr[9]/td[2]", currentAddress);
        verifySubmittedData("//tbody/tr[10]/td[2]", state + " " + city);
    }

    private void fillInputField(String xpath, String value) {
        WebElement field = driver.findElement(By.xpath(xpath));
        field.clear();
        field.sendKeys(value);
    }

    private void fillRadioButton(String gender) {
        String xpath = String.format("//label[contains(text(),'%s')]", gender);
        driver.findElement(By.xpath(xpath)).click();
    }

    private void fillAutocompleteField(String xpath, String input) {
        WebElement autocomplete = driver.findElement(By.xpath(xpath));

        if (!input.isEmpty()) {
            String[] words = input.split(", ");

            for (String word : words) {
                autocomplete.sendKeys(word);
                autocomplete.sendKeys(Keys.ENTER);
            }
        }
    }

    private void selectCheckBoxField(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Предупреждение: передан пустой input, выбор чекбоксов пропущен");
            return; // Останавливаем выполнение метода
        }
        if (!input.isEmpty()) {
            String[] words = input.split(", ");

            for (String word : words) {
                String xpath = "//label[contains(text(),'" + word + "')]";
                driver.findElement(By.xpath(xpath)).click();
            }
        }
    }

    private void fillDatePicker(String xpath, String day, String month, String year) {
        WebElement datePicker = driver.findElement(By.xpath(xpath));
        datePicker.click();

        WebElement monthList = driver.findElement(By.xpath("//select[@class='react-datepicker__month-select']"));
        monthList.click();
        Select select1 = new Select(monthList);
        select1.selectByVisibleText(month);

        WebElement yearList = driver.findElement(By.xpath("//select[@class='react-datepicker__year-select']"));
        yearList.click();
        Select select2 = new Select(yearList);
        select2.selectByVisibleText(year);

        WebElement dayChoose = driver.findElement(By.xpath("//div[contains(@class,'react-datepicker__day--0" + day + "')][not(contains(@class, 'outside-month'))]"));
        dayChoose.click();
    }

    private void fillDropDownList(String xpath, String option) {
        WebElement dropDown = driver.findElement(By.xpath(xpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropDown);
        dropDown.click();
        driver.findElement(By.xpath("//div[contains(text(),'" + option + "')]")).click();
    }

    public void fillPicture(String file) {
        String filePath = Paths.get("src/test/resources/" + file).toAbsolutePath().toString();
        WebElement fileInput = driver.findElement(By.xpath("//*[@id='uploadPicture']"));
        fileInput.sendKeys(filePath);
    }

    private void clickSubmit() {
        driver.findElement(By.xpath("//*[@id='submit']")).click();
    }

    private void verifySubmittedData(String xpath, String expectedText) {
        WebElement elem = driver.findElement(By.xpath(xpath));
        Assertions.assertEquals(expectedText, elem.getText(), "Значение не совпадает с ожидаемым");

    }
}

