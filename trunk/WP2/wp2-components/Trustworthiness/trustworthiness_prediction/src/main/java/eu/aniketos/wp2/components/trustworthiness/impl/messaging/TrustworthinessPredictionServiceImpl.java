package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.messaging.ITrustworthinessPrediction;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;
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

	public Trustworthiness getTrustworthiness(String serviceId) throws Exception {

		if (serviceId == null) {
			logger.warn("received serviceId is null");
			throw new Exception("received serviceId is null");
			
		}

		Atomic service = serviceEntityService.getAtomic(serviceId);

		Trustworthiness tw = null;

		if (service != null) {
			tw = trustUpdate.calculateTrust(serviceId);

		} else {

			Composite cs = serviceEntityService.getComposite(serviceId);

			if (cs != null) {
				tw = csTrustUpdate.aggregateTrustworthiness(serviceId);
			} else {
				logger.warn("Could not find service in the repository.");
				throw new Exception("Could not find service in the repository");
			}

		}

		return tw;

	}

	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	public void setServiceEntityService(ServiceEntityService sEntityService) {
		this.serviceEntityService = sEntityService;
	}

	public ServiceTrustUpdatePolicy getTrustUpdate() {
		return trustUpdate;
	}

	public void setTrustUpdate(ServiceTrustUpdatePolicy trustUpdate) {
		this.trustUpdate = trustUpdate;
	}

	public CompositeTrustUpdate getCsTrustUpdate() {
		return csTrustUpdate;
	}

	public void setCsTrustUpdate(CompositeTrustUpdate csTrustUpdate) {
		this.csTrustUpdate = csTrustUpdate;
	}
}
