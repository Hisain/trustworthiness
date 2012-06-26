package eu.aniketos.wp2.components.trustworthiness.messaging;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ReputationMetricsJms {

	/**
	 * method for receiving reputation metrics using JMS
	 */
	public void receiveMetrics();
		
}