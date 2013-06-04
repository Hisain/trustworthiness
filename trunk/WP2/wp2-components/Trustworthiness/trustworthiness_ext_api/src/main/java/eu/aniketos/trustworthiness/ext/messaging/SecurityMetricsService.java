package eu.aniketos.trustworthiness.ext.messaging;

import java.util.Map;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface SecurityMetricsService {

	/**
	 * @param metric Map containing an evaluation of a trustworthiness property
	 * @throws Exception
	 */
	public void receiveMetrics(Map<String, String> metric);
	
}