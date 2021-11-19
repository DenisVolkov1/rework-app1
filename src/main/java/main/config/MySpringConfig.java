package main.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.EntityManagerFactoryAccessor;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
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
@ComponentScan("main")
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
		
//		  Properties prop=null; try (InputStream input =
//		  this.getClass().getResourceAsStream("/config.properties")) {
//		  
//		  prop = new Properties(); // load a properties file prop.load(input); } catch
//		  (IOException ex) { System.out.println(ex); }
//		  
	  DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		  dataSource.setDriverClassName(prop.getProperty("db.driver"));
//		  dataSource.setUrl(prop.getProperty("db.url"));
//		  dataSource.setUsername(prop.getProperty("db.user"));
//		  dataSource.setPassword(prop.getProperty("db.password"));
		 
	    System.out.println(env.getProperty("db.url"));
	    dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    dataSource.setUrl("jdbc:sqlserver://localhost:1433;databasename=SCPRD;");
	    dataSource.setUsername("sa");
	    dataSource.setPassword("sql");
	    return dataSource;
	}
	
   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
	   HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	   LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
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
