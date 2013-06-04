package eu.aniketos.trustworthiness.impl.messaging;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.ICompositeTrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.Trustworthiness;
import eu.aniketos.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.trustworthiness.trust.management.composite.CompositeTrustUpdate;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class CompositeTrustworthinessPredictionServiceImpl implements
		ICompositeTrustworthinessPrediction {

	private static Logger logger = Logger
			.getLogger(CompositeTrustworthinessPredictionServiceImpl.class);

	private ServiceEntityService serviceEntityService;

	private CompositeTrustUpdate csTrustUpdate;

	private TrustFactory tFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.messaging.
	 * ICompositeTrustworthinessPrediction
	 * #getCompositeTrustworthiness(java.lang.String, java.util.Set)
	 */
	public Trustworthiness getCompositeTrustworthiness(String serviceId,
			Set<String> componentServices) {

		// return an exception if empty or null parameters
		if (serviceId == null || componentServices == null
				|| serviceId.length() == 0 || componentServices.size() == 0) {
			logger.warn("received serviceId or components are null or empty");
			throw new RuntimeException(
					"received serviceId or components are null or empty");
		}

		Composite cs = serviceEntityService.getComposite(serviceId);

		Trustworthiness trustworthiness = null;

		try {
			if (cs == null) {
				logger.info("Could not find service in the repository. New composite service will be created");

				cs = tFactory.createComposite(serviceId);

				Set<Atomic> services = new HashSet<Atomic>();

				for (String s : componentServices) {

					Atomic service = serviceEntityService.getAtomic(s);

					// return an exception if component service is unknown
					if (service == null)
						throw new RuntimeException("Could not find service " + s
								+ " in the repository");

					logger.debug("adding component " + service.getId());
					services.add(service);
				}
				cs.setComponentServices(services);

				serviceEntityService.addComposite(cs);

				trustworthiness = csTrustUpdate.aggregateTrustworthiness(serviceId);

			} else {

				logger.debug("aggregating trustworthiness");
				trustworthiness = csTrustUpdate.aggregateTrustworthiness(serviceId);

			}
		} catch (Exception e) {
			
			logger.error("Exception: " + e.getMessage());
		}

		return trustworthiness;

	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return data access service for atomic and composite Web services
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param sEntityService
	 *            data access service for atomic and composite Web services
	 */
	public void setServiceEntityService(ServiceEntityService sEntityService) {
		this.serviceEntityService = sEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public CompositeTrustUpdate getCsTrustUpdate() {
		return csTrustUpdate;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param csTrustUpdate
	 */
	public void setCsTrustUpdate(CompositeTrustUpdate csTrustUpdate) {
		this.csTrustUpdate = csTrustUpdate;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return service and rating score objects factory
	 */
	public TrustFactory gettFactory() {
		return tFactory;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param tFactory
	 *            service and rating score objects factory
	 */
	public void settFactory(TrustFactory tFactory) {
		this.tFactory = tFactory;
	}

}
