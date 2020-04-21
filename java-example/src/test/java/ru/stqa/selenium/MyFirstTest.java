package ru.stqa.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 10);
  }

  @Test
  public void myFirstTest() {
    driver.get("https://www.google.ru/");
    driver.findElement(By.cssSelector("span.hOoLGe")).click();
    driver.findElement(By.id("K32")).click();
    driver.findElement(By.name("q")).sendKeys("Hello, world!");
    driver.findElement(By.name("btnK")).click();
    wait.until(titleIs("Hello, world! - Поиск в Google"));
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }
}