package eu.aniketos.wp2.components.trustworthiness.trust.dao;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;

/**
 * Data Access Object interface for scores
 *
 * @author: Hisain Elshaafi
 */
public interface ScoreDao {

	/**
	 * adds a rating score to database
	 * @param score rating score
	 */
	public abstract void addScore(Score score);
	
	/**
	 * updates an existing or creates a new score in database
	 * @param score rating score
	 */
	public abstract void updateScore(Score score);

	/**
	 * @param serviceId service String id
	 * @return list of existing scores
	 */
	public abstract List<Score> getScoresByServiceId(final String serviceId);
	
	/**
	 * @param score deletes a score from database
	 */
	public abstract void deleteScore(Score score);


}
