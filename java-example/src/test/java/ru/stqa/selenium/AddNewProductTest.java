package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public class AddNewProductTest extends TestBase {

  @Test
  public void addNewProductTest() {
    driver.get("http://localhost/litecart/admin/login.php");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();

    // переходим на страницу Catalog
    driver.findElement(By.cssSelector("[href *= catalog]")).click();

    // переходим на страницу создания товара
    driver.findElement(By.cssSelector("[href *= edit_product]")).click();

    int random = 1 + (int) (Math.random() * 100);

    // переходим на вкладку General
    driver.findElement(By.cssSelector("[href *= tab-general]")).click();

    driver.findElement(By.cssSelector("input[name=status][value='1']")).click();
    WebElement name = driver.findElement(By.cssSelector("input[name*=name]"));
    name.sendKeys("Super Duck № " + random);
    // сохраняем название товара для проверки его появления в каталоге после создания
    String expectedName = name.getAttribute("textContent");
    driver.findElement(By.cssSelector("input[name*=code]")).sendKeys("rd" + random);
    driver.findElement(By.cssSelector("input[data-name='Rubber Ducks']")).click();
    driver.findElement(By.cssSelector("input[type=checkbox][value='1-3']")).click();
    Select select1 = new Select(driver.findElement(By.cssSelector("[name = default_category_id]")));
    select1.selectByVisibleText("Rubber Ducks");
    File image = new File("src/test/resources/image.png");
    driver.findElement(By.cssSelector("input[type=file]")).sendKeys(image.getAbsolutePath());
    driver.findElement(By.cssSelector("[name=date_valid_from]")).sendKeys("08/05/2020");
    driver.findElement(By.cssSelector("[name=date_valid_to]")).sendKeys("07/05/2021");

    // переходим на вкладку Information
    driver.findElement(By.cssSelector("[href *= tab-information]")).click();

    Select select2 = new Select(driver.findElement(By.cssSelector("[name = manufacturer_id]")));
    select2.selectByVisibleText("ACME Corp.");
    driver.findElement(By.cssSelector("input[name*=short_description]")).sendKeys("Super-Puper Duck");
    driver.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys("Super-Puper-Buper amazing Duck");
    driver.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys(Keys.CONTROL + "a");
    driver.findElement(By.cssSelector(".trumbowyg-italic-button")).click();
    driver.findElement(By.cssSelector("input[name*=head_title]")).sendKeys("BatDuck");

    // переходим на вкладку Prices
    driver.findElement(By.cssSelector("[href *= tab-prices]")).click();

    driver.findElement(By.name("purchase_price")).sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
    driver.findElement(By.name("purchase_price")).sendKeys("1");
    driver.findElement(By.name("prices[USD]")).sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
    driver.findElement(By.name("prices[USD]")).sendKeys("25");

    // сохраняем товар
    driver.findElement(By.name("save")).click();

    // ищем название созданного товара на странице Catalog
    driver.findElement(By.partialLinkText(expectedName));

//    driver.findElement(By.cssSelector("#tab-general"));
//    setDatepicker(driver, "[name=date_valid_from]", "05/07/2021");
  }

//  public void setDatepicker(WebDriver driver, String cssSelector, String date) {
//    new WebDriverWait(driver, 30000).until(
//            (WebDriver d) -> d.findElement(By.cssSelector(cssSelector)).isDisplayed());
//    JavascriptExecutor.class.cast(driver).executeScript(
//            String.format("$('%s').datepicker('setDate', '%s')", cssSelector, date));
//  }
}