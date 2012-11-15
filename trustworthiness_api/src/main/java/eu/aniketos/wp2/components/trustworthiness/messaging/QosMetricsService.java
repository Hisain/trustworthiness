package eu.aniketos.wp2.components.trustworthiness.messaging;

import java.util.Map;

import eu.aniketos.wp2.components.trustworthiness.rules.model.event.TrustEvent;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface QosMetricsService {

	/**
	 * @param metric Map containing an evaluation of a trustworthiness property
	 * @throws Exception
	 */
	public abstract void receiveMetrics(Map<String, String> metric) throws Exception;

	/**
	 * @param event
	 */
	public abstract void processQoSMetric(TrustEvent event) throws Exception;
	
}