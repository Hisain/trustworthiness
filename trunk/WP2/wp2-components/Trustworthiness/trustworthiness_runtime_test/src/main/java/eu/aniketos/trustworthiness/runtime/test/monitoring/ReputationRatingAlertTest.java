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
						"eu/aniketos/trustworthiness/monitoring/reputation",
						props);
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
				"eu/aniketos/trustworthiness/monitoring/reputation",
				props);
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