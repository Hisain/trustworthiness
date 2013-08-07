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
 * documentation and/or other materials provided with the distribution.
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
package eu.aniketos.trustworthiness.mock.monitoring;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.IQosMetricsService;
import eu.aniketos.trustworthiness.ext.rules.model.event.TrustEvent;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class QoSMetrics {

	private static Logger logger = Logger.getLogger(QoSMetrics.class);

	private IQosMetricsService qosMetrics;

	/**
	 * @param qosMetics
	 */
	public QoSMetrics(IQosMetricsService qosMetics) {
		super();
		this.qosMetrics = qosMetics;
	}

	/**
	 * 
	 */
	public void initialize() {

		// sending metrics for Aniketos sample services
		logger.info("Sending QoS metrics for Aniketos sample services");

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("data/qos_data.txt"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}

		while (scanner != null && scanner.hasNext()) {

			String line = scanner.next();

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

	}
}
