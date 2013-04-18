package eu.aniketos.wp2.components.trustworthiness.trust.management.atomic;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Trustworthiness;



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
	public abstract Trustworthiness calculateTrust(Rating ratingScore) throws Exception;

	/**
	 * @param serviceId String service id
	 * @return trustworthiness object
	 * @throws Exception
	 */
	public abstract Trustworthiness updateTrust(String serviceId) throws Exception;

}
