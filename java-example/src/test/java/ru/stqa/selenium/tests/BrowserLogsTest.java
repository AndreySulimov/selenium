package ru.stqa.selenium.tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;

public class BrowserLogsTest extends TestBase {

  @Test
  public void browserLogsTest() {
    // авторизуемся под учетной записью администратора
    driver.get("http://localhost/litecart/admin/login.php");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();

    // переходим в каталог
    driver.findElement(By.cssSelector("[href *= catalog]")).click();
    // переходим в категорию, содержащую товары
    driver.findElement(By.cssSelector("[href $= 'catalog&category_id=1']")).click();

    // получаем все ссылки на все страницы товаров и сохраняем их в список
    List<WebElement> products = driver.findElements(By.cssSelector("[href *= 'product_id']:not([title='Edit'])"));

    // проходимся в цикле по полученному списку ссылок (последовательно открываем страницы товаров)
    for (int i = 0; i < products.size(); i++) {
      // обновляем список ссылок (нужно, т.к. страница будет перезагружаться)
      products = driver.findElements(By.cssSelector("[href *= 'product_id']:not([title='Edit'])"));
      // кликаем по ссылке
      products.get(i).click();
      // получаем название товара
      String name = driver.findElement(By.cssSelector("[name = 'name[en]'")).getAttribute("value");
      // выводим название товара на консоль
      System.out.println(name);
      // получаем логи браузера к текущему моменту и сохраняем в список
      List<LogEntry> logs = driver.manage().logs().get("browser").getAll();
      // если список логов не пуст, то выводим его на консоль
      if (logs.size() > 0) {
        System.out.println("Список логов при открытии страницы товара " + name + " : " + logs);
      }
      // проверяем, нет ли в логе браузера сообщений
      Assert.assertEquals(0, logs.size());
      // возвращаемся на предыдущую страницу (к списку товаров в категории)
      driver.navigate().back();
    }
  }
}