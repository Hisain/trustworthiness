package eu.aniketos.trustworthiness.mock.monitoring;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.ISecurityPropertiesService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class SecurityProperties {

	private static Logger logger = Logger.getLogger(SecurityProperties.class);

	private ISecurityPropertiesService securityMetrics;

	/**
	 * @param securityMetics
	 */
	public SecurityProperties(ISecurityPropertiesService securityMetics) {
		super();
		this.securityMetrics = securityMetics;
	}

	/**
	 * 
	 */
	public void initialize() {

		// sending metrics for Aniketos sample services
		logger.info("Sending security metrics for Aniketos sample services");



		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("data/security_data.txt"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		

		while (scanner != null && scanner.hasNext()) {

			String line = scanner.next();

			String[] securityData = line.split(",");

			Map<String, String> securityProperty = new HashMap<String, String>();

			securityProperty.put("serviceId", securityData[0]);

			securityProperty.put("property", securityData[1]);

			securityProperty.put("subproperty", securityData[2]);

			securityProperty.put("value", securityData[3]);
			

			try {
				
				securityMetrics.receiveProperty(securityProperty);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			if (logger.isDebugEnabled()) {
				logger.debug("added security property for service: "
						+ securityData[0]);
			}
		}

	}
}
