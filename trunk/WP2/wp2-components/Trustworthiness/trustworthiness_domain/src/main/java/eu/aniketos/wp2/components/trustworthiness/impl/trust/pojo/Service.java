package eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
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
	private double trustScore=0;
	private double confidence=0;
	private double movingWt=0;
	private double calcTime=0;
	private double deviation=0;
	//private boolean composite = false;

	public Service(String serviceId) {
		this.id = serviceId;
	}

	public Service() {
	}

	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column (name="trustscore")
	@NotNull
	public double getTrustScore() {
		return trustScore;
	}

	public void setTrustScore(double trustScore) {
		this.trustScore = trustScore;
	}

	@Column (name="confidence")
	@NotNull
	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	@Column (name="movingwt")
	@NotNull
	public double getMovingWt() {
		return movingWt;
	}

	public void setMovingWt(double movingWt) {
		this.movingWt = movingWt;
	}

	@Column (name="calctime")
	@NotNull
	public double getCalcTime() {
		return calcTime;
	}

	public void setCalcTime(double calcTime) {
		this.calcTime = calcTime;
	}

	@Column (name="deviation")
	@NotNull
	public double getDeviation() {
		return deviation;
	}

	public void setDeviation(double deviation) {
		this.deviation = deviation;
	}

	/*@Column(name="iscomposite")
	public boolean isComposite() {
		return composite;
	}

	public void setComposite(boolean composite) {
		this.composite = composite;
	}*/

	

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
