package ru.stqa.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

public class ProductCardTest extends TestBase {

  @Test
  public void productCardTest() {
    // переходим на главную страницу приложения
    driver.get("http://localhost/litecart/en/");

    // находим элемент, соответствующий товару (уточке) в нужном разделе (Campaigns)
    WebElement product = driver.findElement(By.cssSelector("#box-campaigns li"));

    // получаем название товара
    String name = product.findElement(By.cssSelector(".name")).getAttribute("textContent");

    // получаем значение обычной цены товара
    String regularPrice = product.findElement(By.cssSelector(".regular-price")).getAttribute("textContent");
    // получаем значение свойства color обычной цены товара
    String regularPriceColor = product.findElement(By.cssSelector(".regular-price")).getCssValue("color");
    // приводим RGBa-представление цвета к очищенному виду
    String cleanedRegularPriceColor = cleaned(regularPriceColor);
    // режем полученную строку и укладываем в массив
    String[] RGBRegularPrice = cleanedRegularPriceColor.split(",");
    // проверяем, что RGBa-представление цвета соответствует серому цвету
    Assert.assertEquals(isGray(RGBRegularPrice), true);
    // находим у товара элемент с тегом s и классом regular-price, тем самым проверяя, что обычная цена перечеркнута
    WebElement regularPriceStrikeout = product.findElement(By.cssSelector("s.regular-price"));

    // получаем значение акционной цены товара
    String campaignPrice = product.findElement(By.cssSelector(".campaign-price")).getAttribute("textContent");
    // получаем значение свойства color акционной цены товара
    String campaignPriceColor = product.findElement(By.cssSelector(".campaign-price")).getCssValue("color");
    // приводим RGBa-представление цвета к очищенному виду
    String cleanedCampaignPriceColor = cleaned(campaignPriceColor);
    // режем полученную строку и укладываем в массив
    String[] RGBCampaignPrice = cleanedCampaignPriceColor.split(",");
    // проверяем, что RGBa-представление цвета соответствует красному цвету
    Assert.assertEquals(isRed(RGBCampaignPrice), true);
    // находим у товара элемент с тегом strong и классом campaign-price, тем самым проверяя, что акционная цена жирная
    WebElement campaignPriceStrikeout = product.findElement(By.cssSelector("strong.campaign-price"));

    // получаем размер обычной цены товара
    Dimension regularPriceSize = product.findElement(By.cssSelector(".regular-price")).getSize();
    // получаем размер обычной цены товара
    Dimension campaignPriceSize = product.findElement(By.cssSelector(".campaign-price")).getSize();
    // проверяем, что акционная цена крупнее, чем обычная
    Assert.assertEquals(itIsMoreThanThat(campaignPriceSize, regularPriceSize), true);

    // находим ссылку для перехода на страницу товара и кликаем по ней
    product.findElement(By.cssSelector("a.link")).click();

    // получаем название товара
    String name2 = driver.findElement(By.cssSelector("h1")).getAttribute("textContent");

    // получаем значение обычной цены товара
    String regularPrice2 = driver.findElement(By.cssSelector(".regular-price")).getAttribute("textContent");
    // получаем значение свойства color обычной цены товара
    String regularPriceColor2 = driver.findElement(By.cssSelector(".regular-price")).getCssValue("color");
    // приводим RGBa-представление цвета к очищенному виду
    String cleanedRegularPriceColor2 = cleaned(regularPriceColor2);
    // режем полученную строку и укладываем в массив
    String[] RGBRegularPrice2 = cleanedRegularPriceColor2.split(",");
    // проверяем, что RGBa-представление цвета соответствует серому цвету
    Assert.assertEquals(isGray(RGBRegularPrice2), true);
    // находим у товара элемент с тегом s и классом regular-price, тем самым проверяя, что обычная цена перечеркнута
    WebElement regularPriceStrikeout2 = driver.findElement(By.cssSelector("s.regular-price"));

    // получаем значение акционной цены товара
    String campaignPrice2 = driver.findElement(By.cssSelector(".campaign-price")).getAttribute("textContent");
    // получаем значение свойства color акционной цены товара
    String campaignPriceColor2 = driver.findElement(By.cssSelector(".campaign-price")).getCssValue("color");
    // приводим RGBa-представление цвета к очищенному виду
    String cleanedCampaignPriceColor2 = cleaned(campaignPriceColor2);
    // режем полученную строку и укладываем в массив
    String[] RGBCampaignPrice2 = cleanedCampaignPriceColor2.split(",");
    // проверяем, что RGBa-представление цвета соответствует красному цвету
    Assert.assertEquals(isRed(RGBCampaignPrice2), true);
    // находим у товара элемент с тегом strong и классом campaign-price, тем самым проверяя, что акционная цена жирная
    WebElement campaignPriceStrikeout2 = driver.findElement(By.cssSelector("strong.campaign-price"));

    // получаем размер обычной цены товара
    Dimension regularPriceSize2 = driver.findElement(By.cssSelector(".regular-price")).getSize();
    // получаем размер обычной цены товара
    Dimension campaignPriceSize2 = driver.findElement(By.cssSelector(".campaign-price")).getSize();
    // проверяем, что акционная цена крупнее, чем обычная
    Assert.assertEquals(itIsMoreThanThat(campaignPriceSize2, regularPriceSize2), true);

    // Проверяем равенство названий товара на главной странице и странице товара
    Assert.assertEquals(name, name2);
    // Проверяем равенство обычной цены товара на главной странице и странице товара
    Assert.assertEquals(regularPrice, regularPrice2);
    // Проверяем равенство акционной цены товара на главной странице и странице товара
    Assert.assertEquals(campaignPrice, campaignPrice2);

    System.out.println("Главная страница: \n"
            + "* Название товара: " + name + "\n"
            + "* Обычная цена: " + regularPrice + "\n"
            + "  ** Цвет (RGBa-представление): " + Arrays.toString(RGBRegularPrice) + "\n"
            + "  ** Размер: " + regularPriceSize + "\n"
            + "* Акционная цена: " + campaignPrice + "\n"
            + "  ** Цвет (RGBa-представление): " + Arrays.toString(RGBCampaignPrice) + "\n"
            + "  ** Размер: " + campaignPriceSize + "\n"
            + "Страница товара: \n"
            + "* Название товара: " + name2 + "\n"
            + "* Обычная цена: " + regularPrice2 + "\n"
            + "  ** Цвет (RGBa-представление): " + Arrays.toString(RGBRegularPrice2) + "\n"
            + "  ** Размер: " + regularPriceSize2 + "\n"
            + "* Акционная цена: " + campaignPrice2 + "\n"
            + "  ** Цвет (RGBa-представление): " + Arrays.toString(RGBCampaignPrice2) + "\n"
            + "  ** Размер: " + campaignPriceSize2 + "\n");
  }

