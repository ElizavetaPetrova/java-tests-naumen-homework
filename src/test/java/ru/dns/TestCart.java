package ru.dns;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestCart
{
    public FirefoxDriver driver;

    @Before
    public void setUp()
    {
        driver = new FirefoxDriver();
        driver.get("https://www.dns-shop.ru/");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @Test
    public void testAddItemToCart()
    {
        //добавляем товар в корзину
        AddItemToCart();

        //переходим в корзину
        driver.get("https://www.dns-shop.ru/cart/");

        //проверки
        WebElement element = driver.findElement(By.cssSelector(".cart-link__badge"));
        String count = element.getText();
        String expectedCount = "1";
        String msg = String.format("Got: %s, expected: %s", count, expectedCount);
        Assert.assertEquals(msg, count, expectedCount);
    }


    @Test
    public void deleteItemFromCart()
    {
        //добавляем товар в корзину
        AddItemToCart();

        //переходим в корзину
        driver.get("https://www.dns-shop.ru/cart/");

        //удаляем товар из корзины
        driver.findElement(By.cssSelector(".count-buttons__icon-minus")).click();

        //проверки
        WebElement element = driver.findElement(By.cssSelector(".empty-message__title-empty-cart"));
        String text = element.getText();
        String expectedText = "Корзина пуста";
        String msg = String.format("Got: %s, expected: %s", text, expectedText);
        Assert.assertEquals(msg, text, expectedText);
    }

    private void AddItemToCart() {
        //вводим товар в посик
        driver.findElement(By.xpath("(//input[@name='q'])[2]")).sendKeys("мышь");

        //нажимаем на поиск
        driver.findElement(By.cssSelector(".ui-input-search__buttons:nth-child(3) > .ui-input-search__icon_search")).click();

        //переходим в карточку товара
        driver.findElement(By.cssSelector(".catalog-products:nth-child(2) > .catalog-product:nth-child(1) > .catalog-product__name")).click();

        //нажимаем добавить товар в корзину
        driver.findElement(By.xpath("(//div[8]/div/button[2])")).click();

        //*[contains(@class, 'button-ui_passive-done')]
        WebDriverWait wait = new WebDriverWait(driver, '5');
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'button-ui_passive-done')]")));
    }

    @After
    public void close(){
        driver.quit();
    }
}
