package eu.aniketos.wp2.components.trustworthiness.messaging;

import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ITrustworthinessPrediction {
	
	abstract public Trustworthiness getTrustworthiness(String serviceId) throws Exception;
}
