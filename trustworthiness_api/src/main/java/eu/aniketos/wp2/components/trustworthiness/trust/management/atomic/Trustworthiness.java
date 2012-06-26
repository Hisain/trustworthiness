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
	public abstract double getScore();

	/**
	 * @param score trustworthiness score
	 */
	public abstract void setScore(double score);

	/**
	 * @return trustworthiness confidence
	 */
	public abstract double getConfidence();

	/**
	 * @param confidence trustworthiness confidence
	 */
	public abstract void setConfidence(double confidence);

}