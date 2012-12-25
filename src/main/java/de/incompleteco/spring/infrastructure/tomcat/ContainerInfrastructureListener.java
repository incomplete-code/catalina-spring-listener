package de.incompleteco.spring.infrastructure.tomcat;

import java.io.File;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.incompleteco.spring.infrastructure.service.InfrastructureService;

/**
 * tomcat hook
 * @author Will
 *
 */
public class ContainerInfrastructureListener implements LifecycleListener {

	private static final String CATALINA_HOME = "catalina.home";
	
	private static final String DEFAULT_CONTEXT = "infrastructure-context.xml";
	
	private static ConfigurableApplicationContext context;
	
	@Override
	public void lifecycleEvent(LifecycleEvent event) {
		//events to look for -> CONFIGURE_START_EVENT and STOP_EVENT
		if (event.getType().equals(Lifecycle.CONFIGURE_START_EVENT)) {
			//start
			initApplicationContext();
			return;
		}//end if
		
		if (event.getType().equals(Lifecycle.STOP_EVENT)) {
			//stop
			destroyApplicationContext();
			return;
		}//end if
	}

	protected void initApplicationContext() {
		//get the classpath
		String location = "file:" + System.getProperty(CATALINA_HOME) + File.separator + "conf" + File.separator;
		context = new ClassPathXmlApplicationContext(location + DEFAULT_CONTEXT);
		//set details
		context.registerShutdownHook();//JVM hook just in case
		//start it
		context.refresh();
		//now start the infrastructure
		for (InfrastructureService service : context.getBeansOfType(InfrastructureService.class).values()) {
			try {
				service.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end for
	}
	
	protected void destroyApplicationContext() { 
		if (context != null) {
			//call stop on the services
			//now start the infrastructure
			for (InfrastructureService service : context.getBeansOfType(InfrastructureService.class).values()) {
				try {
					service.stop();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}//end for			
			context.close();//signal the close
		}//end if
	}
	
}
