package eu.aniketos.wp2.components.trustworthiness.messaging;

import java.util.Set;

import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ICompositeTrustworthinessPrediction {
	abstract public Trustworthiness getCompositeTrustworthiness(String serviceId, Set<String> componentServices) throws Exception;
}
