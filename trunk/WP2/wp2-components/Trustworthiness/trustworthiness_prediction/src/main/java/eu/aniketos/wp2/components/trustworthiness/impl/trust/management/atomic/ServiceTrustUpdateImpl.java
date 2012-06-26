package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.atomic;


import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;



/**
 * ScoreManager class manages source score and neighbours trust
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
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdate#calculateScore(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score, eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy)
	 */
	public Trustworthiness calculateScore(Score score, ServiceTrustUpdatePolicy policy) throws Exception{
		return policy.calculateTrust(score);
	}
	
}
