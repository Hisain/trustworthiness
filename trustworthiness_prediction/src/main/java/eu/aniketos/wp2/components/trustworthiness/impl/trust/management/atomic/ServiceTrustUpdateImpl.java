package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.atomic;


import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Service;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;



/**
 * ScoreManager class manages source score and neighbours trust
 *
 * @author: Hisain Elshaafi
 */
public class ServiceTrustUpdateImpl implements ServiceTrustUpdate {

	private static Logger logger = Logger.getLogger(ServiceTrustUpdateImpl.class);

	
	public ServiceTrustUpdateImpl() {
				
	}

	public Trustworthiness calculateScore(Score score, ServiceTrustUpdatePolicy policy) throws Exception{
		return policy.calculateTrust(score);
	}
	
}
