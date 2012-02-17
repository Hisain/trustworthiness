package eu.aniketos.wp2.components.trustworthiness.messaging;

import java.util.Set;

import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

public interface CompositeTrustworthinessPredictionService {
	abstract public Trustworthiness getCompositeTrustworthiness(String serviceId, Set<String> componentServices);
}
