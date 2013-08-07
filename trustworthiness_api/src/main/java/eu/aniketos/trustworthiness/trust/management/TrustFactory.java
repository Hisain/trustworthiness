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
 * documentation and/or other materials provided with the distribution.
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
package eu.aniketos.trustworthiness.trust.management;

import eu.aniketos.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.trustworthiness.impl.trust.pojo.QoSMetric;
import eu.aniketos.trustworthiness.impl.trust.pojo.Rating;
import eu.aniketos.trustworthiness.impl.trust.pojo.SecProperty;
import eu.aniketos.trustworthiness.impl.trust.pojo.Service;
import eu.aniketos.trustworthiness.impl.trust.pojo.ThreatLevel;
import eu.aniketos.trustworthiness.impl.trust.pojo.TrustworthinessEntity;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface TrustFactory {

	/**
	 * @param serviceId
	 *            String service id
	 * @return
	 */
	public abstract Atomic createService(String serviceId);

	/**
	 * @param serviceId
	 *            String service id
	 * @return
	 */
	public abstract Composite createComposite(String serviceId);

	/**
	 * @param serviceId
	 *            String service id
	 * @return
	 */
	public abstract Composite createTransientComposite();

	/**
	 * @param service
	 *            new service object
	 * @return new Rating object
	 */
	public abstract Rating createReputationRating(Service service);

	/**
	 * @param service
	 *            service to rate
	 * @return new SecProperty
	 */
	public abstract SecProperty createSecPropertyRating(Service service);

	/**
	 * @param service
	 * @return
	 */
	public abstract QoSMetric createQoSRating(Service service);

	/**
	 * @param service
	 * @return
	 */
	public abstract ThreatLevel createThreatRating(Service service);

	/**
	 * @param serviceId
	 * @return
	 */
	public abstract TrustworthinessEntity createTrustworthiness(String serviceId);

}
