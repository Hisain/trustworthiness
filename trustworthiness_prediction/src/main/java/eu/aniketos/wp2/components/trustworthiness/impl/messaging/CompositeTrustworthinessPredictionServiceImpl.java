package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.management.composite.CompositeTrustUpdateImpl;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.messaging.ICompositeTrustworthinessPrediction;
import eu.aniketos.wp2.components.trustworthiness.messaging.ITrustworthinessPrediction;
import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;
import eu.aniketos.wp2.components.trustworthiness.trust.management.composite.CompositeTrustUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;

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

	public Trustworthiness getCompositeTrustworthiness(String serviceId,
			Set<String> componentServices) throws Exception {
		
		// return an exception if empty or null parameters
		if (serviceId == null || componentServices== null || serviceId.length() == 0 || componentServices.size()==0) {
			logger.warn("received serviceId or components are null or empty");
			throw new Exception("received serviceId or components are null or empty");
		}

		Composite cs = serviceEntityService.getComposite(serviceId);

		Trustworthiness tw = null;

		if (cs == null) {
			logger.info("Could not find service in the repository. New composite service will be created");

			cs = tFactory.createComposite(serviceId);

			Set<Atomic> services = new HashSet<Atomic>();
			for (String s : componentServices) {
				
				Atomic service = serviceEntityService.getAtomic(s);
				
				
				// return an exception if component service is unknown
				if (service==null) throw new Exception("Could not find service " + s + " in the repository");
				
				logger.debug("adding component " + service.getId());
				services.add(service);
			}
			cs.setComponentServices(services);
			
			serviceEntityService.addComposite(cs);

			tw = csTrustUpdate.aggregateTrustworthiness(serviceId);
			
		} else {
			
				logger.debug("aggregating trustworthiness");
				tw = csTrustUpdate.aggregateTrustworthiness(serviceId);
			
		}

		return tw;

	}

	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	public void setServiceEntityService(ServiceEntityService sEntityService) {
		this.serviceEntityService = sEntityService;
	}
	
	public CompositeTrustUpdate getCsTrustUpdate() {
		return csTrustUpdate;
	}

	public void setCsTrustUpdate(CompositeTrustUpdate csTrustUpdate) {
		this.csTrustUpdate = csTrustUpdate;
	}

	public TrustFactory gettFactory() {
		return tFactory;
	}

	public void settFactory(TrustFactory tFactory) {
		this.tFactory = tFactory;
	}

}
