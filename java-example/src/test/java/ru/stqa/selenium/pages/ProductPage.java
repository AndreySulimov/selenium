package ru.stqa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ProductPage extends Page {

  public ProductPage(WebDriver driver) {
    super(driver);
  }

  public void addToBasket() {
    wait.until(visibilityOfElementLocated(By.name("add_cart_product"))).click();
  }
}