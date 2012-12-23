package de.incompleteco.spring.infrastructure.service;

import org.apache.activemq.broker.BrokerService;

public class ActiveMQInfrastructureService implements InfrastructureService {

	private static final String BROKER_URL = "tcp://localhost:61616";
	
	private static BrokerService brokerService;
	
	@Override
	public void start() throws Exception {
		if (brokerService == null) {
			//init
			brokerService = new BrokerService();
			brokerService.addConnector(BROKER_URL);
			brokerService.setUseJmx(false);
			brokerService.setUseShutdownHook(true);
			brokerService.setPersistent(false);
			//start
			brokerService.start();
		}//end if
	}

	@Override
	public void stop() throws Exception {
		if (brokerService != null) {
			brokerService.stop();//exit it
		}//end if
	}

}
