package eu.aniketos.wp2.components.trustworthiness.trust.management.atomic;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ServiceTrustUpdate {

	/**
	 * @param score a rating score
	 * @param policy a configuration of trustworthiness procedure
	 * @return trustworthiness object
	 * @throws Exception
	 */
	public abstract Trustworthiness calculateScore(Score score, ServiceTrustUpdatePolicy policy) throws Exception;

}