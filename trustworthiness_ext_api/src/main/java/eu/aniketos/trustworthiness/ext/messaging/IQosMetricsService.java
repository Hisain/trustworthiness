package eu.aniketos.trustworthiness.ext.messaging;

import java.util.Map;

import eu.aniketos.trustworthiness.ext.rules.model.event.TrustEvent;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface IQosMetricsService {

	/**
	 * @param metric Map containing an evaluation of a trustworthiness property
	 * @throws Exception
	 */
	public abstract void receiveMetrics(Map<String, String> metric);

	/**
	 * @param event
	 */
	public abstract void processQoSMetric(TrustEvent event);
	
}