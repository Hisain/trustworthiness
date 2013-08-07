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
package eu.aniketos.trustworthiness.impl.messaging;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.ITrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.Trustworthiness;
import eu.aniketos.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.trustworthiness.trust.management.composite.CompositeTrustUpdate;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class TrustworthinessPredictionServiceImpl implements
		ITrustworthinessPrediction {

	private static Logger logger = Logger
			.getLogger(TrustworthinessPredictionServiceImpl.class);

	private ServiceEntityService serviceEntityService;

	ServiceTrustUpdatePolicy trustUpdate;

	CompositeTrustUpdate csTrustUpdate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.messaging.
	 * ITrustworthinessPrediction#getTrustworthiness(java.lang.String)
	 */
	public Trustworthiness getTrustworthiness(String serviceId) {

		if (serviceId == null) {
			logger.warn("received serviceId is null");
			throw new RuntimeException("received serviceId is null");

		}

		Trustworthiness trustworthiness = null;

		try {
			if (serviceEntityService.isAtomic(serviceId)) {

				trustworthiness = trustUpdate.updateTrust(serviceId);

			} else if (serviceEntityService.isComposite(serviceId)) {

				trustworthiness = csTrustUpdate
						.aggregateTrustworthiness(serviceId);
			} else {
				logger.warn("Could not find service " + serviceId
						+ " in the repository.");
				throw new RuntimeException("Could not find service "
						+ serviceId + " in the repository");
			}
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
		}

		return trustworthiness;

	}

	/**
	 * @return
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * @param sEntityService
	 */
	public void setServiceEntityService(ServiceEntityService sEntityService) {
		this.serviceEntityService = sEntityService;
	}

	/**
	 * @return
	 */
	public ServiceTrustUpdatePolicy getTrustUpdate() {
		return trustUpdate;
	}

	/**
	 * @param trustUpdate
	 */
	public void setTrustUpdate(ServiceTrustUpdatePolicy trustUpdate) {
		this.trustUpdate = trustUpdate;
	}

	/**
	 * @return
	 */
	public CompositeTrustUpdate getCsTrustUpdate() {
		return csTrustUpdate;
	}

	/**
	 * @param csTrustUpdate
	 */
	public void setCsTrustUpdate(CompositeTrustUpdate csTrustUpdate) {
		this.csTrustUpdate = csTrustUpdate;
	}
}
