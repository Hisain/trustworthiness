package eu.aniketos.trustworthiness.rules.service;

import java.util.Map;

import eu.aniketos.trustworthiness.ext.rules.model.event.ConsumerRatingEvent;


/**
 * @author Hisain Elshaafi (TSSG)
 * triggers generation of ratings through invoking JBoss rules
 */
public interface ReputationRatingUpdate {

	/**
	 * @param metric
	 * @throws Exception
	 */
	void generateTrustRating(Map<String, String> metric) throws Exception;

	/**
	 * @param event
	 * @throws Exception
	 */
	void generateTrustRating(ConsumerRatingEvent event) throws Exception;

}