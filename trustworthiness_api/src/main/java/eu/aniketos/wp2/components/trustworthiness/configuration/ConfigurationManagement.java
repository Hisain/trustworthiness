package eu.aniketos.wp2.components.trustworthiness.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationBuilder;

/**
 * @author Hisain Elshaafi
 * 
 */
public interface ConfigurationManagement {
	 Configuration getConfig();
	 public ConfigurationBuilder getConfigBuilder();
	 public void setConfigBuilder(ConfigurationBuilder configBuilder);
}
