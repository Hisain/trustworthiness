package eu.aniketos.wp2.components.trustworthiness.trust.management.atomic;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ServiceTrustUpdate {

	public abstract Trustworthiness calculateScore(Score score, ServiceTrustUpdatePolicy policy) throws Exception;

}