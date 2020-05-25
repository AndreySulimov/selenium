package ru.stqa.selenium.tests;

import org.junit.Test;
//import org.junit.jupiter.api.Test; // При использовании Web Driver Factory для параллельного запуска тестов
import org.openqa.selenium.By;

public class AdminLoginTest extends TestBase {

  @Test
  public void adminLoginTest() {
    driver.get("http://localhost/litecart/admin/login.php");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();
    driver.findElement(By.cssSelector("div.notice.success")).click();
  }
}