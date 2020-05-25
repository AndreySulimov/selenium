package ru.stqa.selenium.tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingCountriesAndGeoZonesTests extends TestBase {

  @Test
  public void sortingCountriesTest() {
    driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();

    // получаем список элементов, соответствующих строкам таблицы со странами
    List<WebElement> rows = driver.findElements(By.cssSelector(".row"));
    // объявляем список названий стран
    ArrayList<String> listCountries = new ArrayList<>();
    // объявляем список названий зон
    ArrayList<String> listZones = new ArrayList<>();
    // проходимся в цикле по всем строкам таблицы
    for (int i = 0; i < rows.size(); i++) {
      // обновляем список стран (нужно, т.к. страница перезагружается после возврата к списку всех стран)
      rows = driver.findElements(By.cssSelector(".row"));
      // получаем элемент (ячейка таблицы) с названием страны
      WebElement country = rows.get(i).findElement(By.cssSelector("td:nth-child(5)"));
      // получаем элемент (ячейка таблицы) с количеством зон
      WebElement zone = rows.get(i).findElement(By.cssSelector("td:nth-child(6)"));
      // добавляем в список названий стран название страны
      listCountries.add(country.getAttribute("textContent"));
      // получаем количество зон и сохраняем в переменную
      int zoneCount = Integer.parseInt(zone.getAttribute("textContent"));
      // если количество зон > 0, то...
      if (zoneCount > 0) {
        // получаем элемент со ссылкой на страницу конкретной страны и переходим по этой ссылке
        rows.get(i).findElement(By.cssSelector("td:nth-child(5) > a")).click();
        // получаем список всех зон (как веб-элементов)
        List<WebElement> zones = driver.findElements(By.cssSelector("[type = hidden][name *= name]"));
        // проходимся в цикле по каждому элементу списка
        for (WebElement z : zones) {
          // получаем название зоны и добавляем в список названий зон
          listZones.add(z.getAttribute("value"));
        }
        // объявляем список названий зон для сортировки
        ArrayList<String> sortedListZones = new ArrayList<>();
        // проходимся в цикле по списку названий всех зон
        for (String s : listZones) {
          // добавляем название зоны в список для сортировки
          sortedListZones.add(s);
        }
        // сортируем полученный список названий зон
        Collections.sort(sortedListZones);
        // проверяем равенство списков до и после сортировки
        Assert.assertEquals(listZones, sortedListZones);
        // выводим на экран список названий зон
        System.out.println("Zones in: " + "\n" + sortedListZones);
        // очищаем списки (нужно, чтобы предыдущие результаты не мешали сравнению при следующих итерациях цикла)
        listZones.clear();
        sortedListZones.clear();

        // возвращаемся на страницу со списком всех стран
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
      }
    }

    // объявляем список названий стран для сортировки
    ArrayList<String> sortedListCountries = new ArrayList<>();
    // проходимся в цикле по списку названий всех стран
    for (String s : listCountries) {
      // добавляем название страны в список для сортировки
      sortedListCountries.add(s);
    }

    // сортируем полученный список названий стран
    Collections.sort(sortedListCountries);
    // проверяем равенство списков до и после сортировки
    Assert.assertEquals(listCountries, sortedListCountries);
  }

  @Test
  public void sortingGeoZonesTest() {
    driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
//    driver.findElement(By.name("username")).sendKeys("admin");
//    driver.findElement(By.name("password")).sendKeys("admin");
//    driver.findElement(By.name("login")).click();

    // получаем список элементов, соответствующих строкам таблицы с зонами
    List<WebElement> rows = driver.findElements(By.cssSelector(".row"));
    // проходимся в цикле по всем строкам таблицы
    for (int i = 0; i < rows.size(); i++) {
      // обновляем список стран (нужно, т.к. страница перезагружается после возврата к списку всех зон)
      rows = driver.findElements(By.cssSelector(".row"));
      // получаем элемент со ссылкой на страницу конкретной зоны и переходим по этой ссылке
      rows.get(i).findElement(By.cssSelector("td:nth-child(3) > a")).click();
      // получаем список всех зон (как веб-элементов)
      List<WebElement> zones = driver.findElements(By.cssSelector("[name *= zone_code] > [selected = selected]"));
      // объявляем список названий зон
      ArrayList<String> listZones = new ArrayList<>();
      // проходимся в цикле по каждому элементу списка
      for (WebElement z : zones) {
        // получаем название зоны и добавляем в список
        listZones.add(z.getAttribute("textContent"));
      }
      // объявляем список названий зон для сортировки
      ArrayList<String> sortedListZones = new ArrayList<>();
      // проходимся в цикле по списку названий всех зон
      for (String s : listZones) {
        // добавляем название зоны в список для сортировки
        sortedListZones.add(s);
      }
      // сортируем полученный список
      Collections.sort(sortedListZones);
      // проверяем равенство списков до и после сортировки
      Assert.assertEquals(listZones, sortedListZones);
      // выводим на экран отсортированный список названий зон
      System.out.println("Zones in: " + "\n" + sortedListZones);
      // очищаем списки (нужно, чтобы предыдущие результаты не мешали сравнению при следующих итерациях цикла)
      listZones.clear();
      sortedListZones.clear();
      // возвращаемся на страницу со списком всех зон
      driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
    }
  }
}