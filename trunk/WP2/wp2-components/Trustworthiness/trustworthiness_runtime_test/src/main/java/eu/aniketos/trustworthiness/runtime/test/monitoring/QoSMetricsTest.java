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
package eu.aniketos.trustworthiness.runtime.test.monitoring;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.IQosMetricsService;
import eu.aniketos.trustworthiness.ext.rules.model.event.TrustEvent;
import eu.aniketos.trustworthiness.runtime.test.monitoring.event.MetricEventImpl;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class QoSMetricsTest {

	private static Logger logger = Logger.getLogger(QoSMetricsTest.class);

	private IQosMetricsService qosMetrics;

	/**
	 * @param qosMetics
	 */
	public QoSMetricsTest(IQosMetricsService qosMetics) {
		super();
		this.qosMetrics = qosMetics;
	}

	/**
	 * 
	 */
	public void initialize() {

		Map<String, String> metric = new HashMap<String, String>();
		metric.put("serviceId", "Aid1130Test");
		metric.put("property", "performance");
		metric.put("subproperty", "delay");
		metric.put("limit", "W");
		metric.put("value", "W98");
		try {
			qosMetrics.receiveMetrics(metric);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		/*
		 * Random r = new Random();
		 * 
		 * // metric with null or empty service id
		 * logger.info("metric with null or empty service id");
		 * 
		 * for (int i = 0; i < 1; i++) { TrustEvent event = new
		 * MetricEventImpl(); event.setServiceId(null);
		 * event.setProperty("availability"); event.setSubproperty("uptime");
		 * event.setValue(Double.toString(0.9 + r.nextDouble() / 10)); try {
		 * qosMetrics.processQoSMetric(event); } catch (Exception e) {
		 * logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); }
		 * 
		 * for (int i = 0; i < 1; i++) { Map<String, String> metric = new
		 * HashMap<String, String>(); metric.put("serviceId", null);
		 * metric.put("property", "availability"); metric.put("subproperty",
		 * "uptime"); metric.put("value", Double.toString(0.9 + r.nextDouble() /
		 * 10)); try { qosMetrics.receiveMetrics(metric); } catch (Exception e)
		 * { logger.error(e.getMessage()); } }
		 * 
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); } for (int i = 0; i < 1; i++) {
		 * Map<String, String> metric = new HashMap<String, String>();
		 * metric.put("serviceId", ""); metric.put("property", "availability");
		 * metric.put("subproperty", "uptime"); metric.put("value",
		 * Double.toString(0.9 + r.nextDouble() / 10)); try {
		 * qosMetrics.receiveMetrics(metric); } catch (Exception e) {
		 * logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); } for (int i = 0; i < 1; i++) {
		 * TrustEvent event = new MetricEventImpl();
		 * event.setServiceId("testId05"); event.setProperty("availability");
		 * event.setSubproperty("uptime"); event.setValue(Double.toString(0.9 +
		 * r.nextDouble() / 10)); try { qosMetrics.processQoSMetric(event); }
		 * catch (Exception e) { logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); } for (int i = 0; i < 10; i++) {
		 * Map<String, String> metric = new HashMap<String, String>();
		 * metric.put("serviceId", "testId05"); metric.put("property",
		 * "availability"); metric.put("subproperty", "uptime");
		 * metric.put("value", Double.toString(0.9 + r.nextDouble() / 10)); try
		 * { qosMetrics.receiveMetrics(metric); } catch (Exception e) {
		 * logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); }
		 * logger.info("sending metric as null"); for (int i = 0; i < 2; i++) {
		 * Map<String, String> metric = null; try {
		 * qosMetrics.receiveMetrics(metric); } catch (Exception e) {
		 * logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); }
		 * logger.info("sending metric as empty"); for (int i = 0; i < 2; i++) {
		 * Map<String, String> metric = new HashMap<String, String>(); try {
		 * qosMetrics.receiveMetrics(metric); } catch (Exception e) {
		 * logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); } for (int i = 0; i < 10; i++) {
		 * Map<String, String> metric = new HashMap<String, String>();
		 * metric.put("serviceId", "testId05"); metric.put("property",
		 * "reliability"); metric.put("subproperty", "error");
		 * metric.put("value", Double.toString(0.9 + r.nextDouble() / 10)); try
		 * { qosMetrics.receiveMetrics(metric); } catch (Exception e) {
		 * logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); } for (int i = 0; i < 10; i++) {
		 * Map<String, String> metric = new HashMap<String, String>();
		 * metric.put("serviceId", "testId07"); metric.put("property",
		 * "reliability"); metric.put("subproperty", "error");
		 * metric.put("value", Double.toString(0.9 + r.nextDouble() / 10)); try
		 * { qosMetrics.receiveMetrics(metric); } catch (Exception e) {
		 * logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); } for (int i = 0; i < 10; i++) {
		 * Map<String, String> metric = new HashMap<String, String>();
		 * metric.put("serviceId", "testId08"); metric.put("property",
		 * "reliability"); metric.put("subproperty", "error");
		 * metric.put("value", Double.toString(0.5 + r.nextDouble() / 10)); try
		 * { qosMetrics.receiveMetrics(metric); } catch (Exception e) {
		 * logger.error(e.getMessage()); } }
		 */

	}
}
