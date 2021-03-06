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
package eu.aniketos.trustworthiness.impl.rules.model.event;

import eu.aniketos.trustworthiness.rules.model.event.RuleAlertEvent;

/**
 * alert events class
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class RuleAlertEventImpl implements RuleAlertEvent {

	private String serviceId;

	private String property;

	private String subproperty;

	// new alertValue reading causing the event
	private String value;

	// proposed value in contractValue
	private String contractValue;

	private String type;

	private String limit;

	private String timestamp;

	private String eventDescription;

	private String eventId;
	
	private String importance;

	/**
	 * @param serviceId
	 *            atomic or composite serviceId object
	 * @param property
	 *            metric's trustworthiness property
	 * @param subproperty
	 *            subcategory of the trustworthiness property if exists
	 * @param contractValue
	 *            required value
	 * @param type
	 *            type of alert i.e. percentage, real number, etc.
	 * @param limit
	 *            the type of limit requirement i.e. max, min, etc
	 * @param value
	 *            actual alert value
	 */
	public RuleAlertEventImpl(String serviceId, String property,
			String subproperty, String contractValue, String type,
			String limit, String value, String timestamp) {

		this.serviceId = serviceId;
		this.property = property;
		this.subproperty = subproperty;
		this.value = value;
		this.contractValue = contractValue;
		this.type = type;
		this.limit = limit;
		this.timestamp = timestamp;
	}

	public RuleAlertEventImpl(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param alertValue
	 */
	public void setValue(String alertValue) {
		this.value = alertValue;
	}

	/**
	 * @return
	 */
	public String getContractValue() {
		return contractValue;
	}

	/**
	 * @param contractValue
	 */
	public void setContractValue(String contractValue) {
		this.contractValue = contractValue;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param limit
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}

	/**
	 * @return
	 */
	public String getLimit() {
		return limit;
	}

	/**
	 * @param property
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @return
	 */
	public String getSubproperty() {
		return subproperty;
	}

	/**
	 * @param subproperty
	 */
	public void setSubproperty(String subproperty) {
		this.subproperty = subproperty;
	}

	/**
	 * @return
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.rules.model.event.TrustEvent#getEventDescription()
	 */
	public String getEventDescription() {
		// 
		return eventDescription;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.rules.model.event.TrustEvent#setEventDescription(java.lang.String)
	 */
	public void setEventDescription(String eventDescription) {
		// 
		this.eventDescription = eventDescription;
		
	}
	
	/**
	 * @return
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return
	 */
	public String getImportance() {
		return importance;
	}

	/**
	 * @param importance
	 */
	public void setImportance(String importance) {
		this.importance = importance;
	}

}
