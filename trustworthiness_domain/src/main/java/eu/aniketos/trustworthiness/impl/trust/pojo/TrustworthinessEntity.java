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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorType;

import org.hibernate.validator.NotNull;

/**
 * 
 * @author Hisain Elshaafi
 * 
 */
@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "TRUSTWORTHINESS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("general")
public class TrustworthinessEntity extends
		eu.aniketos.trustworthiness.ext.messaging.Trustworthiness implements
		Serializable {

	private static final long serialVersionUID = 7661205089410810957L;

	private double qosMovingWt = 0;
	private double calcTime = 0;
	private double qosDeviation = 0;
	private double reputationMovingWt = 0;
	private double reputationDeviation = 0;
	private double lastAlertScore;
	/**
	 * 
	 */
	private double averageComponentTrustworthinessScore;

	/**
	 * 
	 */
	private double lowestComponentTrustworthinessScore;

	/**
	 * @return
	 */
	@Id
	@Column(name = "id")
	public String getId() {
		return serviceId;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.serviceId = id;
	}

	/**
	 * @return
	 */
	@Column(name = "trustworthiness_score")
	@NotNull
	public double getTrustworthinessScore() {
		return trustworthinessScore;
	}

	/**
	 * @param trustworthinessScore
	 */
	public void setTrustworthinessScore(double trustworthinessScore) {
		this.trustworthinessScore = trustworthinessScore;
	}

	/**
	 * @return
	 */
	@Column(name = "qos_score", precision = 4, scale = 3)
	@NotNull
	public double getQosScore() {
		return qosScore;
	}

	/**
	 * @param qosScore
	 */
	public void setQosScore(double qosScore) {
		this.qosScore = qosScore;
	}

	/**
	 * @return
	 */
	@Column(name = "qos_confidence", precision = 4, scale = 3)
	@NotNull
	public double getQosConfidence() {
		return qosConfidence;
	}

	/**
	 * @param qosConfidence
	 */
	public void setQosConfidence(double confidence) {
		this.qosConfidence = confidence;
	}

	/**
	 * @return
	 */
	@Column(name = "security_score", precision = 4, scale = 3)
	@NotNull
	public double getSecurityScore() {
		return securityScore;
	}

	public void setSecurityScore(double securityScore) {
		this.securityScore = securityScore;
	}

	/**
	 * @return
	 */
	@Column(name = "qos_moving_wt")
	@NotNull
	public double getQosMovingWt() {
		return qosMovingWt;
	}

	/**
	 * @param qosMovingWt
	 */
	public void setQosMovingWt(double movingWt) {
		this.qosMovingWt = movingWt;
	}

	/**
	 * @return
	 */
	@Column(name = "calc_time")
	@NotNull
	public double getCalcTime() {
		return calcTime;
	}

	/**
	 * @param calcTime
	 */
	public void setCalcTime(double calcTime) {
		this.calcTime = calcTime;
	}

	/**
	 * @return
	 */
	@Column(name = "qos_deviation")
	@NotNull
	public double getQosDeviation() {
		return qosDeviation;
	}

	/**
	 * @param qosDeviation
	 */
	public void setQosDeviation(double deviation) {
		this.qosDeviation = deviation;
	}

	/**
	 * @return
	 */
	@Column(name = "rep_score", precision = 4, scale = 3)
	@NotNull
	public double getReputationScore() {
		return reputationScore;
	}

	/**
	 * @param reputationScore
	 */
	public void setReputationScore(double reputationScore) {
		this.reputationScore = reputationScore;
	}

	/**
	 * @return
	 */
	@Column(name = "rep_confidence", precision = 4, scale = 3)
	@NotNull
	public double getReputationConfidence() {
		return reputationConfidence;
	}

	/**
	 * @param reputationConfidence
	 */
	public void setReputationConfidence(double reputationConfidence) {
		this.reputationConfidence = reputationConfidence;
	}

	/**
	 * @return
	 */
	@Column(name = "rep_moving_wt")
	@NotNull
	public double getReputationMovingWt() {
		return reputationMovingWt;
	}

	/**
	 * @param reputationMovingWt
	 */
	public void setReputationMovingWt(double reputationMovingWt) {
		this.reputationMovingWt = reputationMovingWt;
	}

	/**
	 * @return
	 */
	@Column(name = "rep_deviation")
	@NotNull
	public double getReputationDeviation() {
		return reputationDeviation;
	}

	/**
	 * @param reputationDeviation
	 */
	public void setReputationDeviation(double reputationDeviation) {
		this.reputationDeviation = reputationDeviation;
	}

	/**
	 * @return
	 */
	@Column(name = "avg_comp_trust", nullable = true, precision = 4, scale = 3)
	public double getAverageComponentTrustworthinessScore() {

		return averageComponentTrustworthinessScore;
	}

	/**
	 * @param trustworthinessScore
	 */
	public void setAverageComponentTrustworthinessScore(
			double trustworthinessScore) {
		this.averageComponentTrustworthinessScore = trustworthinessScore;

	}

	/**
	 * @return
	 */
	@Column(name = "lo_comp_trust", nullable = true, precision = 4, scale = 3)
	public double getLowestComponentTrustworthinessScore() {
		return lowestComponentTrustworthinessScore;
	}

	/**
	 * @param trustworthinessScore
	 */
	public void setLowestComponentTrustworthinessScore(
			double trustworthinessScore) {
		this.lowestComponentTrustworthinessScore = trustworthinessScore;
	}

	/**
	 * @return
	 */
	@Column(name = "alert_score", nullable = true, precision = 4, scale = 3)
	@NotNull
	public double getLastAlertScore() {
		return lastAlertScore;
	}

	/**
	 * @param lastAlertScore
	 */
	public void setLastAlertScore(double lastAlertScore) {
		this.lastAlertScore = lastAlertScore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrustworthinessEntity other = (TrustworthinessEntity) obj;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Service [id=" + serviceId + " -> trustworthiness="
				+ trustworthinessScore + "]";
	}

}
