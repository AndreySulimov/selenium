package ru.stqa.selenium.tests;

import net.lightbody.bmp.core.har.Har;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.logging.LogEntry;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest extends TestBase{

//  public WebDriver driver;
//  public WebDriverWait wait;
//
//  @Before
//  public void start() throws MalformedURLException {

//    хаб: java -jar selenium-server-standalone-3.141.59.jar -role hub
//    узел1: java -jar selenium-server-standalone-3.141.59.jar -role node -hub http://192.168.0.11:4444/wd/hub -capabilities browserName=firefox,maxInstances=3 -capabilities browserName=chrome,maxInstances=2
//    узел2: java -jar selenium-server-standalone-3.141.59.jar -role node -hub http://192.168.0.11:4444/wd/hub -capabilities browserName="internet explorer",maxInstances=2

//    driver = new RemoteWebDriver(new URL("http://192.168.0.11:4444/wd/hub"), new InternetExplorerOptions());
//    wait = new WebDriverWait(driver, 5);
//  }

  @Test
  public void proxyTest() {
    proxy.newHar(); // создаем новый архив информации о запросах, перехваченных прокси-сервером

    driver.get("http://selenium2.ru/");
    wait.until(titleIs("Hello, world! - Поиск в Google"));

    Har har = proxy.endHar(); // останавливаем прокси-сервер и присваиваем результат в переменную для последующего анализа
    // получаем список с информацией обо всех перехваченных запросах
    har.getLog().getEntries().
            // и выводим на консоль коды всех ответов, полученных от сервера (чтобы убедиться, что везде 200)
                    forEach(l -> System.out.println(l.getResponse().getStatus() + ":" + l.getRequest().getUrl()));
  }

  @Test
  public void myFirstTest() {

    driver.get("https://www.google.ru/");
    driver.findElement(By.name("q")).sendKeys("Hello, world!");
    driver.findElement(By.id("tsf")).submit();
    wait.until(titleIs("Hello, world! - Поиск в Google"));
    // выводим логи браузера
    for (LogEntry l : driver.manage().logs().get("browser").getAll()) {
      System.out.println(l);
    }
  }

//  @After
//  public void stop() {
//    driver.quit();
//    driver = null;
//  }
}