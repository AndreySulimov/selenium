package ru.stqa.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest {

  public WebDriver driver;
  public WebDriverWait wait;

  @Before
  public void start() throws MalformedURLException {

//    хаб: java -jar selenium-server-standalone-3.141.59.jar -role hub
//    узел1: java -jar selenium-server-standalone-3.141.59.jar -role node -hub http://192.168.0.11:4444/wd/hub -capabilities browserName=firefox,maxInstances=3 -capabilities browserName=chrome,maxInstances=2
//    узел2: java -jar selenium-server-standalone-3.141.59.jar -role node -hub http://192.168.0.11:4444/wd/hub -capabilities browserName="internet explorer",maxInstances=2

    driver = new RemoteWebDriver(new URL("http://192.168.0.11:4444/wd/hub"), new InternetExplorerOptions());
    wait = new WebDriverWait(driver, 5);
  }

  @Test
  public void myFirstTest() {
    driver.get("https://www.google.ru/");
    driver.findElement(By.name("q")).sendKeys("Hello, world!");
    driver.findElement(By.id("tsf")).submit();
    wait.until(titleIs("Hello, world! - Поиск в Google"));
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }
}