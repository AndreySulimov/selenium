package ru.stqa.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.WebDriverPool;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class TestBase {

  // инициализируем хранилище, содержащее информацию о драйверах, привязанных к разным потокам
  public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

  public WebDriver driver;
  public WebDriverWait wait;

  @Before
  public void start() {

    /* если с текущим потоком ассоциирован какой-то объект типа WebDriver,
    т.е. tlDriver возвращает непустое значение, значит его и нужно использовать.
    если же в tlDriver пусто, то нужно создать новый экземпляр драйвера и привязать его к текущему потоку*/

    if (tlDriver.get() != null) {
      driver = tlDriver.get();
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      wait = new WebDriverWait(driver, 5);
      return;
    }

    // экземпляр драйвера для запуска Chrome
    driver = new ChromeDriver();

    // экземпляр драйвера для запуска Firefox
    //driver = new FirefoxDriver();

    // экземпляр драйвера для запуска Firefox Nightly
    /* FirefoxOptions options = new FirefoxOptions();
    options.setBinary(new FirefoxBinary(new File("C:\\Program Files\\Firefox Nightly\\firefox.exe")));
    driver = new FirefoxDriver(options); */

    // экземпляр драйвера для запуска Firefox "по старой схеме" (установлена версия Mozilla Firefox 45 ESR)
    /* FirefoxOptions options = new FirefoxOptions().setLegacy(true);
    options.setBinary(new FirefoxBinary(new File("C:\\Program Files\\Mozilla Firefox ESR\\firefox.exe")));
    driver = new FirefoxDriver(options); */

    // экземпляр драйвера для запуска Internet Explorer
    //driver = new InternetExplorerDriver();

    tlDriver.set(driver); // привязываем экземпляр драйвера к текущему потоку
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 5);

    // закрываем браузер(ы)
    Runtime.getRuntime().addShutdownHook(
            new Thread(() -> {
              driver.quit();
              driver = null;
            }));
  }

  @After
  public void stop() {
    //driver.quit();
    //driver = null;
  }
}

/*
// Параллельный запуск тестов с помощью Web Driver Factory
public class TestBase {

  public WebDriver driver;
  public WebDriverWait wait;

  @BeforeEach
  public void start() {
    driver = WebDriverPool.DEFAULT.getDriver(new ChromeOptions());
    //driver = WebDriverPool.DEFAULT.getDriver(new FirefoxOptions());
    //driver = WebDriverPool.DEFAULT.getDriver(new InternetExplorerOptions());
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 10);
  }

  @AfterAll
  public static void stop() {
    WebDriverPool.DEFAULT.dismissAll();
  }
}
*/