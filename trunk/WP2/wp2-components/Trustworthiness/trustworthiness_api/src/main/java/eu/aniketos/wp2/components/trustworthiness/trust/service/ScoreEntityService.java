package eu.aniketos.wp2.components.trustworthiness.trust.service;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating;

/**
 * data access service for scores
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface ScoreEntityService {

	/**
	 * @param rating
	 *            Rating object to add
	 */
	public abstract void addScore(Rating rating);

	/**
	 * @param rating
	 *            Rating object to update
	 */
	public abstract void updateScore(Rating rating);

	/**
	 * @param serviceId
	 *            String service id
	 * @return list of existing scores for service
	 */
	public abstract List<Rating> getScoresByServiceId(final String serviceId);

	/**
	 * @param rating
	 *            Rating object to delete
	 */
	public abstract void deleteScore(Rating rating);

	
}
