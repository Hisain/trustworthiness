package eu.aniketos.wp2.components.trustworthiness.trust.management.composite;

import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface CompositeTrustUpdate {
	abstract public Trustworthiness aggregateTrustworthiness(String serviceId) throws Exception;
}
