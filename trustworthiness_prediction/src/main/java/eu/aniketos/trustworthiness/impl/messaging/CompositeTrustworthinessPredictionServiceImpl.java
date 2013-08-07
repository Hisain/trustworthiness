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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import eu.aniketos.trustworthiness.ext.messaging.ICompositeTrustworthinessPrediction;
import eu.aniketos.data.ICompositionPlan;
import eu.aniketos.trustworthiness.ext.messaging.Trustworthiness;
import eu.aniketos.trustworthiness.impl.messaging.util.BPMNParser;
import eu.aniketos.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.trustworthiness.trust.management.composite.CompositeTrustUpdate;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class CompositeTrustworthinessPredictionServiceImpl implements
		ICompositeTrustworthinessPrediction {

	private static Logger logger = Logger
			.getLogger(CompositeTrustworthinessPredictionServiceImpl.class);

	private ServiceEntityService serviceEntityService;

	private CompositeTrustUpdate csTrustUpdate;

	private TrustFactory tFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.messaging.
	 * ICompositeTrustworthinessPrediction
	 * #getCompositeTrustworthiness(java.lang.String, java.util.Set)
	 */
	public Trustworthiness getCompositeTrustworthiness(String serviceId,
			Set<String> componentServices) {

		// return an exception if empty or null parameters
		if (serviceId == null || componentServices == null
				|| serviceId.length() == 0 || componentServices.size() == 0) {

			logger.error("received composite serviceId or components are null or empty");
			return null;
		}

		Composite cs = serviceEntityService.getComposite(serviceId);

		Trustworthiness trustworthiness = null;

		try {
			if (cs == null) {
				logger.info("Could not find service in the repository. New composite service will be created");

				cs = tFactory.createComposite(serviceId);

				Set<Atomic> services = new HashSet<Atomic>();

				for (String s : componentServices) {

					Atomic service = serviceEntityService.getAtomic(s);

					// return an exception if component service is unknown
					if (service == null) {
						logger.error("Could not find component service " + s
								+ " in the repository");
						return null;
					}

					logger.debug("adding component " + service.getId());
					services.add(service);
				}

				cs.setComponentServices(services);

				serviceEntityService.addComposite(cs);

				trustworthiness = csTrustUpdate
						.aggregateTrustworthiness(serviceId);

			} else {

				logger.debug("aggregating trustworthiness");
				trustworthiness = csTrustUpdate
						.aggregateTrustworthiness(serviceId);

			}
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage());
		}

		return trustworthiness;

	}

	public Trustworthiness getCompositeTrustworthiness(ICompositionPlan plan) {

		if (plan == null) {
			logger.error("composition plan is null.");
			return null;

		}

		SAXBuilder builder = new SAXBuilder();

		Document doc = null;

		try {

			InputStream is = new ByteArrayInputStream(plan.getBPMNXML()
					.getBytes("UTF-8"));
			doc = (Document) builder.build(is);

		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException:" + e);
		} catch (JDOMException e) {
			logger.error("JDOMException:" + e);
		} catch (IOException e) {
			logger.error("IOException:" + e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("document " + doc);
		}
		String csId = BPMNParser.getProcessId(doc);

		Composite cs = null;

		if (csId != null) {
			cs = serviceEntityService.getComposite(csId);
		} else {
			logger.error("composition plan with no process ID.");
			return null;
		}

		if (cs == null) {

			logger.info("Could not find service in the repository. New composite service will be created");

			cs = tFactory.createComposite(csId);

			serviceEntityService.addComposite(cs);
		}

		List<String> componentServices = BPMNParser.getServicesList(plan
				.getBPMNXML());

		if (logger.isDebugEnabled()) {
			logger.debug("composition plan contains "
					+ componentServices.size() + " components.");
		}

		Set<Atomic> services = new HashSet<Atomic>();

		for (String s : componentServices) {

			Atomic service = serviceEntityService.getAtomic(s);

			// return an exception if component service is unknown
			if (service == null) {
				throw new RuntimeException("Could not find service " + s
						+ " in the repository");
			}

			if (logger.isDebugEnabled()) {
				logger.debug("adding component " + service.getId());
			}

			services.add(service);
		}

		cs.setComponentServices(services);

		serviceEntityService.updateComposite(cs);

		Trustworthiness trustworthiness = null;

		try {

			trustworthiness = csTrustUpdate.aggregateTrustworthiness(cs);

		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage());
		}

		return trustworthiness;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return data access service for atomic and composite Web services
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param sEntityService
	 *            data access service for atomic and composite Web services
	 */
	public void setServiceEntityService(ServiceEntityService sEntityService) {
		this.serviceEntityService = sEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public CompositeTrustUpdate getCsTrustUpdate() {
		return csTrustUpdate;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param csTrustUpdate
	 */
	public void setCsTrustUpdate(CompositeTrustUpdate csTrustUpdate) {
		this.csTrustUpdate = csTrustUpdate;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return service and rating score objects factory
	 */
	public TrustFactory gettFactory() {
		return tFactory;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param tFactory
	 *            service and rating score objects factory
	 */
	public void settFactory(TrustFactory tFactory) {
		this.tFactory = tFactory;
	}

}
