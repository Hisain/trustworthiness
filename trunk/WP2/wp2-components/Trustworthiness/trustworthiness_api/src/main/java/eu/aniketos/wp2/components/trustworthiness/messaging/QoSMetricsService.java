package eu.aniketos.wp2.components.trustworthiness.messaging;

import java.util.Map;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface QoSMetricsService {

	public void receiveMetrics(Map<String, String> metric) throws Exception;
	
}