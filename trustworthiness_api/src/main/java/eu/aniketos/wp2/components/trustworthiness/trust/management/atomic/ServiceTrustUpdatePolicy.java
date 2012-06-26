package eu.aniketos.wp2.components.trustworthiness.trust.management.atomic;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;



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
	public abstract Trustworthiness calculateTrust(Score ratingScore) throws Exception;

	/**
	 * @param serviceId String service id
	 * @return trustworthiness object
	 * @throws Exception
	 */
	public abstract Trustworthiness calculateTrust(String serviceId) throws Exception;

}
