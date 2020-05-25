package ru.stqa.selenium.app;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.pages.BasketPage;
import ru.stqa.selenium.pages.MainPage;
import ru.stqa.selenium.pages.ProductPage;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Application {

  public WebDriver driver;
  public WebDriverWait wait;
  public MainPage mainPage;
  public ProductPage productPage;
  public BasketPage basketPage;

  public Application() {

    // экземпляр драйвера для запуска Chrome
    driver = new ChromeDriver();

    // экземпляр драйвера для запуска Firefox
    //driver = new FirefoxDriver();

    // экземпляр драйвера для запуска Internet Explorer
    //driver = new InternetExplorerDriver();

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // неявные ожидания
    wait = new WebDriverWait(driver, 5); // явные ожидания

    mainPage = new MainPage(driver);
    productPage = new ProductPage(driver);
    basketPage = new BasketPage(driver);
  }

  public void quit() {
    driver.quit();
  }

  public void refreshPage() {
    driver.navigate().refresh();
  }

  public void alertDissmiss() {
    try {
      driver.switchTo().alert().dismiss();
    } catch (NoAlertPresentException e) {
      // ничего не делаем, алерт отсутствует
    }
  }

  public int countProductInBasket() {
    WebElement oldQuantity = driver.findElement(By.cssSelector("span.quantity"));

      /* из-за ошибки, возникающей при добавлении товара в корзину,
      счетчик не обновляется после добавления товара + отображается алерт об ошибке.
      Алерт перехватываем и закрываем (метод alertDissmiss), а для обновления счетчика - обновляем страницу.

      Ожидание исчезновения "старого" элемента и последующий поиск "нового" при этом используются,
      как если бы ошибки не было. */

    refreshPage();
    wait.until(stalenessOf(oldQuantity));
    WebElement newQuantity = wait.until(visibilityOfElementLocated(By.cssSelector("span.quantity")));
    int count = Integer.parseInt(newQuantity.getAttribute("textContent"));
    return count;
  }

  public void addToBasket(int count) {
    int actualCount = countProductInBasket();
    while (actualCount != count) {
      mainPage.openFirstProductCard();
      productPage.addToBasket();
      actualCount = countProductInBasket();
      mainPage.openByButton();
    }
  }
}