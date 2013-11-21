package it.f2informatica.webapp.test.context;

import com.google.common.collect.Iterables;
import it.f2informatica.webapp.context.WebApplicationContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.dialect.SpringStandardDialect;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;
import java.util.Locale;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebApplicationContextTest {

	static WebApplicationContext webApplicationContext;

	@Mock
	ResourceHandlerRegistry handlerRegistry;

	@Mock
	ResourceHandlerRegistration resourceHandlerRegistration;

	@Mock
	List<HandlerMethodArgumentResolver> argumentResolvers;

	@Mock
	ResourceLoader resourceLoader;

	@Mock
	ViewControllerRegistry viewControllerRegistry;

	@Mock
	ViewControllerRegistration viewControllerRegistration;

	@Mock
	DefaultServletHandlerConfigurer servletHandlerConfigurer;

	@Mock
	InterceptorRegistry interceptorRegistry;

	@BeforeClass
	public static void beforeClass() {
		webApplicationContext = new WebApplicationContext();
	}

	@Test
	public void resourceHandlers() {
		when(handlerRegistry.addResourceHandler("/static/**")).thenReturn(resourceHandlerRegistration);
		when(resourceHandlerRegistration.addResourceLocations("/static/")).thenReturn(resourceHandlerRegistration);
		webApplicationContext.addResourceHandlers(handlerRegistry);
		verify(handlerRegistry).addResourceHandler("/static/**");
		verify(resourceHandlerRegistration).addResourceLocations("/static/");
	}

	@Test
	public void argumentResolvers() {
		ArgumentCaptor<PageableHandlerMethodArgumentResolver> argument = pageableArgumentCaptor();
		webApplicationContext.addArgumentResolvers(argumentResolvers);
		verify(argumentResolvers).add(argument.capture());
		assertThat(argument.getValue()).isInstanceOf(PageableHandlerMethodArgumentResolver.class);
	}

	private ArgumentCaptor<PageableHandlerMethodArgumentResolver> pageableArgumentCaptor() {
		return ArgumentCaptor.forClass(PageableHandlerMethodArgumentResolver.class);
	}

	@Test
	public void addViewControllerMappingRootUrlToRedirectHomePage() {
		ArgumentCaptor<String> urlPathArgument = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> viewNameArgument = ArgumentCaptor.forClass(String.class);

		when(viewControllerRegistry.addViewController("/")).thenReturn(viewControllerRegistration);
		doNothing().when(viewControllerRegistration).setViewName("redirect:/home");

		webApplicationContext.addViewControllers(viewControllerRegistry);

		verify(viewControllerRegistry).addViewController(urlPathArgument.capture());
		verify(viewControllerRegistration).setViewName(viewNameArgument.capture());

		assertThat("/").isEqualTo(urlPathArgument.getValue());
		assertThat("redirect:/home").isEqualTo(viewNameArgument.getValue());
	}

	@Test
	public void configureDefaultServletHandling() {
		doNothing().when(servletHandlerConfigurer).enable();
		webApplicationContext.configureDefaultServletHandling(servletHandlerConfigurer);
		verify(servletHandlerConfigurer).enable();
	}

	@Test
	public void beanNameViewResolver() {
		BeanNameViewResolver beanNameViewResolver = webApplicationContext.beanNameViewResolver();
		assertThat(beanNameViewResolver.getOrder()).isEqualTo(1);
	}

	@Test
	public void thymeleafViewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = webApplicationContext.thymeleafViewResolver();
		assertThat(thymeleafViewResolver.getOrder()).isEqualTo(2);
	}

	@Test
	public void thymeleafTemplateEngine() {
		SpringTemplateEngine thymeleafTemplateEngine = webApplicationContext.thymeleafTemplateEngine();
		IDialect springDialiect = Iterables.getFirst(thymeleafTemplateEngine.getDialects(), null);
		assertThat(springDialiect).isInstanceOf(SpringStandardDialect.class);
	}

	@Test
	public void thymeleafTemplateResolver() {
		ServletContextTemplateResolver templateResolver = webApplicationContext.thymeleafTemplateResolver();
		templateResolver.initialize();
		assertThat(templateResolver.getTemplateMode()).isEqualTo("HTML5");
	}

	@Test
	public void messageSource() {
		ReloadableResourceBundleMessageSource messageSource = webApplicationContext.messageSource();
		String englishMessage = messageSource.getMessage("message.test", new Object[]{}, Locale.ENGLISH);
		assertThat(englishMessage).isEqualTo("Message in English");
		String italianMessage = messageSource.getMessage("message.test", new Object[]{}, Locale.ITALIAN);
		assertThat(italianMessage).isEqualTo("Messaggio in Italiano");
	}

	@Test
	public void addInterceptors() {
		ArgumentCaptor<LocaleChangeInterceptor> argument = ArgumentCaptor.forClass(LocaleChangeInterceptor.class);
		webApplicationContext.addInterceptors(interceptorRegistry);
		verify(interceptorRegistry).addInterceptor(argument.capture());
		assertThat(argument.getValue().getParamName()).isEqualTo("siteLanguage");
	}

	@Test
	public void localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = webApplicationContext.localeChangeInterceptor();
		assertThat(localeChangeInterceptor.getParamName()).isEqualTo("siteLanguage");
	}

	@Test
	public void localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = webApplicationContext.localeResolver();
		assertThat(cookieLocaleResolver.getCookieName()).isEqualTo("CURRENT_LOCALE");
	}

}