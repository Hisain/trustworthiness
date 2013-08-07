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

import eu.aniketos.trustworthiness.ext.rules.model.event.TrustEvent;

/**
 * metric events class
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class MetricEventImpl implements TrustEvent {

	private String serviceId;

	private String property;

	private String subproperty;

	// new metricValue reading causing the event
	private String metricValue;

	private String timestamp;

	private String eventDescription;

	public MetricEventImpl() {
	}

	/**
	 * @param serviceId
	 *            atomic or composite serviceId object
	 * @param property
	 *            metric's trustworthiness property
	 * @param subproperty
	 *            subcategory of the trustworthiness property if exists
	 * @param metricValue
	 *            actual metric value
	 */
	public MetricEventImpl(String serviceId, String property,
			String subproperty, String metricValue) {

		this.serviceId = serviceId;
		this.property = property;
		this.subproperty = subproperty;
		this.metricValue = metricValue;
	}

	public MetricEventImpl(String serviceId, String property,
			String subproperty, String metricValue, String timestamp) {

		this.serviceId = serviceId;
		this.property = property;
		this.subproperty = subproperty;
		this.metricValue = metricValue;
		this.timestamp = timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.impl.rules.model.event.MetricEvent#getService
	 * ()
	 */
	public String getServiceId() {
		return serviceId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.impl.rules.model.event.MetricEvent#setService
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.String)
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.impl.rules.model.event.MetricEvent#getMetricValue
	 * ()
	 */
	public String getValue() {
		return metricValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.impl.rules.model.event.MetricEvent#setMetricValue
	 * (java.lang.String)
	 */
	public void setValue(String metricValue) {
		this.metricValue = metricValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.impl.rules.model.event.MetricEvent#setProperty
	 * (java.lang.String)
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.impl.rules.model.event.MetricEvent#getProperty
	 * ()
	 */
	public String getProperty() {
		return property;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.impl.rules.model.event.MetricEvent#getSubproperty
	 * ()
	 */
	public String getSubproperty() {
		return subproperty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.impl.rules.model.event.MetricEvent#setSubproperty
	 * (java.lang.String)
	 */
	public void setSubproperty(String subproperty) {
		this.subproperty = subproperty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.rules.model.event.TrustEvent#getTimestamp()
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.rules.model.event.TrustEvent#setTimestamp
	 * (java.lang.String)
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.rules.model.event.TrustEvent#getEventDescription
	 * ()
	 */
	public String getEventDescription() {
		//
		return eventDescription;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.rules.model.event.TrustEvent#setEventDescription
	 * (java.lang.String)
	 */
	public void setEventDescription(String eventDescription) {
		//
		this.eventDescription = eventDescription;

	}

}
