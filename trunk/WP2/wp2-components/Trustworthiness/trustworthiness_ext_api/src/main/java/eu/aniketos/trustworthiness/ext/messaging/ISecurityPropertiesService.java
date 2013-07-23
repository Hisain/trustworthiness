package eu.aniketos.trustworthiness.ext.messaging;

import java.util.Map;

//import eu.aniketos.data.ISecurityProperty;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ISecurityPropertiesService {

	/**
	 * @param metric Map containing an evaluation of a trustworthiness property
	 * 
	 */
	public void receiveProperty(Map<String, String> metric);
	
	/**
	 * @param ISecurityProperty property describes security property and its values
	 * 
	 */
	//public void receiveProperty(String serviceId, ISecurityProperty property, String state);
	
}