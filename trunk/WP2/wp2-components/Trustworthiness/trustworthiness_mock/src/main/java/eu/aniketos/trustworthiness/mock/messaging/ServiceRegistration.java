package eu.aniketos.trustworthiness.mock.messaging;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.IServiceManagement;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ServiceRegistration {

	private static Logger logger = Logger.getLogger(ServiceRegistration.class);

	private IServiceManagement iServiceManagement;

	/**
	 * @param qosMetics
	 */
	public ServiceRegistration(IServiceManagement iServiceManagement) {
		super();
		this.iServiceManagement = iServiceManagement;
	}

	/**
	 * 
	 */
	public void initialize() {


		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("data/services_data.txt"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		

		while (scanner != null && scanner.hasNext()) {
			String line = scanner.next();

			String serviceId = line;

			iServiceManagement.addService(serviceId);

			if (logger.isDebugEnabled()) {
				logger.debug("added service: " + serviceId);
			}
		}
	}

	public IServiceManagement getServiceManagement() {
		return iServiceManagement;
	}

	public void setServiceManagement(IServiceManagement iServiceManagement) {
		this.iServiceManagement = iServiceManagement;
	}
}
