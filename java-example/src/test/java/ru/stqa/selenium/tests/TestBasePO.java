package ru.stqa.selenium.tests;

import org.junit.Before;
import ru.stqa.selenium.app.Application;

public class TestBasePO {

  // инициализируем хранилище, содержащее информацию о драйверах, привязанных к разным потокам
  public static ThreadLocal<Application> tlApp = new ThreadLocal<>();
  public Application app;

  @Before
  public void start() {

    /* если с текущим потоком ассоциирован какой-то объект типа WebDriver,
    т.е. appDriver возвращает непустое значение, значит его и нужно использовать.
    если же в appDriver пусто, то нужно создать новый экземпляр драйвера и привязать его к текущему потоку*/

    if (tlApp.get() != null) {
      app = tlApp.get();
      return;
    }

    app = new Application();
    tlApp.set(app); // привязываем экземпляр драйвера к текущему потоку


    // закрываем браузер(ы)
    Runtime.getRuntime().addShutdownHook(
            new Thread(() -> {
              app.quit();
              app = null;
            }));
  }
}