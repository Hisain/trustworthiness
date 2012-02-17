package eu.aniketos.wp2.components.trustworthiness.trust.management;

import eu.aniketos.wp2.components.trustworthiness.rules.service.ScoreUpdate;


public interface MonitorHelper {
	void setupObservers(ScoreUpdate scoreUpdate);
}
