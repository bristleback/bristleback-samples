package pl.bristleback.sample.chat.web.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Initializer implementation automatically getting detected by SpringServletContainerInitializer.
 * @author Tom-Steve Watzke
 */
public class WebInit implements WebApplicationInitializer {

	AnnotationConfigWebApplicationContext rootContext;
		
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		setupApplicationContext(servletContext);
		registerServlets(servletContext);
		registerListeners(servletContext);
		registerFilters(servletContext);
	}

	private void registerFilters(ServletContext servletContext) {
		/*
		 *  register default input/output filter
		 */
		FilterRegistration filterReg = servletContext.addFilter(
				"encodingFilter",
				CharacterEncodingFilter.class);
		filterReg.setInitParameter("encoding", "UTF-8");
		filterReg.setInitParameter("forceEncoding", "true");
		filterReg.addMappingForUrlPatterns(null, false, "/*");
	}

	private void registerListeners(ServletContext servletContext) {
		/*
		 * Manage the lifecycle of the root application context
		 */
		servletContext.addListener(new ContextLoaderListener(rootContext));
		
	}

	private void registerServlets(ServletContext servletContext) {
		/*
		 * Initializing the annotation based dispatcher servlet
		 */
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(WebMvcConfig.class);
		
		/*
		 * Register the annotation based dispatcher servlet and configure it
		 */
		ServletRegistration.Dynamic dispatcherServlet;
		dispatcherServlet = servletContext.addServlet(
				"springServlet", 
				new DispatcherServlet(dispatcherContext));
		dispatcherServlet.setLoadOnStartup(2);
		dispatcherServlet.addMapping("/websocket/*");
				
	}

	private void setupApplicationContext(ServletContext servletContext) {
		rootContext = new AnnotationConfigWebApplicationContext();
		if(rootContext.getEnvironment().getSystemProperties().containsKey("catalina.home")) {
			rootContext.getEnvironment().setActiveProfiles("servlet");
			rootContext.register(WebMvcConfig.class);
		}
	}
}
