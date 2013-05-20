package pl.bristleback.sample.chat.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import pl.bristleback.sample.chat.user.ChatUser;
import pl.bristleback.server.bristle.api.users.UserContext;
import pl.bristleback.server.bristle.conf.resolver.init.PojoConfigResolver;
import pl.bristleback.server.bristle.engine.servlet.BristlebackHttpHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for annotation application context.
 *
 * @author Tom-Steve Watzke
 * @see pl.bristleback.server.bristle.conf.namespace.BristlebackServletBeanDefinitionParser
 */
@Configuration
@Profile("servlet")
@Import(BristlebackServerMessagesConfig.class)
public class BristlebackServletConfig {

  /**
   * Servlet engine to be used.
   */
  private final static String ENGINE_NAME = "system.engine.tomcat.servlet";

  /**
   * Logging level for debugging using Log4J.
   */
  private final static String LOGGING_LEVEL = "org.apache.log4j.Level.DEBUG";

  /**
   * User context class
   */
  private final static Class<? extends UserContext> USER_CONTEXT = ChatUser.class;

  /**
   * Default: "**\/*" (without "\")
   */
  private final static String SERVLET_MAPPING = "/websocket";

  @Bean
  public PojoConfigResolver initialConfigurationResolver() {
    PojoConfigResolver configResolver = new PojoConfigResolver();
    configResolver.setEngineName(ENGINE_NAME);
    configResolver.setLoggingLevel(LOGGING_LEVEL);
    configResolver.setUserContextClass(USER_CONTEXT);

    return configResolver;
  }

  @Bean
  public SimpleUrlHandlerMapping bristlebackHandlerMappings() {
    Map<String, BristlebackHttpHandler> mappings = new HashMap<>();
    mappings.put(SERVLET_MAPPING, bristlebackHttpHandler());

    SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
    handlerMapping.setUrlMap(mappings);

    return handlerMapping;
  }

  @Bean
  public BristlebackHttpHandler bristlebackHttpHandler() {
    BristlebackHttpHandler httpHandler = new BristlebackHttpHandler();
    httpHandler.setInitialConfigurationResolver(initialConfigurationResolver());

    return httpHandler;
  }
}
