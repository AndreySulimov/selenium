package ru.stqa.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;
import java.util.Set;

public class NewWindowsTest extends TestBase {
  @Test
  public void newWindowsTest() {
    driver.get("http://localhost/litecart/admin/login.php");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();

    // переходим на страницу со списком стран
    driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
    // начинаем создание новой страны (кликаем по кнопке)
    driver.findElement(By.cssSelector("a.button")).click();
    // находим все интересующие нас иконки, открывающие новые окна, и сохраняем в список
    List<WebElement> links = driver.findElements(By.cssSelector("i.fa.fa-external-link"));
    // проходимся в цикле по списку с иконками
    for (int i = 0; i < links.size(); i++) {
      // запоминаем идентификатор текущего окна
      String originalWindow = driver.getWindowHandle();
      // запоминаем идентификаторы уже открытых окон
      Set<String> oldWindows = driver.getWindowHandles();
      // обновляем список иконок, открывающих новые окна
      links = driver.findElements(By.cssSelector("i.fa.fa-external-link"));
      // кликаем по i-й иконке
      links.get(i).click();
      // ждем появления нового окна, с новым идентификатором, и сохраняем этот идентификатор
      String newWindow = wait.until(anyWindowOtherThan(oldWindows)); // метод anyWindowOtherThan() смотри ниже
      // переключаемся в новое окно
      driver.switchTo().window(newWindow);
      // получаем url и заголовок открывшейся в новом окне страницы и выводим на экран
      String pageUrl =  driver.getCurrentUrl();
      String pageTitle =  driver.getTitle();
      System.out.println(pageTitle + " - " + pageUrl);
      // закрываем новое окно
      driver.close();
      // возвращаемся в исходное окно
      driver.switchTo().window(originalWindow);
    }
  }

  // метод для ожидание появления нового окна
  public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
    return new ExpectedCondition<String>() {
      public String apply(WebDriver driver) {
        Set<String> handles = driver.getWindowHandles();
        handles.removeAll(oldWindows);
        return handles.size() > 0 ? handles.iterator().next() : null;
      }
    };
  }
}