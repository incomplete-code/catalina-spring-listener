package de.incompleteco.spring.infrastructure.service;

import org.h2.tools.Server;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

/**
 * H2 impl
 * @author Will
 *
 */
public class H2InfrastructureService implements InfrastructureService, ApplicationContextAware {

	private static Server server;
	
	private ApplicationContext context;
	
	@Override
	public void start() throws Exception {
		if (server == null) {
			server = Server.createTcpServer("-tcpAllowOthers").start();
			//initializer
			processInitializer();
		}//end if
	}

	@Override
	public void stop() throws Exception {
		if (server != null) {
			server.stop();
		}//end if
	}
	
	protected void processInitializer() {
		if (context.getBeansOfType(DataSourceInitializer.class) == null) {
			return;//nothing to process
		}//end if
		//loop through possibile initializers
		for (DataSourceInitializer initializer : context.getBeansOfType(DataSourceInitializer.class).values()) {
			//set it to enabled and execute
			initializer.setEnabled(true);
			initializer.afterPropertiesSet();//init
			//reset
			initializer.setEnabled(false);//to stop the clean up part			
		}//end for
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
