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

	public ServiceTrustworthiness() {}

	public ServiceTrustworthiness(String serviceId, double score, double confidence) {
		this.serviceId = serviceId;
		this.score = score;
		this.confidence = confidence;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

}
