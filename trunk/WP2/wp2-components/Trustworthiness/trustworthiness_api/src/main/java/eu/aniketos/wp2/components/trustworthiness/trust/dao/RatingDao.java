package eu.aniketos.wp2.components.trustworthiness.trust.dao;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating;

/**
 * Data Access Object interface for scores
 *
 * @author: Hisain Elshaafi
 */
public interface RatingDao {

	/**
	 * adds a rating score to database
	 * @param rating rating score
	 */
	public abstract void addRating(Rating rating);
	
	/**
	 * updates an existing or creates a new score in database
	 * @param rating rating score
	 */
	public abstract void updateRating(Rating rating);

	/**
	 * @param serviceId service String id
	 * @return list of existing scores
	 */
	public abstract List<Rating> getRatingsByServiceId(final String serviceId);
	
	/**
	 * @param rating deletes a score from database
	 */
	public abstract void deleteRating(Rating rating);


}
