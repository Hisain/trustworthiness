package eu.aniketos.trustworthiness.impl.messaging;

import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.ServiceManagement;
import eu.aniketos.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;

public class ServiceManagementImpl implements ServiceManagement {

	private static Logger logger = Logger.getLogger(ServiceManagementImpl.class);
			
	private ServiceEntityService serviceEntityService;
	
	private TrustFactory trustFactory;
	
	public void addService(Map<String, String> serviceData) {
		if (serviceData == null
				|| serviceData.size() == 0
				|| !serviceData.containsKey("serviceId")
				|| serviceData.get("serviceId") == null
				|| serviceData.get("serviceId") == ""
				) {
			logger.warn("received service data contains null or empty element");
			throw new RuntimeException("received service data contains null or empty element");

		} else {

			String serviceId = serviceData.get("serviceId");
			Atomic service = null;
			if ((service = serviceEntityService.getAtomic(serviceId)) != null) {
				logger.warn("service " + serviceId + " already exists");
			} else {
				logger.info("creating new service entry");
				service = trustFactory.createService(serviceId);

				serviceEntityService.addAtomic(service);
			}
		}
	}

	public void addService(String serviceId) {
		if (serviceId == null
				|| serviceId.length() == 0
				) {
			logger.warn("received serviceId is null or empty");
			throw new RuntimeException("received serviceId is null or empty");

		} else {

			Atomic service = null;
			if ((service = serviceEntityService.getAtomic(serviceId)) != null) {
				logger.warn("service " + serviceId + " already exists");
			} else {
				logger.info("creating new service entry");
				service = trustFactory.createService(serviceId);

				serviceEntityService.addAtomic(service);
			}
		}
	}

	public void removeService(String serviceId) {
		if (serviceId == null
				|| serviceId.length() == 0
				) {
			logger.warn("received serviceId is null or empty");
			
			throw new RuntimeException("received serviceId is null or empty");

		} else {

			Atomic service = null;
			if ((service = serviceEntityService.getAtomic(serviceId)) == null) {
				logger.warn("service " + serviceId + " does not exist");
			} else {
				logger.info("deleting service entry");

				serviceEntityService.deleteAtomic(service);
			}
		}
	}
	
	/**
	 * required for Spring dependency injection
	 * 
	 * @param serviceEntityService data access service object for Web services
	 */
	public void setServiceEntityService(
			ServiceEntityService serviceEntityService) {
		this.serviceEntityService = serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return data access service object for Web services
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}
	
	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public TrustFactory getTrustFactory() {
		return trustFactory;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param trustFactory
	 */
	public void setTrustFactory(TrustFactory trustFactory) {
		this.trustFactory = trustFactory;
	}

}
