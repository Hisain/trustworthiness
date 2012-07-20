package eu.aniketos.wp2.components.trustworthiness.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationBuilder;

/**
 * @author Hisain Elshaafi
 * 
 */
public interface ConfigurationManagement {
	 
	/**
	 * @return configuration object
	 */
	
	Configuration getConfig();
	 
	/**
	 * @return Apache Configuration configurationbuilder that can create
	 * configuration objects
	 */	
	public ConfigurationBuilder getConfigBuilder();
	 
	/**
	 * @param configBuilder Apache ConfigurationBuilder interface
	 */
	public void setConfigBuilder(ConfigurationBuilder configBuilder);
}
