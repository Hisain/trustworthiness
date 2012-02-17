package eu.aniketos.wp2.components.trustworthiness.impl.trust.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ScoreEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.dao.ScoreDao;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
@Transactional(propagation = Propagation.REQUIRES_NEW,noRollbackFor={Exception.class})
public class ScoreEntityServiceImpl implements ScoreEntityService {
	
	ScoreDao scoreDao;
	
	public void addScore(Score score) {
		scoreDao.addScore(score);

	}

	public void updateScore(Score score) {
		scoreDao.updateScore(score);

	}

	@Transactional(readOnly=true)
	public List<Score> getScoresByServiceId(String source) {
		return scoreDao.getScoresByServiceId(source);
	}

	public void deleteScore(Score score) {
		scoreDao.deleteScore(score);

	}

	public ScoreDao getScoreDao() {
		return scoreDao;
	}

	public void setScoreDao(ScoreDao scoreDao) {
		this.scoreDao = scoreDao;
	}

	
}
