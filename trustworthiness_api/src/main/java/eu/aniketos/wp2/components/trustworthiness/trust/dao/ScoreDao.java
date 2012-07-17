package eu.aniketos.wp2.components.trustworthiness.trust.dao;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating;

/**
 * Data Access Object interface for scores
 *
 * @author: Hisain Elshaafi
 */
public interface ScoreDao {

	/**
	 * adds a rating score to database
	 * @param rating rating score
	 */
	public abstract void addScore(Rating rating);
	
	/**
	 * updates an existing or creates a new score in database
	 * @param rating rating score
	 */
	public abstract void updateScore(Rating rating);

	/**
	 * @param serviceId service String id
	 * @return list of existing scores
	 */
	public abstract List<Rating> getScoresByServiceId(final String serviceId);
	
	/**
	 * @param rating deletes a score from database
	 */
	public abstract void deleteScore(Rating rating);


}
