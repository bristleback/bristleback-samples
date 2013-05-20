package pl.bristleback.scrumtable;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.bristleback.server.bristle.app.StandaloneServerRunner;

import java.util.Scanner;

/**
 * //@todo class description
 * <p/>
 * Created on: 11.04.11 16:46 <br/>
 *
 * @author Paweł Machowski
 */
public class App {

  private App() {
    throw new UnsupportedOperationException();
  }

  private static final String[] CONFIG_FILES =
    {"applicationContext.xml"};


  public static void main(String[] args) {
    BasicConfigurator.configure();
    Logger.getLogger("org.apache").setLevel(Level.INFO);

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_FILES);

    StandaloneServerRunner runner = (StandaloneServerRunner) applicationContext.getBean("bristlebackStandaloneServer");

    Scanner in = new Scanner(System.in);
    String value = in.nextLine();
    while (!value.equalsIgnoreCase("x")) {
      value = in.nextLine();
    }
    runner.stopServer();
  }
}
