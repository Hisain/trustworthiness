package eu.aniketos.wp2.components.trustworthiness.impl.trust.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import eu.aniketos.wp2.components.trustworthiness.trust.dao.ScoreDao;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;

/**
 * Data Access Object for source scores
 * 
 * @author: Hisain Elshaafi
 */
public class ScoreDaoImpl  extends JpaDaoSupport implements ScoreDao {

	private static Logger logger = Logger.getLogger(ScoreDaoImpl.class);

	public ScoreDaoImpl() {
		super();
	}
	
	public void addScore(Score score) {

		try {
			getJpaTemplate().persist(score);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("score saved");
			}
		} 
		catch (Exception e) {
			logger.error("addScore: " + e.getMessage());
		}
	}

	public void updateScore(Score score) {

		try {
			getJpaTemplate().merge(score);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("score saved");
			}
		} 
		catch (Exception e) {
			logger.error("updateScore: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Score> getScoresByServiceId(final String serviceId) {

		List<Score> serviceScores = new ArrayList<Score>();
		
		List<Object> results = null;
		try {
			//TODO: needs checking
			results = (ArrayList<Object>) getJpaTemplate().find("from Score s where s.service = ?",serviceId);
			getJpaTemplate().flush();
			//em.createQuery("s from Score s, Agent a where s.agent = a and a.name='"+agentName+"'")
		} catch (Exception e) {
			logger.error("loadScores: " + e.getMessage());
		}
		
		if (results != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("results  " + results.size());
			}

			for (Object result : results) {

				Score score = (Score) result;
				serviceScores.add(score);
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

	public void deleteScore(Score score) {

		String serviceId = score.getService().getId();

		logger.info("deleting score from " + serviceId);
		
		try {
			
			score = getJpaTemplate().merge(score);
			getJpaTemplate().remove(score);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("deleted record for score " + score.getId());
			}

		} catch (Exception e) {
			logger.error("deleteScore: " + e.getMessage());
		}

	}


}
