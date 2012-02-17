package eu.aniketos.wp2.components.trustworthiness.trust.management.atomic;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;



/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ServiceTrustUpdatePolicy {
	
	/**
	 * updates scores based on new filter score
	 * @param member 
	 *
	 * @param score member score
	 * @throws Exception 
	 */
	public abstract Trustworthiness calculateTrust(Score ratingScore) throws Exception;

	public abstract Trustworthiness calculateTrust(String serviceId) throws Exception;

}
