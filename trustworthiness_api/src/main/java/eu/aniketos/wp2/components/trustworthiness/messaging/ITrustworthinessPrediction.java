package eu.aniketos.wp2.components.trustworthiness.messaging;

import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ITrustworthinessPrediction {
	
	/**
	 * @param serviceId a String service id
	 * @return Trustworthiness object
	 * @throws Exception
	 */
	abstract public Trustworthiness getTrustworthiness(String serviceId) throws Exception;
}
