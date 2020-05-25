package ru.stqa.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class WorkingWithBasketTest extends TestBase {

  @Test
  public void workingWithBasketTest() {
    driver.get("http://localhost/litecart/en/");

    // объявляем переменную для сохранения количества товаров в корзине
    int count = 0;
    // добавляем товары в корзину (на главной странице, по одному), пока их не станет 3
    while (count != 3) {
      wait.until(visibilityOfElementLocated(By.cssSelector("li[class *= product]"))).click();
      wait.until(visibilityOfElementLocated(By.name("add_cart_product"))).click();
      WebElement oldQuantity = driver.findElement(By.cssSelector("span.quantity"));

      /* из-за ошибки, возникающей при добавлении товара в корзину,
      счетчик не обновляется после добавления товара + отображается алерт об ошибке.
      Алерт перехватываем и закрываем (метод alertDissmiss), а для обновления счетчика - обновляем страницу.

      Ожидание исчезновения "старого" элемента и последующий поиск "нового" при этом используются,
      как если бы ошибки не было. */

      driver.navigate().refresh();
      wait.until(stalenessOf(oldQuantity));
      WebElement newQuantity = wait.until(visibilityOfElementLocated(By.cssSelector("span.quantity")));
      count = Integer.parseInt(newQuantity.getAttribute("textContent"));

      // переходим на главную страницу
      driver.findElement(By.cssSelector("a[href $= 'en/']")).click();
    }
    // переходим в корзину
    driver.findElement(By.cssSelector("a.link[href *= checkout]")).click();

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

  public void alertDissmiss() {
    try {
      driver.switchTo().alert().dismiss();
    } catch (NoAlertPresentException e) {
      // ничего не делаем, алерт отсутствует
    }
  }
}