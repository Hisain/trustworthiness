package eu.aniketos.wp2.components.trustworthiness.impl.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationBuilder;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ConfigurationService implements ConfigurationManagement {

	private static Logger logger = Logger.getLogger(ConfigurationService.class);

	private ConfigurationBuilder configBuilder = null;

	private static Configuration config = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.wp2.components.trustworthiness.configuration.
	 * ConfigurationManagement#getConfig()
	 */
	public Configuration getConfig() {
		
		if (config == null) {
			try {
				config = configBuilder.getConfiguration();
			} catch (ConfigurationException e) {
				logger.error(e.getMessage());
			}
		}
		return config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.wp2.components.trustworthiness.configuration.
	 * ConfigurationManagement#getConfigBuilder()
	 */
	public ConfigurationBuilder getConfigBuilder() {
		return configBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.wp2.components.trustworthiness.configuration.
	 * ConfigurationManagement
	 * #setConfigBuilder(org.apache.commons.configuration.ConfigurationBuilder)
	 */
	public void setConfigBuilder(ConfigurationBuilder configBuilder) {
		this.configBuilder = configBuilder;
	}

}
