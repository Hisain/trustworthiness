package eu.aniketos.trustworthiness.trust.management.atomic;

import eu.aniketos.trustworthiness.impl.trust.pojo.Rating;
import eu.aniketos.trustworthiness.impl.trust.pojo.TrustworthinessEntity;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ServiceTrustUpdate {

	/**
	 * @param rating a rating score
	 * @param policy a configuration of trustworthiness procedure
	 * @return trustworthiness object
	 * @throws Exception
	 */
	public abstract TrustworthinessEntity calculateScore(Rating rating, ServiceTrustUpdatePolicy policy) throws Exception;

}