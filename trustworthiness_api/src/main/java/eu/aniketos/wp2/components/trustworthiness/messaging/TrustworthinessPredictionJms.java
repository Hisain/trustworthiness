package eu.aniketos.wp2.components.trustworthiness.messaging;


import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface TrustworthinessPredictionJms {

	public abstract void sendTrustworthiness(Trustworthiness trust) throws Exception;

}