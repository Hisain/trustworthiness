package eu.aniketos.wp2.components.trustworthiness.impl.trust.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import eu.aniketos.wp2.components.trustworthiness.trust.dao.ScoreDao;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating;

/**
 * Data Access Object for source scores
 * 
 * @author: Hisain Elshaafi
 */
public class ScoreDaoImpl  extends JpaDaoSupport implements ScoreDao {

	private static Logger logger = Logger.getLogger(ScoreDaoImpl.class);

	/**
	 * 
	 */
	public ScoreDaoImpl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.ScoreDao#addScore(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void addScore(Rating rating) {

		try {
			getJpaTemplate().persist(rating);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("score saved");
			}
		} 
		catch (Exception e) {
			logger.error("addScore: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.ScoreDao#updateScore(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void updateScore(Rating rating) {

		try {
			getJpaTemplate().merge(rating);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("score saved");
			}
		} 
		catch (Exception e) {
			logger.error("updateScore: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.ScoreDao#getScoresByServiceId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Rating> getScoresByServiceId(final String serviceId) {

		List<Rating> serviceScores = new ArrayList<Rating>();
		
		List<Object> results = null;
		try {
			//TODO: needs checking
			results = (ArrayList<Object>) getJpaTemplate().find("from Rating s where s.service = ?",serviceId);
			getJpaTemplate().flush();
			//em.createQuery("s from Rating s, Agent a where s.agent = a and a.name='"+agentName+"'")
		} catch (Exception e) {
			logger.error("loadScores: " + e.getMessage());
		}
		
		if (results != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("results  " + results.size());
			}

			for (Object result : results) {

				Rating rating = (Rating) result;
				serviceScores.add(rating);
			}
		} 
		else {
			logger.warn("query returned null");
		}
		
		if (serviceScores != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("loaded scores " + serviceScores.size());
				logger.debug("found scores");
			}
		} 
				
		return serviceScores;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.ScoreDao#deleteScore(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void deleteScore(Rating rating) {

		String serviceId = rating.getService().getId();

		logger.info("deleting score from " + serviceId);
		
		try {
			
			rating = getJpaTemplate().merge(rating);
			getJpaTemplate().remove(rating);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("deleted record for score " + rating.getId());
			}

		} catch (Exception e) {
			logger.error("deleteScore: " + e.getMessage());
		}

	}

	


}
