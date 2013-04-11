package pl.bristleback.sample.chat;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.bristleback.sample.chat.web.config.BristlebackStandaloneServerConfig;
import pl.bristleback.server.bristle.app.StandaloneServerRunner;

import java.util.Scanner;

public final class App {

	private App() {
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.getEnvironment().setActiveProfiles("standalone");
		applicationContext.register(BristlebackStandaloneServerConfig.class);
		applicationContext.refresh();
		
		StandaloneServerRunner runner = (StandaloneServerRunner) applicationContext
				.getBean("bristlebackStandaloneServer");

		Scanner in = new Scanner(System.in);
		String value = in.nextLine();
		while (!value.equalsIgnoreCase("x")) {
			value = in.nextLine();
		}
		runner.stopServer();
		in.close();
	}
}
