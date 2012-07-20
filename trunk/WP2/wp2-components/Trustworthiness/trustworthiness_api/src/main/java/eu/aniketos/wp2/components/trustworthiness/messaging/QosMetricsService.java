package eu.aniketos.wp2.components.trustworthiness.messaging;

import java.util.Map;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface QosMetricsService {

	/**
	 * @param metric Map containing an evaluation of a trustworthiness property
	 * @throws Exception
	 */
	public void receiveMetrics(Map<String, String> metric) throws Exception;
	
}