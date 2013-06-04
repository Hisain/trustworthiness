package eu.aniketos.trustworthiness.mock.messaging;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.ServiceManagement;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ServiceRegistration {

	private static Logger logger = Logger.getLogger(ServiceRegistration.class);

	private ServiceManagement serviceManagement;

	/**
	 * @param qosMetics
	 */
	public ServiceRegistration(ServiceManagement serviceManagement) {
		super();
		this.serviceManagement = serviceManagement;
	}

	/**
	 * 
	 */
	public void initialize() {


		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("data\\services_data.txt"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		

		while (scanner != null && scanner.hasNext()) {
			String line = scanner.next();

			String serviceId = line;

			serviceManagement.addService(serviceId);

			if (logger.isDebugEnabled()) {
				logger.debug("added service: " + serviceId);
			}
		}
	}

	public ServiceManagement getServiceManagement() {
		return serviceManagement;
	}

	public void setServiceManagement(ServiceManagement serviceManagement) {
		this.serviceManagement = serviceManagement;
	}
}
