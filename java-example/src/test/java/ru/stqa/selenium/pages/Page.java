package ru.stqa.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Page {

  protected WebDriver driver;
  protected WebDriverWait wait;

  public Page(WebDriver driver) {
    this.driver = driver;
    wait = new WebDriverWait(driver, 5); // явные ожидания
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // неявные ожидания
  }
}