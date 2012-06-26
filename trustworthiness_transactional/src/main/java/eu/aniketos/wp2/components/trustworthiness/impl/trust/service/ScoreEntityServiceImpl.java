package eu.aniketos.wp2.components.trustworthiness.impl.trust.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ScoreEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.dao.ScoreDao;

/**
 *  data access service for scores
 *  
 * @author Hisain Elshaafi (TSSG)
 *
 */
@Transactional(propagation = Propagation.REQUIRES_NEW,noRollbackFor={Exception.class})
public class ScoreEntityServiceImpl implements ScoreEntityService {
	
	ScoreDao scoreDao;
	
	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ScoreEntityService#addScore(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score)
	 */
	public void addScore(Score score) {
		scoreDao.addScore(score);

	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ScoreEntityService#updateScore(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score)
	 */
	public void updateScore(Score score) {
		scoreDao.updateScore(score);

	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ScoreEntityService#getScoresByServiceId(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public List<Score> getScoresByServiceId(String source) {
		return scoreDao.getScoresByServiceId(source);
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ScoreEntityService#deleteScore(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score)
	 */
	public void deleteScore(Score score) {
		scoreDao.deleteScore(score);

	}

	/**
	 * @return score DAO object
	 */
	public ScoreDao getScoreDao() {
		return scoreDao;
	}

	/**
	 * @param scoreDao score DAO object
	 */
	public void setScoreDao(ScoreDao scoreDao) {
		this.scoreDao = scoreDao;
	}

	
}
