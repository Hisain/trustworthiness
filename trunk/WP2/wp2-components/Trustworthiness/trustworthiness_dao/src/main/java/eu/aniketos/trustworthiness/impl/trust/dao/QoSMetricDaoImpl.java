package eu.aniketos.trustworthiness.impl.trust.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import eu.aniketos.trustworthiness.trust.dao.QoSMetricDao;
import eu.aniketos.trustworthiness.impl.trust.pojo.QoSMetric;

/**
 * Data Access Object for source scores
 * 
 * @author: Hisain Elshaafi
 */
public class QoSMetricDaoImpl extends JpaDaoSupport implements QoSMetricDao {

	private static Logger logger = Logger.getLogger(QoSMetricDaoImpl.class);

	/**
	 * 
	 */
	public QoSMetricDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.trust.dao.RatingDao#addRating
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void addMetric(QoSMetric metric) {

		try {
			getJpaTemplate().persist(metric);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("score saved");
			}
		} catch (Exception e) {
			logger.error("addRating: " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.trust.dao.RatingDao#updateRating
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void updateMetric(QoSMetric metric) {

		try {
			getJpaTemplate().merge(metric);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("score saved");
			}
		} catch (Exception e) {
			logger.error("updateRating: " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.trust.dao.RatingDao#
	 * getRatingsByServiceId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<QoSMetric> getMetricsByServiceId(final String serviceId) {

		List<QoSMetric> serviceRatings = new ArrayList<QoSMetric>();

		List<Object> results = null;
		try {
			// TODO: needs checking
			results = (ArrayList<Object>) getJpaTemplate().find(
					"from QoSMetric q where q.service = ?", serviceId);
			getJpaTemplate().flush();
			// em.createQuery("s from Rating s, Agent a where s.agent = a and a.name='"+agentName+"'")
		} catch (Exception e) {
			logger.error("loadRatings: " + e.getMessage());
		}

		if (results != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("results  " + results.size());
			}

			for (Object result : results) {

				QoSMetric metric = (QoSMetric) result;
				serviceRatings.add(metric);
			}
		} else {
			logger.warn("query returned null");
		}

		if (serviceRatings != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("loaded scores " + serviceRatings.size());
				logger.debug("found scores");
			}
		}

		return serviceRatings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.trust.dao.RatingDao#deleteRating
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void deleteMetric(QoSMetric metric) {

		String serviceId = metric.getService().getId();

		logger.info("deleting score from " + serviceId);

		try {

			metric = getJpaTemplate().merge(metric);
			getJpaTemplate().remove(metric);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("deleted record for score " + metric.getId());
			}

		} catch (Exception e) {
			logger.error("deleteRating: " + e.getMessage());
		}

	}

}
