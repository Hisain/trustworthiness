package eu.aniketos.wp2.components.trustworthiness.mock.messaging;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.messaging.ServiceManagement;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ServiceManagementTest {

	private static Logger logger = Logger.getLogger(ServiceManagementTest.class);

	private ServiceManagement serviceManagement;

	/**
	 * @param qosMetics
	 */
	public ServiceManagementTest(ServiceManagement serviceManagement) {
		super();
		this.serviceManagement = serviceManagement;
	}

	/**
	 * 
	 */
	public void initialize() {

		// metric with null or empty service id
		logger.info("metric with null or empty service id");
		for (int i = 0; i < 1; i++) {
			Map<String, String> serviceData = new HashMap<String, String>();
			serviceData.put("serviceId", null);
			serviceData.put("serviceName", "testName1");
			serviceData.put("serviceDescription", "");
			try {
				serviceManagement.addService(serviceData);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		
		
		for (int i = 0; i < 1; i++) {
			Map<String, String> serviceData = new HashMap<String, String>();
			serviceData.put("serviceId", "");
			serviceData.put("serviceName", "testName1");
			serviceData.put("serviceDescription", "");
			try {
				serviceManagement.addService(serviceData);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		
		
		for (int i = 0; i < 1; i++) {
			Map<String, String> serviceData = new HashMap<String, String>();
			serviceData.put("serviceId", "testId1");
			serviceData.put("serviceName", "testName1");
			serviceData.put("serviceDescription", "");
			try {
				serviceManagement.addService(serviceData);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		
		for (int i = 0; i < 1; i++) {
			String serviceId = null;
			try {
				serviceManagement.addService(serviceId);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		
		for (int i = 0; i < 1; i++) {
			String serviceId = "";
			try {
				serviceManagement.addService(serviceId);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		
		for (int i = 0; i < 1; i++) {
			String serviceId ="testId2";
			try {
				serviceManagement.addService(serviceId);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		
		for (int i = 0; i < 1; i++) {
			String serviceId = "testId1";
			try {
				serviceManagement.removeService(serviceId);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
	}

	public ServiceManagement getServiceManagement() {
		return serviceManagement;
	}

	public void setServiceManagement(ServiceManagement serviceManagement) {
		this.serviceManagement = serviceManagement;
	}
}