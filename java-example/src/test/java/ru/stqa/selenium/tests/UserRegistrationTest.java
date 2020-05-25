package ru.stqa.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class UserRegistrationTest extends TestBase {

  @Test
  public void userRegistrationTest() {
    driver.get("http://localhost/litecart/en/login");

    String email = "test" + (int) (Math.random() * 100) + "@mail.com";
    String password = "test";

    driver.findElement(By.cssSelector("td > a")).click();
    driver.findElement(By.name("firstname")).sendKeys("Test");
    driver.findElement(By.name("lastname")).sendKeys("Test");
    driver.findElement(By.name("address1")).sendKeys("Test");
    Select select1 = new Select(driver.findElement(By.cssSelector("[name = country_code]")));
    select1.selectByVisibleText("United States");
    driver.findElement(By.name("postcode")).sendKeys("12345");
    driver.findElement(By.name("city")).sendKeys("City");
    driver.findElement(By.name("email")).sendKeys(email);
    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.name("confirmed_password")).sendKeys(password);
    driver.findElement(By.name("phone")).sendKeys("+79001234567");
    driver.findElement(By.name("create_account")).click();

    /* поле с зоной (штатом) по какой-то причине становится доступно только после
    заполнения обязательных полей и клика на "Create Account", поэтому нужно выбрать штат
    и повторно ввести пароль (значения полей сбрасываются) */
    Select select2 = new Select(driver.findElement(By.cssSelector("[name = zone_code]")));
    select2.selectByVisibleText("Alaska");
    driver.findElement(By.name("password")).sendKeys("test");
    driver.findElement(By.name("confirmed_password")).sendKeys("test");
    driver.findElement(By.name("create_account")).click();

    driver.findElement(By.cssSelector("[href *= logout]")).click();

    driver.findElement(By.name("email")).sendKeys(email);
    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.name("login")).click();

    driver.findElement(By.cssSelector("[href *= logout]")).click();
  }
}