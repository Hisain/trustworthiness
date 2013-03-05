package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.atomic;

import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class ServiceTrustworthiness implements Trustworthiness {

	private String serviceId;
	private double trustworthinessScore;
	private double qosConfidence;
	private double qosScore;
	private double securityScore;

	/**
	 * 
	 */
	public ServiceTrustworthiness() {}

	/**
	 * @param serviceId
	 * @param trustworthinessScore
	 * @param qosConfidence
	 */
	public ServiceTrustworthiness(String serviceId, double trustworthinessScore, double qosScore, double qosConfidence, double securityScore) {
		this.serviceId = serviceId;
		this.trustworthinessScore = trustworthinessScore;
		this.qosScore = qosScore;
		this.qosConfidence = qosConfidence;
		this.securityScore = securityScore;
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
	public double getTrustworthinessScore() {
		return trustworthinessScore;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness#setScore(double)
	 */
	public void setTrustworthinessScore(double score) {
		this.trustworthinessScore = score;
	}


	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness#getQosScore()
	 */
	public double getQosScore() {
		return qosScore;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness#setQScore(double)
	 */
	public void setQosScore(double qosScore) {
		this.qosScore = qosScore;
		
	}
	
	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness#getConfidence()
	 */
	public double getQosConfidence() {
		return qosConfidence;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness#setConfidence(double)
	 */
	public void setQosConfidence(double confidence) {
		this.qosConfidence = confidence;
	}

	public double getSecurityScore() {
		return securityScore;
	}

	public void setSecurityScore(double securityScore) {
		this.securityScore = securityScore;
		
	}


}
