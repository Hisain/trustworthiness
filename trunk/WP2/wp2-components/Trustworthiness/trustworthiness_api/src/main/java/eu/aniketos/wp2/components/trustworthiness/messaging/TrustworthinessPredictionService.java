package eu.aniketos.wp2.components.trustworthiness.messaging;

import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

public interface TrustworthinessPredictionService {
	
	abstract public Trustworthiness getTrustworthiness(String serviceId);
}
