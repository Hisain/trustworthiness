/**
 *
 */
package eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@org.hibernate.annotations.Proxy(lazy=false)  
@Table(name = "SCORE")
public class Score implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6837528366375185230L;

	private String id;

	private Service service;

	private double score;

	//@Length(max = 25)
	private String property;

	//@Length(max = 50)
	private long recency;

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
	public Score() {
	}

	public Score(String id, Service service, double score, long recency,
			String property) {
		this.id = id;
		this.service = (Service)service;
		this.score = score;
		this.property = property;
		this.recency = recency;
	}

	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotNull
	@ManyToOne
	// (cascade = { CascadeType.ALL })
	@JoinColumn(name="service")
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = (Service)service;
	}

	@NotNull
	public long getRecency() {
		return recency;
	}

	public void setRecency(long recency) {
		this.recency = recency;
	}

	@NotNull
	@Column(precision = 6, scale = 2)
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@NotNull
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Transient
	public double getRecencyWt() {
		return recencyWt;
	}

	public void setRecencyWt(double scoreWt) {
		this.recencyWt = scoreWt;
	}

	@Transient
	public double getPropertyWt() {
		return propertyWt;
	}

	public void setPropertyWt(double propertyWt) {
		this.propertyWt = propertyWt;
	}

	@Transient
	public double getScoreWt() {
		return scoreWt;
	}

	public void setScoreWt(double scoreWt) {
		this.scoreWt = scoreWt;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (o == null || !(o instanceof Score)) {

			return false;
		}

		Score other = (Score) o;

		/*
		 * equivalence by id
		 */
		Score castOther = (Score) other;
		return new EqualsBuilder().append(id, castOther.getId()).append(service,
				castOther.getService()).append(score, castOther.getScore())
				.append(recency, castOther.getRecency()).append(property,
						castOther.getProperty()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(441293447, 2056268651).append(id).append(
				service).append(score).append(recency).append(property)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("service", service).append("score", score).append(
						"recency", recency).append("property", property)
				.toString();
	}
}
