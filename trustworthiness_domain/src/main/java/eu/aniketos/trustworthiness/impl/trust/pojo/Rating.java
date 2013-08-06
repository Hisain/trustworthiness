/**
 *
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
@org.hibernate.annotations.Proxy(lazy=false)  
@Table(name = "RATING")
public class Rating implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6837528366375185230L;

	private String id;

	private Service service;
	
	private String consumerId;
	
	private String transactionId;

	private double score;

	//@Length(max = 25)
	private String property;

	//@Length(max = 50)
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
	public Rating() {
	}

	/**
	 * @param id
	 * @param service
	 * @param score
	 * @param recency
	 * @param property
	 */
	public Rating(String id, Service service, double score, long recency,
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
	 * 
	 * TODO: test after change from service to service
	 */
	@NotNull
	@ManyToOne
	// (cascade = { CascadeType.ALL })
	@JoinColumn(name="service_id")
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
	@Column(name="transaction_id",unique=true)
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 */
	public void setTransactionId(String transaction) {
		this.transactionId = transaction;
	}

	/**
	 * @return
	 */
	@NotNull
	@Column(name="consumer_id")
	public String getConsumerId() {
		return consumerId;
	}

	/**
	 * @param consumerId
	 */
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
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
	@Column(name="event_description")
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
		if (o == null || !(o instanceof Rating)) {

			return false;
		}

		Rating other = (Rating) o;

		/*
		 * equivalence by id
		 */
		Rating castOther = (Rating) other;
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