  // функция для приведения RGBa-представления цвета к очищенному виду
  /* значение получаемого свойства имеет вид типа "rgba(119, 119, 119, 1)",
  поэтому необходимо избавиться от букв, скобок и пробелов,
  чтобы можно было разрезать строку и сложить значения в массив для последующей сверки*/

  public static String cleaned(String color) {
    return color
            .replaceAll("\\s", "")
            .replaceAll("[()]", "")
            .replaceAll("[a-z]", "");
  }

  // функция для проверки равенства значений для каналов R, G и B в RGBa-представлении (того, что цвет серый)
  public static boolean isGray(String[] colors) {
    int i = 0;
    boolean result = false;
    while (i < colors.length - 1) {
      if (colors[i].equals(colors[i + 1])) {
        if (colors[i].equals(colors[i + 2])) result = true;
      } else result = false;
      break;
    }
    return result;
  }

  // функция для проверки того, что каналы G и B в RGBa-представлении имеют нулевые значения (т.е. цвет - красный)
  public static boolean isRed(String[] colors) {
    // начинаем со второго элемента, т.к. канал R нас не интересует
    int i = 1;
    boolean result = false;
    while (i < colors.length - 1) {
      if (colors[i].equals("0")) {
        if (colors[i].equals(colors[i + 1])) result = true;
      } else result = false;
      break;
    }
    return result;
  }

  // функция для сравнения размеров двух элементов
  public static boolean itIsMoreThanThat(Dimension a, Dimension b) {
    // получаем ширину элемента a
    int aWidth = a.getWidth();
    // получаем высоту элемента a
    int aHeight = a.getHeight();
    // получаем ширину элемента b
    int bWidth = b.getWidth();
    // получаем высоту элемента b
    int bHeight = b.getHeight();
    boolean result = false;

    if (aWidth > bWidth) {
      if (aHeight > bHeight) result = true;
    } else result = false;
    return result;
  }
}