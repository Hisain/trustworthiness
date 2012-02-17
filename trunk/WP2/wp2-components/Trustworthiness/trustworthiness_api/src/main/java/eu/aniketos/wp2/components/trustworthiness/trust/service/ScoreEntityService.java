package eu.aniketos.wp2.components.trustworthiness.trust.service;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ScoreEntityService {
	public abstract void addScore(Score score);
	
	public abstract void updateScore(Score score);

	public abstract List<Score> getScoresByServiceId(final String source);
	
	public abstract void deleteScore(Score score);

}
