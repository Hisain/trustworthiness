package eu.aniketos.wp2.components.trustworthiness.impl.trust.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import eu.aniketos.wp2.components.trustworthiness.trust.dao.ThreatLevelDao;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.ThreatLevel;

/**
 * Data Access Object for source threatLevels
 * 
 * @author: Hisain Elshaafi
 */
public class ThreatLevelDaoImpl  extends JpaDaoSupport implements ThreatLevelDao {

	private static Logger logger = Logger.getLogger(ThreatLevelDaoImpl.class);

	/**
	 * 
	 */
	public ThreatLevelDaoImpl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.ThreatLevelDao#addThreatLevel(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.ThreatLevel)
	 */
	public void addThreatLevel(ThreatLevel threatLevel) {

		try {
			getJpaTemplate().persist(threatLevel);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("threatLevel saved");
			}
		} 
		catch (Exception e) {
			logger.error("addThreatLevel: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.ThreatLevelDao#updateThreatLevel(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.ThreatLevel)
	 */
	public void updateThreatLevel(ThreatLevel threatLevel) {

		try {
			getJpaTemplate().merge(threatLevel);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("threatLevel saved");
			}
		} 
		catch (Exception e) {
			logger.error("updateThreatLevel: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.ThreatLevelDao#getThreatLevelsByServiceId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<ThreatLevel> getThreatLevelsByServiceId(final String serviceId) {

		List<ThreatLevel> serviceThreatLevels = new ArrayList<ThreatLevel>();
		
		List<Object> results = null;
		
		try {
			//TODO: needs checking
			results = (ArrayList<Object>) getJpaTemplate().find("from ThreatLevel s where s.service = ?",serviceId);
			getJpaTemplate().flush();
			//em.createQuery("s from ThreatLevel s, Agent a where s.agent = a and a.name='"+agentName+"'")
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
		} 
		else {
			logger.warn("query returned null");
		}
		
		if (serviceThreatLevels != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("loaded threatLevels " + serviceThreatLevels.size());
				logger.debug("found threatLevels");
			}
		} 
				
		return serviceThreatLevels;
	}
	
	@SuppressWarnings("unchecked")
	public ThreatLevel getThreatLevel(final String serviceId, final String threatLevel) {

		ThreatLevel serviceThreatLevel = null;
		
		List<Object> results = null;
		try {
			//TODO: needs checking
			results = (ArrayList<Object>) getJpaTemplate()
					.find("from ThreatLevel s where s.service = ? and s.property = ?",serviceId,threatLevel);
			getJpaTemplate().flush();
			//em.createQuery("s from ThreatLevel s, Agent a where s.agent = a and a.name='"+agentName+"'")
		} catch (Exception e) {
			logger.error("loadThreatLevels: " + e.getMessage());
		}
		
		if (results != null) {
			if (results.size()>1){
				logger.warn("more than one score of security property may exist");
			}
			if (logger.isDebugEnabled()) {
				logger.debug("results  " + results.size());
			}

			for (Object result : results) {

				serviceThreatLevel = (ThreatLevel) result;
				break;
			}
		} 
		else {
			logger.warn("query returned null");
		}
		
		if (serviceThreatLevel != null) {
			if (logger.isDebugEnabled()) {
				
				logger.debug("found threatLevels");
			}
		} 
				
		return serviceThreatLevel;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.ThreatLevelDao#deleteThreatLevel(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.ThreatLevel)
	 */
	public void deleteThreatLevel(ThreatLevel threatLevel) {

		String serviceId = threatLevel.getService().getId();

		logger.info("deleting threatLevel from " + serviceId);
		
		try {
			
			threatLevel = getJpaTemplate().merge(threatLevel);
			getJpaTemplate().remove(threatLevel);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("deleted record for threatLevel " + threatLevel.getId());
			}

		} catch (Exception e) {
			logger.error("deleteThreatLevel: " + e.getMessage());
		}

	}

	
}
