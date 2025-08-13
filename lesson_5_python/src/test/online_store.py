import pytest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.firefox import GeckoDriverManager
from webdriver_manager.microsoft import EdgeChromiumDriverManager
import time

class TestOnlineStore:
    @pytest.fixture(autouse=True)
    def setup(self, request):
        # Получаем параметр браузера из командной строки
        browser = request.config.getoption("--browser")

        # Инициализация драйвера в зависимости от браузера
        if browser == "chrome":
            self.driver = webdriver.Chrome(ChromeDriverManager().install())
        elif browser == "firefox":
            self.driver = webdriver.Firefox(executable_path=GeckoDriverManager().install())
        elif browser == "edge":
            self.driver = webdriver.Edge(EdgeChromiumDriverManager().install())
        else:
            raise ValueError(f"Unsupported browser: {browser}")

        self.url = "https://demowebshop.tricentis.com/"
        self.driver.get(self.url)
        self.driver.maximize_window()

        print(f"Current URL = {self.driver.current_url}")
        assert self.driver.current_url == self.url

        yield

        # Закрытие браузера после теста
        if self.driver:
            self.driver.quit()

    @pytest.mark.parametrize("search_query", ["Laptop", "Smartphone", "Fiction"])
    def test_add_to_cart(self, search_query):
        # Ввод поискового запроса
        search_box = WebDriverWait(self.driver, 10).until(
            EC.presence_of_element_located((By.ID, "small-searchterms"))
        )
        search_box.send_keys(search_query)
        search_box.send_keys(Keys.ENTER)

        # Нажатие кнопки "Add to cart" для первого товара
        add_to_cart = WebDriverWait(self.driver, 10).until(
            EC.presence_of_element_located((By.XPATH, "(//input[@value='Add to cart'])[1]"))
        )

        # Сохранение названия товара
        expected_item = WebDriverWait(self.driver, 10).until(
            EC.presence_of_element_located((By.XPATH, "(//div[@class='item-box'])[1]//h2/a"))
        ).text

        # Прокрутка и клик
        self.driver.execute_script("arguments[0].scrollIntoView();", add_to_cart)
        add_to_cart.click()

        # Ожидание сообщения об успешном добавлении
        WebDriverWait(self.driver, 10).until(
            EC.visibility_of_element_located((By.CSS_SELECTOR, ".bar-notification.success"))
        )

        # Переход в корзину
        cart = WebDriverWait(self.driver, 10).until(
            EC.element_to_be_clickable((By.XPATH, "//a[contains(text(), 'Shopping cart')]"))
        )
        cart.click()

        # Проверка наличия товара в корзине
        actual_item = WebDriverWait(self.driver, 10).until(
            EC.presence_of_element_located((By.XPATH, "//tbody/tr[1]//a"))
        ).text

        assert actual_item == expected_item, f"Товар {expected_item} не найден в корзине"

def pytest_addoption(parser):
    parser.addoption(
        "--browser", action="store", default="chrome", help="browser to execute tests (chrome, firefox, edge)"
    )