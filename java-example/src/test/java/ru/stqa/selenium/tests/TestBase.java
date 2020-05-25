package ru.stqa.selenium.tests;

import com.google.common.io.Files;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.WebDriverPool;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

public class TestBase {

  // инициализируем хранилище, содержащее информацию о драйверах, привязанных к разным потокам
  public static ThreadLocal<EventFiringWebDriver> tlDriver = new ThreadLocal<>();

  public EventFiringWebDriver driver; // драйвер с использованием протоколирования
  public WebDriverWait wait;
  public BrowserMobProxy proxy; // переменная, содержащая перехваченный прокси-сервером трафик

  public static class MyListener extends AbstractWebDriverEventListener {
    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
      System.out.println(by);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
      System.out.println(by + " found");
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
      System.out.println(throwable);

      // снятие скриншота при возникновении исключения и его сохранение
      File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      File screen = new File("screen-" + System.currentTimeMillis() + ".png");
      try {
        Files.copy(tmp, screen);
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println(screen);
    }
  }

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

    // возможность перехвата трафика между клиентом (браузером) и сервером с помощью прокси-сервера (сервера-посредника)
//    proxy = new BrowserMobProxyServer();
//    proxy.start(0);

    //Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

//    Proxy proxy = new Proxy();
//    proxy.setHttpProxy("localhost:8888");
//
//    DesiredCapabilities capabilities = new DesiredCapabilities();
    //capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
//    capabilities.setCapability("proxy", proxy);

    // экземпляр драйвера для запуска Chrome с использованием протоколирования
    driver = new EventFiringWebDriver(new ChromeDriver());

    // добавляем наблюдатель
    driver.register(new MyListener());

    // экземпляр драйвера для запуска Chrome
    // driver = new ChromeDriver();

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