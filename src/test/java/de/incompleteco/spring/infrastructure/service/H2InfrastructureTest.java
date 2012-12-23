package de.incompleteco.spring.infrastructure.service;

import static org.junit.Assert.assertTrue;

import java.sql.DriverManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/META-INF/spring/infrastructure-context.xml"})
public class H2InfrastructureTest {

	@Autowired
	@Qualifier("h2InfrastructureService")
	private InfrastructureService service;
	
	@Test
	public void test() throws Exception {
		service.start();
		//check
		Class.forName("org.h2.Driver");
		assertTrue(DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test","sa","").isValid(1000));
		//stop the service
		service.stop();
	}

}
