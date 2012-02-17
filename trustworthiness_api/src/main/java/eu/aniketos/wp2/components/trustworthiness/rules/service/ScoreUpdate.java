package eu.aniketos.wp2.components.trustworthiness.rules.service;

import java.util.Map;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ScoreUpdate {

	void updateScore(Map<String, String> metric) throws Exception;

}