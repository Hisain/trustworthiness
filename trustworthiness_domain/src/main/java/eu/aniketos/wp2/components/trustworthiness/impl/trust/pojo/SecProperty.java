/**
 *
 */
package eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "SEC_PROPERTY")
public class SecProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 935859863872557458L;

	private String id;

	private Service service;

	private double score;

	// @Length(max = 25)
	private String property;

	// @Length(max = 50)
	private long recency;

	private String updateDescription;

	/**
	 *
	 */
	public SecProperty() {
	}

	/**
	 * @param id
	 * @param serviceId
	 * @param score
	 * @param recency
	 * @param property
	 */
	public SecProperty(String id, Service service, double score, long recency,
			String property) {
		this.id = id;
		this.service = service;
		this.score = score;
		this.property = property;
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
	 */
	@NotNull
	@ManyToOne
	// (cascade = { CascadeType.ALL })
	@JoinColumn(name = "service_id")
	public Service getService() {
		return service;
	}

	/**
	 * @param serviceId
	 */
	public void setService(Service service) {
		this.service = service;
	}

	/**
	 * @return
	 */
	@NotNull
	@Column(precision = 6, scale = 2)
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
	 * @return updateDescription description of latest security property update
	 */
	@Column(name="update_description")
	public String getUpdateDescription() {
		return updateDescription;
	}

	/**
	 * @param updateDescription
	 *            description of latest security property update
	 */
	public void setUpdateDescription(String updateDescription) {
		this.updateDescription = updateDescription;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (o == null || !(o instanceof SecProperty)) {

			return false;
		}

		SecProperty other = (SecProperty) o;

		/*
		 * equivalence by id
		 */
		SecProperty castOther = (SecProperty) other;
		return new EqualsBuilder().append(id, castOther.getId())
				.append(service, castOther.getService())
				.append(property, castOther.getProperty()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(441293447, 2056268651).append(id)
				.append(service).append(score).append(property).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("serviceId", service).append("score", score)
				.append("property", property).toString();
	}
}
