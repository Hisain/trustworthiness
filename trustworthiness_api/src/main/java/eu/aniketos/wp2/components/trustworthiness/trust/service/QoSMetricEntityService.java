package eu.aniketos.wp2.components.trustworthiness.trust.service;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.QoSMetric;

/**
 * data access service for scores
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface QoSMetricEntityService {

	/**
	 * @param metric
	 *            QoSMetric object to add
	 */
	public abstract void addMetric(QoSMetric metric);

	/**
	 * @param metric
	 *            QoSMetric object to update
	 */
	public abstract void updateMetric(QoSMetric metric);

	/**
	 * @param serviceId
	 *            String service id
	 * @return list of existing scores for service
	 */
	public abstract List<QoSMetric> getMetricsByServiceId(final String serviceId);

	/**
	 * @param metric
	 *            QoSMetric object to delete
	 */
	public abstract void deleteMetric(QoSMetric metric);

	
}
