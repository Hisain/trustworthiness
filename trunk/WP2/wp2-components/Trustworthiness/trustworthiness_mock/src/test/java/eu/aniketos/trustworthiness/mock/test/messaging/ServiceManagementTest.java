package eu.aniketos.trustworthiness.mock.test.messaging;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.IServiceManagement;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ServiceManagementTest {

	private static Logger logger = Logger.getLogger(ServiceManagementTest.class);

	private IServiceManagement iServiceManagement;

	/**
	 * @param qosMetics
	 */
	public ServiceManagementTest(IServiceManagement iServiceManagement) {
		super();
		this.iServiceManagement = iServiceManagement;
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
				iServiceManagement.addService(serviceData);
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
				iServiceManagement.addService(serviceData);
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
			serviceData.put("serviceId", "testId01");
			serviceData.put("serviceName", "testName1");
			serviceData.put("serviceDescription", "");
			try {
				iServiceManagement.addService(serviceData);
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
				iServiceManagement.addService(serviceId);
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
				iServiceManagement.addService(serviceId);
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
			String serviceId = "testId02";
			try {
				iServiceManagement.addService(serviceId);
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
			String serviceId = "testId01";
			try {
				iServiceManagement.removeService(serviceId);
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

	public IServiceManagement getServiceManagement() {
		return iServiceManagement;
	}

	public void setServiceManagement(IServiceManagement iServiceManagement) {
		this.iServiceManagement = iServiceManagement;
	}
}
