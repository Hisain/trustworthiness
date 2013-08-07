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

import eu.aniketos.trustworthiness.trust.dao.ThreatLevelDao;
import eu.aniketos.trustworthiness.impl.trust.pojo.ThreatLevel;

/**
 * Data Access Object for source threatLevels
 * 
 * @author: Hisain Elshaafi
 */
public class ThreatLevelDaoImpl extends JpaDaoSupport implements ThreatLevelDao {

	private static Logger logger = Logger.getLogger(ThreatLevelDaoImpl.class);

	/**
	 * 
	 */
	public ThreatLevelDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.trust.dao.ThreatLevelDao#addThreatLevel(eu
	 * .aniketos.trustworthiness.impl.trust.pojo.ThreatLevel)
	 */
	public void addThreatLevel(ThreatLevel threatLevel) {

		try {
			getJpaTemplate().persist(threatLevel);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("threatLevel saved");
			}
		} catch (Exception e) {
			logger.error("addThreatLevel: " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.trust.dao.ThreatLevelDao#updateThreatLevel
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.ThreatLevel)
	 */
	public void updateThreatLevel(ThreatLevel threatLevel) {

		try {
			getJpaTemplate().merge(threatLevel);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("threatLevel saved");
			}
		} catch (Exception e) {
			logger.error("updateThreatLevel: " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.trust.dao.ThreatLevelDao#
	 * getThreatLevelsByServiceId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<ThreatLevel> getThreatLevelsByServiceId(final String serviceId) {

		List<ThreatLevel> serviceThreatLevels = new ArrayList<ThreatLevel>();

		List<Object> results = null;

		try {
			// TODO: needs checking
			results = (ArrayList<Object>) getJpaTemplate().find(
					"from ThreatLevel s where s.service = ?", serviceId);
			getJpaTemplate().flush();
			// em.createQuery("s from ThreatLevel s, Agent a where s.agent = a and a.name='"+agentName+"'")
		} catch (Exception e) {
			logger.error("loadThreatLevels: " + e.getMessage());
		}

		if (results != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("results  " + results.size());
			}

			for (Object result : results) {

				ThreatLevel threatLevel = (ThreatLevel) result;
				serviceThreatLevels.add(threatLevel);
			}
		} else {
			logger.warn("query returned null");
		}

		if (serviceThreatLevels != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("loaded threatLevels "
						+ serviceThreatLevels.size());
				logger.debug("found threatLevels");
			}
		}

		return serviceThreatLevels;
	}

	@SuppressWarnings("unchecked")
	public ThreatLevel getThreatLevel(final String serviceId,
			final String threatLevel) {

		ThreatLevel serviceThreatLevel = null;

		List<Object> results = null;
		try {
			// TODO: needs checking
			results = (ArrayList<Object>) getJpaTemplate()
					.find("from ThreatLevel s where s.service = ? and s.property = ?",
							serviceId, threatLevel);
			getJpaTemplate().flush();
			// em.createQuery("s from ThreatLevel s, Agent a where s.agent = a and a.name='"+agentName+"'")
		} catch (Exception e) {
			logger.error("loadThreatLevels: " + e.getMessage());
		}

		if (results != null) {
			if (results.size() > 1) {
				logger.warn("more than one score of security property may exist");
			}
			if (logger.isDebugEnabled()) {
				logger.debug("results  " + results.size());
			}

			for (Object result : results) {

				serviceThreatLevel = (ThreatLevel) result;
				break;
			}
		} else {
			logger.warn("query returned null");
		}

		if (serviceThreatLevel != null) {
			if (logger.isDebugEnabled()) {

				logger.debug("found threatLevels");
			}
		}

		return serviceThreatLevel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.trust.dao.ThreatLevelDao#deleteThreatLevel
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.ThreatLevel)
	 */
	public void deleteThreatLevel(ThreatLevel threatLevel) {

		String serviceId = threatLevel.getService().getId();

		logger.info("deleting threatLevel from " + serviceId);

		try {

			threatLevel = getJpaTemplate().merge(threatLevel);
			getJpaTemplate().remove(threatLevel);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("deleted record for threatLevel "
						+ threatLevel.getId());
			}

		} catch (Exception e) {
			logger.error("deleteThreatLevel: " + e.getMessage());
		}

	}

}
