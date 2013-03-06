package eu.aniketos.wp2.components.trustworthiness.trust.dao;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.QoSMetric;

/**
 * Data Access Object interface for scores
 *
 * @author: Hisain Elshaafi
 */
public interface QoSMetricDao {

	/**
	 * adds a rating score to database
	 * @param rating rating score
	 */
	public abstract void addMetric(QoSMetric qosMetric);
	
	/**
	 * updates an existing or creates a new score in database
	 * @param rating rating score
	 */
	public abstract void updateMetric(QoSMetric qosMetric);

	/**
	 * @param serviceId service String id
	 * @return list of existing scores
	 */
	public abstract List<QoSMetric> getMetricsByServiceId(final String serviceId);
	
	/**
	 * @param rating deletes a score from database
	 */
	public abstract void deleteMetric(QoSMetric qosMetric);


}
