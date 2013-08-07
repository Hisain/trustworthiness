/**
 * Copyright (c) 2013, Waterford Institute of Technology
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *    - Neither the name of Waterford Institute of Technology nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *      
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL WATERFORD INSTITUTE OF TECHNOLOGY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
	 * @see eu.aniketos.trustworthiness.trust.dao.RatingDao#addRating
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
	 * @see eu.aniketos.trustworthiness.trust.dao.RatingDao#updateRating
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
	 * @see eu.aniketos.trustworthiness.trust.dao.RatingDao#deleteRating
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
