package eu.aniketos.wp2.components.trustworthiness.trust.management.atomic;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.TrustworthinessEntity;



/**
 * invokes calculation of trustworthiness 
 * 
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ServiceTrustUpdatePolicy {
	
	/**
	 * updates scores based on new filter score
	 * @param score a service rating score
	 * @throws Exception 
	 */
	public abstract TrustworthinessEntity calculateTrust(Rating ratingScore) throws Exception;

	/**
	 * @param serviceId String service id
	 * @return trustworthiness object
	 * @throws Exception
	 */
	public abstract TrustworthinessEntity updateTrust(String serviceId) throws Exception;

}
