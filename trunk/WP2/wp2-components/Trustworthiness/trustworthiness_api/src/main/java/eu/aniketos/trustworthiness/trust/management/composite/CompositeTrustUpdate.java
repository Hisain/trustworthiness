package eu.aniketos.trustworthiness.trust.management.composite;

import eu.aniketos.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.trustworthiness.impl.trust.pojo.TrustworthinessEntity;



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
	public abstract TrustworthinessEntity aggregateTrustworthiness(String serviceId) throws Exception;

	public abstract TrustworthinessEntity aggregateTrustworthiness(Composite cs) throws Exception;
}
