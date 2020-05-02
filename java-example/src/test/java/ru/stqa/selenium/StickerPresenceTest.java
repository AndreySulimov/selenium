package ru.stqa.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class StickerPresenceTest extends TestBase {

  @Test
  public void stickerPresenceTest() {
    driver.get("http://localhost/litecart/en/");

    // получаем список всех уточек на странице
    List<WebElement> ducks = driver.findElements(By.cssSelector("li.product"));
    // выводим на экран размер полученного списка
    System.out.println("Found " + ducks.size() + " ducks:");
    // проходимся в цикле по каждому элементу полученного списка
    for (WebElement duck : ducks) {
      // получаем количество стикеров у элемента (уточка) и записываем в переменную
      int stickersCount = duck.findElements(By.cssSelector(".sticker")).size();
      // проверяем, что количество стикеров у элемента равно 1
      Assert.assertEquals(stickersCount, 1);
      // получаем и сохраняем свойства стикера и название уточки
      WebElement sticker = duck.findElement(By.cssSelector(".sticker"));
      WebElement name = duck.findElement(By.cssSelector("div.name"));
      // выводим на экран название уточки и текст на стикере
      System.out.println("* " + name.getText() + " with sticker " + sticker.getText());
    }
  }
}