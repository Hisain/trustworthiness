package eu.aniketos.trustworthiness.ext.messaging;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ITrustworthinessPrediction {
	
	/**
	 * @param serviceId a String service id
	 * @return Trustworthiness object
	 * @throws Exception
	 */
	abstract public Trustworthiness getTrustworthiness(String serviceId);
}
