/**
 *
 */
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
package eu.aniketos.trustworthiness.impl.trust.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

//import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "QOS")
public class QoSMetric implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6837528366375185230L;

	private String id;

	private Service service;

	private double score;

	// @Length(max = 25)
	private String property;

	// @Length(max = 50)
	private long recency;

	// description of triggering event
	private String eventDescription;

	/*
	 * recencyWt and propertyWt is weight of score regardless of other scores
	 */
	private double recencyWt;

	private double propertyWt;

	/*
	 * scoreWt is weight of score calculated in relation to all existing scores
	 */
	private double scoreWt;

	/**
	 *
	 */
	public QoSMetric() {
	}

	/**
	 * @param id
	 * @param service
	 * @param score
	 * @param recency
	 * @param property
	 */
	public QoSMetric(String id, Service service, double score, long recency,
			String property, String eventDescription) {
		this.id = id;
		this.service = service;
		this.score = score;
		this.property = property;
		this.recency = recency;
		this.eventDescription = eventDescription;
	}

	/**
	 * @return
	 */
	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 * 
	 *         TODO: test after change from service to service
	 */
	@NotNull
	@ManyToOne
	// (cascade = { CascadeType.ALL })
	@JoinColumn(name = "service_id")
	public Service getService() {
		return service;
	}

	/**
	 * @param service
	 */
	public void setService(Service service) {
		this.service = service;
	}

	/**
	 * @return
	 */
	@NotNull
	public long getRecency() {
		return recency;
	}

	/**
	 * @param recency
	 */
	public void setRecency(long recency) {
		this.recency = recency;
	}

	/**
	 * @return
	 */
	@NotNull
	@Column(precision = 7, scale = 6)
	public double getScore() {
		return score;
	}

	/**
	 * @param score
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @return
	 */
	@NotNull
	public String getProperty() {
		return property;
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
	@Column(name = "event_description")
	public String getEventDescription() {
		return eventDescription;
	}

	/**
	 * @param eventDescription
	 */
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	/**
	 * @return
	 */
	@Transient
	public double getRecencyWt() {
		return recencyWt;
	}

	/**
	 * @param scoreWt
	 */
	public void setRecencyWt(double scoreWt) {
		this.recencyWt = scoreWt;
	}

	/**
	 * @return
	 */
	@Transient
	public double getPropertyWt() {
		return propertyWt;
	}

	/**
	 * @param propertyWt
	 */
	public void setPropertyWt(double propertyWt) {
		this.propertyWt = propertyWt;
	}

	/**
	 * @return
	 */
	@Transient
	public double getScoreWt() {
		return scoreWt;
	}

	/**
	 * @param scoreWt
	 */
	public void setScoreWt(double scoreWt) {
		this.scoreWt = scoreWt;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (o == null || !(o instanceof QoSMetric)) {

			return false;
		}

		QoSMetric other = (QoSMetric) o;

		/*
		 * equivalence by id
		 */
		QoSMetric castOther = (QoSMetric) other;
		return new EqualsBuilder().append(id, castOther.getId())
				.append(service, castOther.getService())
				.append(score, castOther.getScore())
				.append(recency, castOther.getRecency())
				.append(property, castOther.getProperty()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(441293447, 2056268651).append(id)
				.append(service).append(score).append(recency).append(property)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("service", service).append("score", score)
				.append("recency", recency).append("property", property)
				.toString();
	}
}
