package eu.aniketos.trustworthiness.impl.trust.management.atomic;


import eu.aniketos.trustworthiness.impl.trust.pojo.Rating;
import eu.aniketos.trustworthiness.impl.trust.pojo.TrustworthinessEntity;
import eu.aniketos.trustworthiness.trust.management.atomic.ServiceTrustUpdate;
import eu.aniketos.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;



/**
 * ServiceTrustUpdateImpl
 *
 * @author: Hisain Elshaafi
 */
public class ServiceTrustUpdateImpl implements ServiceTrustUpdate {
	
	/**
	 * default constructor
	 */
	public ServiceTrustUpdateImpl() {
				
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.management.atomic.ServiceTrustUpdate#calculateScore(eu.aniketos.trustworthiness.impl.trust.pojo.Rating, eu.aniketos.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy)
	 */
	public TrustworthinessEntity calculateScore(Rating rating, ServiceTrustUpdatePolicy policy) throws Exception{
		return policy.calculateTrust(rating);
	}
	
}
