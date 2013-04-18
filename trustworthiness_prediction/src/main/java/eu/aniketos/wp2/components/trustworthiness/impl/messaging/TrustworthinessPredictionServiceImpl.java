package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Trustworthiness;
import eu.aniketos.wp2.components.trustworthiness.messaging.ITrustworthinessPrediction;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.trust.management.composite.CompositeTrustUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class TrustworthinessPredictionServiceImpl implements
		ITrustworthinessPrediction {

	private static Logger logger = Logger
			.getLogger(TrustworthinessPredictionServiceImpl.class);

	private ServiceEntityService serviceEntityService;

	ServiceTrustUpdatePolicy trustUpdate;

	CompositeTrustUpdate csTrustUpdate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.wp2.components.trustworthiness.messaging.
	 * ITrustworthinessPrediction#getTrustworthiness(java.lang.String)
	 */
	public Trustworthiness getTrustworthiness(String serviceId)
			throws Exception {

		if (serviceId == null) {
			logger.warn("received serviceId is null");
			throw new Exception("received serviceId is null");

		}

		Trustworthiness trustworthiness = null;

		if (serviceEntityService.isAtomic(serviceId)) {

			trustworthiness = trustUpdate.updateTrust(serviceId);

		} else if (serviceEntityService.isComposite(serviceId)) {

			trustworthiness = csTrustUpdate.aggregateTrustworthiness(serviceId);
		} else {
			logger.warn("Could not find service in " + serviceId
					+ " the repository.");
			throw new Exception("Could not find service in " + serviceId
					+ " the repository");
		}

		return trustworthiness;

	}

	/**
	 * @return
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * @param sEntityService
	 */
	public void setServiceEntityService(ServiceEntityService sEntityService) {
		this.serviceEntityService = sEntityService;
	}

	/**
	 * @return
	 */
	public ServiceTrustUpdatePolicy getTrustUpdate() {
		return trustUpdate;
	}

	/**
	 * @param trustUpdate
	 */
	public void setTrustUpdate(ServiceTrustUpdatePolicy trustUpdate) {
		this.trustUpdate = trustUpdate;
	}

	/**
	 * @return
	 */
	public CompositeTrustUpdate getCsTrustUpdate() {
		return csTrustUpdate;
	}

	/**
	 * @param csTrustUpdate
	 */
	public void setCsTrustUpdate(CompositeTrustUpdate csTrustUpdate) {
		this.csTrustUpdate = csTrustUpdate;
	}
}
