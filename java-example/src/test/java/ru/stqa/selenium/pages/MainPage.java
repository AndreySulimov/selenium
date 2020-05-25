package ru.stqa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class MainPage extends Page {

  public MainPage(WebDriver driver) {
    super(driver);
  }

  public void openByUrl() {
    driver.get("http://localhost/litecart/en/");
  }

  public void openByButton() {
    driver.findElement(By.cssSelector("a[href $= 'en/']")).click();
  }

  public void openFirstProductCard() {
    wait.until(visibilityOfElementLocated(By.cssSelector("li[class *= product]"))).click();
  }
}