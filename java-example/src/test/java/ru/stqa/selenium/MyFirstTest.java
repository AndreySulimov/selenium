package ru.stqa.selenium;

import org.junit.Test;
//import org.junit.jupiter.api.Test; // При использовании Web Driver Factory для параллельного запуска тестов
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest extends TestBase {

  @Test
  public void myFirstTest() {
    driver.get("https://www.google.ru/");
    driver.findElement(By.name("q")).sendKeys("Hello, world!");
    driver.findElement(By.id("tsf")).submit();
    wait.until(titleIs("Hello, world! - Поиск в Google"));
  }
}