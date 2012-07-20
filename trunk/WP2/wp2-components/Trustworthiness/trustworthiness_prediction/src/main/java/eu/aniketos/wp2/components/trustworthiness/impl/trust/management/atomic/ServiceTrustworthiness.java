package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.atomic;

import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class ServiceTrustworthiness implements Trustworthiness {

	private String serviceId;
	private double score;
	private double confidence;

	/**
	 * 
	 */
	public ServiceTrustworthiness() {}

	/**
	 * @param serviceId
	 * @param score
	 * @param confidence
	 */
	public ServiceTrustworthiness(String serviceId, double score, double confidence) {
		this.serviceId = serviceId;
		this.score = score;
		this.confidence = confidence;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness#getServiceId()
	 */
	public String getServiceId() {
		return serviceId;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness#setServiceId(java.lang.String)
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness#getScore()
	 */
	public double getScore() {
		return score;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness#setScore(double)
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness#getConfidence()
	 */
	public double getConfidence() {
		return confidence;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness#setConfidence(double)
	 */
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

}
