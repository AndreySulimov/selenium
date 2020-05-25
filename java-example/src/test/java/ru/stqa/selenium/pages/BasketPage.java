package ru.stqa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class BasketPage extends Page {

  public BasketPage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    driver.findElement(By.cssSelector("a.link[href *= checkout]")).click();
  }

  public void removeAllProducts() {
    // получаем все элементы, соответствующие кнопке удаления товара, и сохраняем их в список
    List<WebElement> buttons = driver.findElements(By.cssSelector("button[value = Remove]"));

    /* удаляем товары из корзины (кликаем по кнопке Remove), пока их не останется,
    проверяя при этом обновление таблицы товаров в корзине */
    while (buttons.size() != 0) {
      WebElement oldTable = driver.findElement(By.cssSelector(".dataTable"));
      WebElement button = wait.until(visibilityOfElementLocated(By.cssSelector("button[value = Remove]")));
      button.click();
      wait.until(stalenessOf(oldTable));

      buttons = driver.findElements(By.cssSelector("button[value = Remove]"));
    }
  }
}