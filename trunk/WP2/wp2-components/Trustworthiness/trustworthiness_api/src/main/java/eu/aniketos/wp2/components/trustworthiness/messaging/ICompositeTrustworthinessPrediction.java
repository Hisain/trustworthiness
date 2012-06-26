package eu.aniketos.wp2.components.trustworthiness.messaging;

import java.util.Set;

import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ICompositeTrustworthinessPrediction {
	
	/**
	 * @param serviceId a String composite service id
	 * @param componentServices set of components' String ids
	 * @return Trustworthiness object
	 * @throws Exception
	 */
	abstract public Trustworthiness getCompositeTrustworthiness(String serviceId, Set<String> componentServices) throws Exception;
}
