package eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo;

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
@org.hibernate.annotations.Proxy(lazy=false)  
@Table(name = "SERVICE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("general")
public class Service implements Serializable {
	
	private static final long serialVersionUID = 7661205089410810957L;
	private String id = null;
	private double trustworthinessScore=0;
	private double qosScore=0;
	private double qosConfidence=0;
	private double qosMovingWt=0;
	private double calcTime=0;
	private double qosDeviation=0;
	private double reputationScore=0;
	private double reputationConfidence=0;
	private double reputationMovingWt=0;
	private double reputationDeviation=0;
	private double securityScore=0;
	//private boolean composite = false;

	/**
	 * @param serviceId
	 */
	public Service(String serviceId) {
		this.id = serviceId;
	}

	/**
	 * 
	 */
	public Service() {
	}

	/**
	 * @return
	 */
	@Id
	@Column(name="id")
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
	 */
	@Column (name="trustworthiness_score")
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
	@Column (name="qos_score")
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
	@Column (name="qos_confidence")
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
	@Column (name="security_score")
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
	@Column (name="qos_moving_wt")
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
	@Column (name="calc_time")
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
	@Column (name="qos_deviation")
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
	@Column (name="rep_score")
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
	@Column (name="rep_confidence")
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
	@Column (name="rep_moving_wt")
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
	@Column (name="rep_deviation")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Service other = (Service) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Service [id=" + id + "]";
	}

}
