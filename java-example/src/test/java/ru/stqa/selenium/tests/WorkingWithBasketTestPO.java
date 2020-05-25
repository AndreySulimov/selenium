package ru.stqa.selenium.tests;

import org.junit.Test;

public class WorkingWithBasketTestPO extends TestBasePO {

  @Test
  public void workingWithBasketTestPO() {

    app.mainPage.openByUrl();
    app.addToBasket(3);
    app.basketPage.open();
    app.basketPage.removeAllProducts();
  }
}