package de.incompleteco.spring.infrastructure.service;

/**
 * interface to start & stop a service
 * @author Will
 *
 */
public interface InfrastructureService {

	public void start() throws Exception;
	
	public void stop() throws Exception;
	
}
