package eu.aniketos.trustworthiness.rules.service;

import java.util.Map;

import eu.aniketos.trustworthiness.ext.rules.model.event.TrustEvent;

/**
 * @author Hisain Elshaafi (TSSG)
 * triggers generation of ratings through invoking JBoss rules
 */
public interface MetricRatingUpdate {

	/**
	 * @param metric
	 * @throws Exception
	 */
	void generateRating(Map<String, String> metric) throws Exception;

	/**
	 * @param event
	 * @throws Exception
	 */
	void generateRating(TrustEvent event) throws Exception;

}