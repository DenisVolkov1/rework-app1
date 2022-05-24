package main.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@PropertySource("classpath:config.properties")
@EnableTransactionManagement
@ComponentScan("main.config")
@ComponentScan("main.controllers")
// Сделано чтобы переключаться между "mssql" и "postgresql" базами.
//@ComponentScan("main.dao.impl_dao.mssql")
@ComponentScan("main.dao.impl_dao.postgresql") // - используем PostgresSql
//////////////////////////////////////////////////////////////////////////
@ComponentScan("main.dao.impl_dao.rowmapper")
@ComponentScan("main.dao.interface_dao")
@ComponentScan("main.dao.model")
@ComponentScan("main.dao.service")
@ComponentScan("main.util")
@EnableWebMvc
public class MySpringConfig implements WebMvcConfigurer {

    private ApplicationContext applicationContext;
    
    @Autowired
    Environment env;

	@Autowired
    public MySpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
	

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
    
	@Bean
	public DataSource getDataSource() {
			  
	  DriverManagerDataSource dataSource = new DriverManagerDataSource();
		  dataSource.setDriverClassName(env.getProperty("db.driver"));
		  dataSource.setUrl(env.getProperty("db.url"));
		  dataSource.setUsername(env.getProperty("db.user"));
		  dataSource.setPassword(env.getProperty("db.password"));
		 
	    return dataSource;
	}
	
   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
	   HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	   LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setJpaDialect(new HibernateJpaDialect());
	    factory.setPackagesToScan("main");
	    factory.setDataSource(getDataSource());
	    return factory;
   }

   @Bean
   public PlatformTransactionManager transactionManager(){
      JpaTransactionManager transactionManager= new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
      return transactionManager;
   }

    
	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(getDataSource());
	}
	
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
    
	
}
