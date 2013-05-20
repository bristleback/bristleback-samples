package pl.bristleback.sample.chat.web.config;

import org.springframework.context.annotation.*;
import pl.bristleback.sample.chat.user.ChatUser;
import pl.bristleback.server.bristle.api.users.UserContext;
import pl.bristleback.server.bristle.app.StandaloneServerRunner;
import pl.bristleback.server.bristle.conf.resolver.init.PojoConfigResolver;

/**
 * Configuration class for annotation application context.
 *
 * @author Tom-Steve Watzke
 * @see pl.bristleback.server.bristle.conf.namespace.BristlebackStandaloneServerBeanDefinitionParser
 */
@Configuration
@Profile("standalone")
@ComponentScan(basePackages = {"pl.bristleback.sample.chat"})
@Import(BristlebackServerMessagesConfig.class)
public class BristlebackStandaloneServerConfig {

  /**
   * Servlet engine port to be used.
   */
  private final static Integer ENGINE_PORT = 8080;

  /**
   * Logging level for debugging using Log4J.
   */
  private final static String LOGGING_LEVEL = "org.apache.log4j.Level.DEBUG";

  /**
   * User context class.
   */
  private final static Class<? extends UserContext> USER_CONTEXT = ChatUser.class;

  /**
   * Start server after initialization.
   */
  private final static boolean START_AFTER_INIT = false;

  @Bean
  public PojoConfigResolver initialConfigurationResolver() {
    PojoConfigResolver configResolver = new PojoConfigResolver();
    configResolver.setEnginePort(ENGINE_PORT);
    configResolver.setLoggingLevel(LOGGING_LEVEL);
    configResolver.setUserContextClass(USER_CONTEXT);

    return configResolver;
  }

  @Bean
  public StandaloneServerRunner bristlebackStandaloneServer() {
    StandaloneServerRunner serverRunner = new StandaloneServerRunner();
    serverRunner.setInitialConfigurationResolver(initialConfigurationResolver());

    if (START_AFTER_INIT) {
      serverRunner.startServer();
    }

    return serverRunner;
  }

}
