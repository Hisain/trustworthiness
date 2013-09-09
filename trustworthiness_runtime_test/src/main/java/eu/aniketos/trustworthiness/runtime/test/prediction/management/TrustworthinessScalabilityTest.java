/**
 * Copyright (c) 2013, Waterford Institute of Technology
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    - Neither the name of Waterford Institute of Technology nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *      
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL WATERFORD INSTITUTE OF TECHNOLOGY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.aniketos.trustworthiness.runtime.test.prediction.management;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import eu.aniketos.trustworthiness.ext.messaging.ICompositeTrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.IQosMetricsService;
import eu.aniketos.trustworthiness.ext.messaging.IReputationRatingsService;
import eu.aniketos.trustworthiness.ext.messaging.ISecurityPropertiesService;
import eu.aniketos.trustworthiness.ext.messaging.ITrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.Trustworthiness;
import eu.aniketos.trustworthiness.ext.rules.model.event.ConsumerRatingEvent;
import eu.aniketos.trustworthiness.ext.rules.model.event.TrustEvent;
import eu.aniketos.trustworthiness.runtime.test.monitoring.event.ConsumerRatingEventImpl;
import eu.aniketos.trustworthiness.runtime.test.monitoring.event.MetricEventImpl;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class TrustworthinessScalabilityTest {

	private static Logger logger = Logger
			.getLogger(TrustworthinessScalabilityTest.class);

	private ITrustworthinessPrediction twPrediction;
	private ICompositeTrustworthinessPrediction ctwPrediction;

	private IQosMetricsService qosMetrics;
	
	private IReputationRatingsService repMetrics;
	
	private ISecurityPropertiesService securityMetrics;

	/**
	 * @param twPrediction
	 * @param ctwPrediction
	 */
	public TrustworthinessScalabilityTest(
			ITrustworthinessPrediction twPrediction,
			ICompositeTrustworthinessPrediction ctwPrediction, IQosMetricsService qosMetrics, IReputationRatingsService repMetrics, ISecurityPropertiesService securityMetics) {
		super();
		this.twPrediction = twPrediction;
		this.ctwPrediction = ctwPrediction;
		this.qosMetrics = qosMetrics;
		this.repMetrics = repMetrics;
		this.securityMetrics = securityMetics;
	}

	/**
	 * 
	 */
	public void requestTrustworthiness() {

		
		logger.info("Sending QoS metrics for scalability test services");

		Scanner scanner1 = null;
		try {
			scanner1 = new Scanner(new File("data/scalability_test/qos_data.txt"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}

		while (scanner1 != null && scanner1.hasNext()) {

			String line = scanner1.next();

			String[] qosData = line.split(",");

			TrustEvent event = new MetricEventImpl();

			event.setServiceId(qosData[0]);

			event.setProperty(qosData[1]);

			event.setSubproperty(qosData[2]);

			event.setValue(qosData[3]);

			try {
				qosMetrics.processQoSMetric(event);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			if (logger.isDebugEnabled()) {
				logger.debug("added QoS for service: " + qosData[0]);
			}
		}
		
		logger.info("Sending reputation ratings for Aniketos sample services");

		Scanner scanner2 = null;

		try {

			scanner2 = new Scanner(new File("data/scalability_test/reputation_data.txt"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}

		while (scanner2 != null && scanner2.hasNext()) {
			String line = scanner2.next();

			String[] qosData = line.split(",");

			ConsumerRatingEvent event = new ConsumerRatingEventImpl();

			event.setServiceId(qosData[0]);

			event.setProperty(qosData[1]);

			event.setConsumerId(qosData[2]);

			event.setTransactionId(qosData[3]);

			event.setValue(qosData[4]);

			DateTime dt = new DateTime();
			DateTimeFormatter fmt = ISODateTimeFormat.dateTimeNoMillis();
			String timestamp = fmt.print(dt);

			event.setTimestamp(timestamp);

			try {
				repMetrics.processReputationRating(event);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			if (logger.isDebugEnabled()) {
				logger.debug("added reputation for service: " + qosData[0]);
			}
		}
		
		logger.info("Sending security metrics for Aniketos sample services");

		Scanner scanner3 = null;
		try {
			scanner3 = new Scanner(new File("data/scalability_test/security_data.txt"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}

		while (scanner3 != null && scanner3.hasNext()) {

			String line = scanner3.next();

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
		
		
		int counter = 0;

		while (counter < 100) {
			
			counter++;
			
			int numServices = 0;
			
			Scanner scanner4 = null;
			try {
				scanner4 = new Scanner(new File("data/scalability_test/services_data.txt"));

			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			}

			Set<String> services = new HashSet<String>();
			
			while (scanner4 != null && scanner4.hasNext() && numServices <= counter) {

				String line = scanner4.nextLine();

				String serviceId = line;

				
				services.add(serviceId);
								
				numServices++;
				
				if (!scanner4.hasNext() && numServices < counter){
					try {
						scanner4 = new Scanner(new File("data/scalability_test/services_data.txt"));

					} catch (FileNotFoundException e) {
						logger.error(e.getMessage());
					}
				}
				
			}
			logger.debug("sending trustworthiness request for " + services.size() + " services.");
			ctwPrediction.getCompositeTrustworthiness("x1", services);
			logger.debug("received trustworthiness response.");
			

		}

		// ////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\

		
	}
}
