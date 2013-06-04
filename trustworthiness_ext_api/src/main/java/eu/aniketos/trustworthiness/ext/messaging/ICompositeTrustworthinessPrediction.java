package eu.aniketos.trustworthiness.ext.messaging;

import java.util.Set;




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
	abstract public Trustworthiness getCompositeTrustworthiness(String serviceId, Set<String> componentServices);
}
