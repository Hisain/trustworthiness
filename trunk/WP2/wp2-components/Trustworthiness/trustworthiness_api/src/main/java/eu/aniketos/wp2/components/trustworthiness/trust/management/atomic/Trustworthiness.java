package eu.aniketos.wp2.components.trustworthiness.trust.management.atomic;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface Trustworthiness {

	/**
	 * @return String service id
	 */
	public abstract String getServiceId();

	/**
	 * @param serviceId String service id
	 */
	public abstract void setServiceId(String serviceId);

	/**
	 * @return trustworthiness score
	 */
	public abstract double getTrustworthinessScore();

	/**
	 * @param score trustworthiness score
	 */
	public abstract void setTrustworthinessScore(double securityScore);
	
	/**
	 * @return trustworthiness score
	 */
	public abstract double getSecurityScore();

	/**
	 * @param score trustworthiness score
	 */
	public abstract void setSecurityScore(double securityScore);
	
	/**
	 * @return trustworthiness score
	 */
	public abstract double getQosScore();

	/**
	 * @param score trustworthiness score
	 */
	public abstract void setQosScore(double score);

	/**
	 * @return trustworthiness confidence
	 */
	public abstract double getQosConfidence();

	/**
	 * @param confidence trustworthiness confidence
	 */
	public abstract void setQosConfidence(double confidence);

}