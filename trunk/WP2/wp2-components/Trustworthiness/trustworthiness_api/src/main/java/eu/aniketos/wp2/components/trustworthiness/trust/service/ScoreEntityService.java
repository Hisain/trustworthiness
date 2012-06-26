package eu.aniketos.wp2.components.trustworthiness.trust.service;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;

/**
 * data access service for scores
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface ScoreEntityService {

	/**
	 * @param score
	 *            Score object to add
	 */
	public abstract void addScore(Score score);

	/**
	 * @param score
	 *            Score object to update
	 */
	public abstract void updateScore(Score score);

	/**
	 * @param serviceId
	 *            String service id
	 * @return list of existing scores for service
	 */
	public abstract List<Score> getScoresByServiceId(final String serviceId);

	/**
	 * @param score
	 *            Score object to delete
	 */
	public abstract void deleteScore(Score score);

}
