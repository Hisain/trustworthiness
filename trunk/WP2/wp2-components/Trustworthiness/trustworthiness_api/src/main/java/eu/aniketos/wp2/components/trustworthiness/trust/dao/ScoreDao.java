package eu.aniketos.wp2.components.trustworthiness.trust.dao;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;

/**
 * Data Access Object interface for scores
 *
 * @author: Hisain Elshaafi
 */
public interface ScoreDao {

	public abstract void addScore(Score score);
	
	public abstract void updateScore(Score score);

	public abstract List<Score> getScoresByServiceId(final String source);
	
	public abstract void deleteScore(Score score);


}
