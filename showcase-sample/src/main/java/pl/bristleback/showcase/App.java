package pl.bristleback.showcase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.bristleback.server.bristle.app.StandaloneServerRunner;

import java.util.Scanner;

public class App {

  private static final String STOP_CHAR = "x";

  private static final String[] CONFIG_FILES =
    {"applicationContext.xml"};

  private App() {
    throw new UnsupportedOperationException();
  }

  public static void main(String[] args) {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_FILES);

    StandaloneServerRunner runner = (StandaloneServerRunner) applicationContext.getBean("bristlebackStandaloneServer");

    Scanner in = new Scanner(System.in);
    String value = in.nextLine();
    while (!value.equalsIgnoreCase(STOP_CHAR)) {
      value = in.nextLine();
    }
    runner.stopServer();
  }
}

