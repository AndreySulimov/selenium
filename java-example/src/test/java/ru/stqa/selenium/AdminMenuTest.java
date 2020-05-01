package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class AdminMenuTest extends TestBase {
  @Test
  public void adminLoginTest() {
    driver.get("http://localhost/litecart/admin/login.php");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();

    // получаем список пунктов меню
    List<WebElement> itemsLevelOne = driver.findElements(By.cssSelector("#box-apps-menu > li"));
    // проходимся по каждому из элементов списка в цикле
    for (int i = 0; i < itemsLevelOne.size(); i++) {
      // обновляем список пунктов меню (нужно, т.к. страница перезагружается после каждого клика по пункту меню)
      itemsLevelOne = driver.findElements(By.cssSelector("#box-apps-menu > li"));
      // выбираем пункт меню с соответствующим номером в списке и кликаем по нему
      itemsLevelOne.get(i).click();
      // ждем, пока на странице появится элемент с тегом "h1", тем самым проверяя его наличие на страницах
      wait.until(presenceOfElementLocated(By.tagName("h1")));
      // получаем список вложенных пунктов меню для открытого на данный момент пункта меню
      List<WebElement> itemsLevelTwo = driver.findElements(By.cssSelector("#box-apps-menu > li#app- > ul.docs > li"));
      // если список вложенных пунктов меню не пуст, то...
      if (itemsLevelTwo.size() > 0) {
        /* проходимся по каждому из элементов списка в цикле,
        начиная со второго элемента списка, т.к. первый элемент выбирается автоматически
        при клике на основной пункт меню (элемент из списка itemsLevelOne) */
        for (int a = 1; a < itemsLevelTwo.size(); a++) {
          // обновляем список пунктов меню
          itemsLevelTwo = driver.findElements(By.cssSelector("#box-apps-menu > li#app- > ul.docs > li"));
          // выбираем вложенный пункт меню с соответствующим номером в списке и кликаем по нему
          itemsLevelTwo.get(a).click();
          // ждем, пока на странице появится элемент с тегом "h1", тем самым проверяя его наличие на страницах
          wait.until(presenceOfElementLocated(By.tagName("h1")));
        }
      }
    }
  }
}