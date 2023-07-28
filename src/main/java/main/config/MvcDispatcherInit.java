package main.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class MvcDispatcherInit extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class [] {MySpringConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
		String typeRunning = System.getenv("REWORK_APP1_MONETKA_RUNTYPE");
		
		RuntimeException throwException = new RuntimeException("\r\n*********************************************************\r\n"
				+ "Установите системную переменную REWORK_APP1_MONETKA_RUNTYPE со значением:"
				+ " \"development\" - запуск приложения в режиме разработки, либо \"production\" - запуск приложения в рабочем режиме."
				+ "\r\n*********************************************************\r\n" );
			
		if(typeRunning == null) {
			throw throwException;
		} else if (!typeRunning.equals("development") && !typeRunning.equals("production")){
			throw throwException;
		}
		
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }

}
