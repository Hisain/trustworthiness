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
package eu.aniketos.trustworthiness.runtime.test.monitoring;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.trustworthiness.impl.trust.pojo.Rating;
import eu.aniketos.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.trustworthiness.trust.service.RatingEntityService;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ReputationRatingAlertTest {

	private static Logger logger = Logger
			.getLogger(ReputationRatingAlertTest.class);

	private ServiceEntityService serviceEntityService;

	private RatingEntityService ratingEntityService;

	private TrustFactory trustFactory;

	private EventAdmin eventAdmin;

	int maxDescriptionSize = 0;

	Map<String, String> properties = new HashMap<String, String>();

	/**
	 * 
	 */
	public void initialize() {

		// inject new service and its ratings
		String serviceId = "testInjId1";
		Atomic testService = serviceEntityService.getAtomic(serviceId);
		if (testService == null) {
			testService = trustFactory.createService(serviceId);
			serviceEntityService.addAtomic(testService);
		}

		// send an alert to prediction
		HashMap<String, String> props = new HashMap<String, String>();
		props.put("service.id", serviceId);
		Event osgiEvent = new Event(
				"eu/aniketos/trustworthiness/monitoring/reputation", props);
		eventAdmin.sendEvent(osgiEvent);

		logger.debug("sent alert1 to topic eu/aniketos/trustworthiness/monitoring/reputation");

		Rating rating = trustFactory.createReputationRating(testService);
		rating.setScore(.91);
		rating.setRecency(System.currentTimeMillis() / 3600000);
		rating.setProperty("reputation");
		rating.setConsumerId("cTest001");
		rating.setTransactionId("tTest001");
		ratingEntityService.addRating(rating);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rating = trustFactory.createReputationRating(testService);
		rating.setScore(.96);
		rating.setRecency(System.currentTimeMillis() / 3600000);
		rating.setProperty("reputation");
		rating.setConsumerId("cTest002");
		rating.setTransactionId("tTest002");
		ratingEntityService.addRating(rating);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rating = trustFactory.createReputationRating(testService);
		rating.setScore(.99);
		rating.setRecency(System.currentTimeMillis() / 3600000);
		rating.setProperty("reputation");
		rating.setConsumerId("cTest003");
		rating.setTransactionId("tTest003");
		ratingEntityService.addRating(rating);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rating = trustFactory.createReputationRating(testService);
		rating.setScore(.94);
		rating.setRecency(System.currentTimeMillis() / 3600000);
		rating.setProperty("reputation");
		rating.setConsumerId("cTest004");
		rating.setTransactionId("tTest004");
		ratingEntityService.addRating(rating);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rating = trustFactory.createReputationRating(testService);
		rating.setScore(.91);
		rating.setRecency(System.currentTimeMillis() / 3600000);
		rating.setProperty("reputation");
		rating.setConsumerId("cTest005");
		rating.setTransactionId("tTest005");
		ratingEntityService.addRating(rating);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// send an alert to prediction
		props.put("service.id", serviceId);
		osgiEvent = new Event(
				"eu/aniketos/trustworthiness/monitoring/reputation", props);
		eventAdmin.sendEvent(osgiEvent);

		logger.debug("sent alert2 to topic eu/aniketos/trustworthiness/monitoring/reputation");

	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param serviceEntityService
	 */
	public void setServiceEntityService(
			ServiceEntityService serviceEntityService) {
		this.serviceEntityService = serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public RatingEntityService getRatingEntityService() {
		return ratingEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param ratingEntityService
	 */
	public void setRatingEntityService(RatingEntityService ratingEntityService) {
		this.ratingEntityService = ratingEntityService;
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

	/**
	 * required for Spring dependency injection
	 * 
	 * @return OSGi event admin
	 */
	public EventAdmin getEventAdmin() {
		return eventAdmin;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param eventAdmin
	 *            OSGi event admin
	 */
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}
}