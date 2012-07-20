package eu.aniketos.wp2.components.trustworthiness.trust.service;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating;

/**
 * data access service for scores
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface RatingEntityService {

	/**
	 * @param rating
	 *            Rating object to add
	 */
	public abstract void addRating(Rating rating);

	/**
	 * @param rating
	 *            Rating object to update
	 */
	public abstract void updateRating(Rating rating);

	/**
	 * @param serviceId
	 *            String service id
	 * @return list of existing scores for service
	 */
	public abstract List<Rating> getRatingsByServiceId(final String serviceId);

	/**
	 * @param rating
	 *            Rating object to delete
	 */
	public abstract void deleteRating(Rating rating);

	
}
