package eu.aniketos.trustworthiness.impl.trust.management;

import java.util.UUID;

import eu.aniketos.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.trustworthiness.impl.trust.pojo.QoSMetric;
import eu.aniketos.trustworthiness.impl.trust.pojo.Rating;
import eu.aniketos.trustworthiness.impl.trust.pojo.SecProperty;
import eu.aniketos.trustworthiness.impl.trust.pojo.Service;
import eu.aniketos.trustworthiness.impl.trust.pojo.ThreatLevel;
import eu.aniketos.trustworthiness.impl.trust.pojo.TrustworthinessEntity;
import eu.aniketos.trustworthiness.trust.management.TrustFactory;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class TrustFactoryImpl implements TrustFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.trust.management.TrustFactory
	 * #createService(java.lang.String)
	 */
	public Atomic createService(String serviceId) {
		Atomic service = new Atomic(serviceId);

		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.trust.management.TrustFactory
	 * #createRating (eu.aniketos.trustworthiness.impl.trust.pojo.Service)
	 */
	public Rating createReputationRating(Service service) {
		Rating rating = new Rating();
		rating.setService(service);
		// id should be shared with member table
		rating.setId(UUID.randomUUID().toString());

		return rating;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.trust.management.TrustFactory
	 * #createComposite(java.lang.String)
	 */
	public Composite createComposite(String serviceId) {
		
		Composite service = new Composite(serviceId);

		return service;
	}

	public Composite createTransientComposite() {

		Composite service = new Composite();
		service.setId(UUID.randomUUID().toString());
		return service;
	}

	public SecProperty createSecPropertyRating(Service service) {
		
		SecProperty sec = new SecProperty();
		sec.setService(service);
		sec.setId(UUID.randomUUID().toString());
		return sec;
	}

	public QoSMetric createQoSRating(Service service) {
		
		QoSMetric metric = new QoSMetric();
		metric.setService(service);
		// id should be shared with member table
		metric.setId(UUID.randomUUID().toString());

		return metric;
	}

	public ThreatLevel createThreatRating(Service service) {

		ThreatLevel threatLevel = new ThreatLevel();
		threatLevel.setService(service);
		threatLevel.setId(UUID.randomUUID().toString());

		return threatLevel;
	}

	public TrustworthinessEntity createTrustworthiness(String serviceId) {
		
		TrustworthinessEntity trustworthinessEntity = new TrustworthinessEntity();
		trustworthinessEntity.setId(serviceId);

		return trustworthinessEntity;
	}

}
