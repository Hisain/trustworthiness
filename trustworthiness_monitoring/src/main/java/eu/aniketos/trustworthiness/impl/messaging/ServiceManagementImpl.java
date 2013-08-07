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

import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.IServiceManagement;
import eu.aniketos.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;

public class ServiceManagementImpl implements IServiceManagement {

	private static Logger logger = Logger
			.getLogger(ServiceManagementImpl.class);

	private ServiceEntityService serviceEntityService;

	private TrustFactory trustFactory;

	public void addService(Map<String, String> serviceData) {
		if (serviceData == null || serviceData.size() == 0
				|| !serviceData.containsKey("serviceId")
				|| serviceData.get("serviceId") == null
				|| serviceData.get("serviceId") == "") {
			logger.warn("received service data contains null or empty element");
			throw new RuntimeException(
					"received service data contains null or empty element");

		} else {

			String serviceId = serviceData.get("serviceId");
			Atomic service = null;
			if ((service = serviceEntityService.getAtomic(serviceId)) != null) {
				logger.warn("service " + serviceId + " already exists");
			} else {
				logger.info("creating new service entry");
				service = trustFactory.createService(serviceId);

				serviceEntityService.addAtomic(service);
			}
		}
	}

	public void addService(String serviceId) {
		if (serviceId == null || serviceId.length() == 0) {
			logger.warn("received serviceId is null or empty");
			throw new RuntimeException("received serviceId is null or empty");

		} else {

			Atomic service = null;
			if ((service = serviceEntityService.getAtomic(serviceId)) != null) {
				logger.warn("service " + serviceId + " already exists");
			} else {
				logger.info("creating new service entry");
				service = trustFactory.createService(serviceId);

				serviceEntityService.addAtomic(service);
			}
		}
	}

	public void removeService(String serviceId) {
		if (serviceId == null || serviceId.length() == 0) {
			logger.warn("received serviceId is null or empty");

			throw new RuntimeException("received serviceId is null or empty");

		} else {

			Atomic service = null;
			if ((service = serviceEntityService.getAtomic(serviceId)) == null) {
				logger.warn("service " + serviceId + " does not exist");
			} else {
				logger.info("deleting service entry");

				serviceEntityService.deleteAtomic(service);
			}
		}
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param serviceEntityService
	 *            data access service object for Web services
	 */
	public void setServiceEntityService(
			ServiceEntityService serviceEntityService) {
		this.serviceEntityService = serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return data access service object for Web services
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public TrustFactory getTrustFactory() {
		return trustFactory;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param trustFactory
	 */
	public void setTrustFactory(TrustFactory trustFactory) {
		this.trustFactory = trustFactory;
	}

}
