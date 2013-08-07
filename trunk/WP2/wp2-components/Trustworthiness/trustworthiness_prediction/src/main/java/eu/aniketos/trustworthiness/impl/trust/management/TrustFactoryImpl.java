/**
 * Copyright (c) 2013, Waterford Institute of Technology
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    - Neither the name of Waterford Institute of Technology nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *      
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL WATERFORD INSTITUTE OF TECHNOLOGY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
