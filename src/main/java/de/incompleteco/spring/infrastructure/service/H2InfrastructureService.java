package de.incompleteco.spring.infrastructure.service;

import org.h2.tools.Server;

/**
 * H2 impl
 * @author Will
 *
 */
public class H2InfrastructureService implements InfrastructureService {

	private static Server server;
	
	@Override
	public void start() throws Exception {
		if (server == null) {
			server = Server.createTcpServer("-tcpAllowOthers").start();
		}//end if
	}

	@Override
	public void stop() throws Exception {
		if (server != null) {
			server.stop();
		}//end if
	}

}
