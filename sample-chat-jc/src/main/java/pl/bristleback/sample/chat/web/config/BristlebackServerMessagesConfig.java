package pl.bristleback.sample.chat.web.config;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import pl.bristleback.server.bristle.action.client.ClientActionProxyInterceptor;
import pl.bristleback.server.bristle.api.annotations.ClientAction;
import pl.bristleback.server.bristle.api.annotations.ClientActionClass;
import pl.bristleback.server.bristle.conf.resolver.message.ObjectSenderInjector;
import pl.bristleback.server.bristle.message.ConditionObjectSender;

/**
 * Configuration class for annotation application context.
 * @author Tom-Steve Watzke
 * @see pl.bristleback.server.bristle.conf.namespace.BristlebackServerMessagesBeanDefinitionParser
 */
@Configuration
public class BristlebackServerMessagesConfig {
	
	/*
	 * Register client action classes
	 */

	@Bean
	public DefaultAdvisorAutoProxyCreator autoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}
	
	@Bean
	public AnnotationClassFilter outputAnnotationPointcut() {
		return new AnnotationClassFilter(ClientActionClass.class);
	}
	
	@Bean
	public AnnotationMethodMatcher outputActionAnnotationPointcut() {
		return new AnnotationMethodMatcher(ClientAction.class);
	}
	
	@Bean
	public ComposablePointcut clientActionMechanismPointcut() {
		return new ComposablePointcut(
				outputAnnotationPointcut(), 
				outputActionAnnotationPointcut());
	}
	
	@Bean
	public ClientActionProxyInterceptor clientActionInterceptor() {
		return new ClientActionProxyInterceptor();
	}
	
	@Bean
	public DefaultPointcutAdvisor clientActionMessageProxyAdvisor() {
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
		defaultPointcutAdvisor.setPointcut(clientActionMechanismPointcut());
		defaultPointcutAdvisor.setAdvice(clientActionInterceptor());
		
		return defaultPointcutAdvisor;
	}
	
	/**
	 * Register ConditionSender bean post processor
	 */
	@Bean(name = "system.sender.condition.injector")
	@Scope("singleton")
	public ObjectSenderInjector objectSenderInjector() {
		return new ObjectSenderInjector();
	}
	
	/**
	 * Register ConditionSenderBean
	 */
	@Bean(name = "system.sender.condition")
	@Scope("prototype")
	public ConditionObjectSender conditionObjectSender() {
		return new ConditionObjectSender();
	}
}
