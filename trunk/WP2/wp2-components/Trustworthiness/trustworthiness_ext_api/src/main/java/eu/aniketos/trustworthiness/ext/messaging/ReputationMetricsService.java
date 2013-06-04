package eu.aniketos.trustworthiness.ext.messaging;

import java.util.Map;

import eu.aniketos.trustworthiness.ext.rules.model.event.ConsumerRatingEvent;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ReputationMetricsService {

	/**
	 * @param metric Map containing an evaluation of a trustworthiness property
	 * @throws Exception
	 */
	public abstract void receiveRatings(Map<String, String> metric);
	
	/**
	 * @param event
	 * @throws Exception
	 */
	public abstract void processReputationRating(ConsumerRatingEvent event);
	
}