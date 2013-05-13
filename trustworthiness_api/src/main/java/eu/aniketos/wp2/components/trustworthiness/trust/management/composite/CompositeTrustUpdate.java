package eu.aniketos.wp2.components.trustworthiness.trust.management.composite;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.TrustworthinessEntity;



/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface CompositeTrustUpdate {
	
	/**
	 * @param serviceId String service id
	 * @return trustworthiness object
	 * @throws Exception
	 */
	abstract public TrustworthinessEntity aggregateTrustworthiness(String serviceId) throws Exception;
}
