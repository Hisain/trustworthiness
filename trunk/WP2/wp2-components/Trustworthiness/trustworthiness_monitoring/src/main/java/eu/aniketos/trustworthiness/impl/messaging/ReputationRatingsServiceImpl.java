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
package eu.aniketos.trustworthiness.impl.messaging;

import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.IReputationRatingsService;
import eu.aniketos.trustworthiness.ext.rules.model.event.ConsumerRatingEvent;
import eu.aniketos.trustworthiness.impl.messaging.util.PropertyValidator;
import eu.aniketos.trustworthiness.rules.service.ReputationRatingUpdate;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ReputationRatingsServiceImpl implements IReputationRatingsService {

	private static Logger logger = Logger
			.getLogger(ReputationRatingsServiceImpl.class);

	private ReputationRatingUpdate ratingUpdate;

	private ServiceEntityService serviceEntityService;
	
	private PropertyValidator validator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.messaging.QosMetricsService
	 * #receiveMetrics(java.util.Map)
	 */
	public void receiveRatings(Map<String, String> rating) {

		logger.debug("received new rating ");
		if (rating == null
				|| rating.size() == 0
				|| !rating.containsKey("serviceId")
				|| rating.get("serviceId") == null
				|| rating.get("serviceId") == ""
				|| !rating.containsKey("consumerId")
				|| rating.get("consumerId") == null
				|| rating.get("consumerId") == ""
				|| !validator.isValidConsumer(rating.get("consumerId"))
				|| !rating.containsKey("transactionId")
				|| rating.get("transactionId") == null
				|| rating.get("transactionId") == ""
				|| !rating.containsKey("property")
				|| rating.get("property") == null
				|| rating.get("property") == ""
				|| !rating.containsKey("value")
				|| rating.get("value") == null
				|| rating.get("value") == ""
				|| !validator.isNumeric(rating.get("value"))
				|| (rating.containsKey("subproperty") && (rating
						.get("subproperty") == null || rating
						.get("subproperty") == ""))) {
			logger.warn("received consumer rating contains null, empty or invalid data");
			

		} else {

			try {
				ratingUpdate.generateTrustRating(rating);
			} catch (Exception e) {
				logger.error("Exception: " + e.getMessage());
			}
		}
	}

	public void processReputationRating(ConsumerRatingEvent event) {

		logger.debug("received new rating " + event.getTransactionId());
		if (event == null || event.getServiceId() == null
				|| event.getConsumerId() == null
				|| event.getTransactionId() == null
				|| event.getProperty() == null || event.getValue() == null
				|| event.getServiceId().isEmpty()
				|| event.getConsumerId().isEmpty()
				|| event.getTransactionId().isEmpty()
				|| !event.getProperty().equalsIgnoreCase("reputation")
				|| event.getProperty().isEmpty() || event.getValue().isEmpty()
				|| !validator.isValidConsumer(event.getConsumerId())
				|| !validator.isNumeric(event.getValue())) {

			logger.warn("received consumer rating contains null, empty or invalid data");
		

		} else {

			try {
				ratingUpdate.generateTrustRating(event);
			} catch (Exception e) {
				logger.error("Exception: " + e.getMessage());
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
	public ReputationRatingUpdate getRatingUpdate() {
		return ratingUpdate;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param ratingUpdate
	 */
	public void setRatingUpdate(ReputationRatingUpdate ratingUpdate) {
		this.ratingUpdate = ratingUpdate;
	}

	/**
	 * required for Spring dependency injection
	 */
	public PropertyValidator getValidator() {
		return validator;
	}

	/**
	 * required for Spring dependency injection
	 */
	public void setValidator(PropertyValidator validator) {
		this.validator = validator;
	}

}
