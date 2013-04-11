package pl.bristleback.sample.chat.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration class for annotation application context.
 * Base class for all additional configurations like view resolver, i18n, etc.
 * @author Tom-Steve Watzke
 */
@EnableWebMvc
@Configuration
@Profile("servlet")
@Import({ BristlebackServletConfig.class })
@ComponentScan( basePackages = { "pl.bristleback.sample.chat" } )
public class WebMvcConfig extends WebMvcConfigurerAdapter {

}
