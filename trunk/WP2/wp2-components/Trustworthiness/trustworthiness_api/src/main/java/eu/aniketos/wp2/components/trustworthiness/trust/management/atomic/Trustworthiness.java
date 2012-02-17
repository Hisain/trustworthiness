package eu.aniketos.wp2.components.trustworthiness.trust.management.atomic;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface Trustworthiness {

	public abstract String getServiceId();

	public abstract void setServiceId(String serviceId);

	public abstract double getScore();

	public abstract void setScore(double score);

	public abstract double getConfidence();

	public abstract void setConfidence(double confidence);

}