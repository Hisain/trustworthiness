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
package eu.aniketos.trustworthiness.runtime.test.monitoring;

import java.util.Random;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import eu.aniketos.trustworthiness.ext.messaging.IReputationRatingsService;
import eu.aniketos.trustworthiness.ext.rules.model.event.ConsumerRatingEvent;
import eu.aniketos.trustworthiness.runtime.test.monitoring.event.ConsumerRatingEventImpl;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ReputationRatingsTest {

	private static Logger logger = Logger
			.getLogger(ReputationRatingsTest.class);

	private IReputationRatingsService repMetrics;

	/**
	 * @param qosMetics
	 */
	public ReputationRatingsTest(IReputationRatingsService repMetrics) {
		super();
		this.repMetrics = repMetrics;
	}

	/**
	 * 
	 */
	public void initialize() {

		Random r = new Random();

		// //////////////////////////////////////////
		logger.info("normal ratings");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		for (int i = 0; i < 1000; i++) {

			ConsumerRatingEvent event = new ConsumerRatingEventImpl();

			event.setServiceId("testId10");

			event.setConsumerId("c149");

			event.setTransactionId("t" + Integer.toString(r.nextInt()));

			event.setProperty("reputation");

			event.setValue(Double.toString(0.9 + r.nextDouble() / 10));

			DateTime dt = new DateTime();
			DateTimeFormatter fmt = ISODateTimeFormat.dateTimeNoMillis();
			String timestamp = fmt.print(dt);

			event.setTimestamp(timestamp);

			/*if (logger.isDebugEnabled()) {
				logger.debug(" timestamp=" + timestamp);
			}*/

			String eventDescription = "nice service, happy to do business with..";
			event.setEventDescription(eventDescription);

			try {
				final ConsumerRatingEvent event1 = event;
				Thread thread = new Thread() {

					public void run() {
						if (logger.isDebugEnabled()) {
							logger.debug(" sending rating for transaction "
									+ event1.getTransactionId());
						}
						repMetrics.processReputationRating(event1);

					}

				};
				thread.start();
				Thread.sleep(50);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		/*
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); }
		 * 
		 * // ////////////////////////////////////////
		 * 
		 * for (int i = 0; i < 3; i++) {
		 * 
		 * ConsumerRatingEvent event = new ConsumerRatingEventImpl();
		 * 
		 * event.setServiceId("testId05");
		 * 
		 * event.setConsumerId("c159");
		 * 
		 * event.setTransactionId("t235");
		 * 
		 * event.setProperty("reputation");
		 * 
		 * event.setValue(Double.toString(0.9 + r.nextDouble() / 10));
		 * 
		 * DateTime dt = new DateTime(); DateTimeFormatter fmt =
		 * ISODateTimeFormat.dateTimeNoMillis(); String timestamp =
		 * fmt.print(dt);
		 * 
		 * event.setTimestamp(timestamp);
		 * 
		 * if (logger.isDebugEnabled()) { logger.debug(" timestamp=" +
		 * timestamp); }
		 * 
		 * String eventDescription = "thanks..";
		 * event.setEventDescription(eventDescription);
		 * 
		 * try { repMetrics.processReputationRating(event); } catch (Exception
		 * e) { logger.error(e.getMessage()); } }
		 * 
		 * // ////////////////////////////////////////
		 * 
		 * // rating with null or empty service id
		 * logger.info("rating with null or empty service id");
		 * 
		 * for (int i = 0; i < 1; i++) {
		 * 
		 * ConsumerRatingEvent event = new ConsumerRatingEventImpl();
		 * 
		 * event.setServiceId(null);
		 * 
		 * event.setConsumerId("c169");
		 * 
		 * event.setTransactionId("t236");
		 * 
		 * event.setProperty("reputation");
		 * 
		 * event.setValue(Double.toString(0.9 + r.nextDouble() / 10));
		 * 
		 * try { repMetrics.processReputationRating(event); } catch (Exception
		 * e) { logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); }
		 * 
		 * // ////////////////////////////////////////
		 * 
		 * // rating with null or empty service id
		 * logger.info("rating with null or empty consumer id");
		 * 
		 * for (int i = 0; i < 1; i++) {
		 * 
		 * ConsumerRatingEvent event = new ConsumerRatingEventImpl();
		 * 
		 * event.setServiceId("testId05");
		 * 
		 * event.setConsumerId(null);
		 * 
		 * event.setTransactionId("t288");
		 * 
		 * event.setProperty("reputation");
		 * 
		 * event.setValue(Double.toString(0.9 + r.nextDouble() / 10));
		 * 
		 * try { repMetrics.processReputationRating(event); } catch (Exception
		 * e) { logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); }
		 * 
		 * // /////////////////////////////
		 * 
		 * // rating with null or empty service id
		 * logger.info("rating with null or empty transaction id");
		 * 
		 * for (int i = 0; i < 1; i++) {
		 * 
		 * ConsumerRatingEvent event = new ConsumerRatingEventImpl();
		 * 
		 * event.setServiceId("testId05");
		 * 
		 * event.setConsumerId("c177");
		 * 
		 * event.setTransactionId(null);
		 * 
		 * event.setProperty("reputation");
		 * 
		 * event.setValue(Double.toString(0.9 + r.nextDouble() / 10));
		 * 
		 * try { repMetrics.processReputationRating(event); } catch (Exception
		 * e) { logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); }
		 * 
		 * // ////////////////////////////////////////
		 * 
		 * logger.info("sending metric as null");
		 * 
		 * for (int i = 0; i < 2; i++) {
		 * 
		 * ConsumerRatingEvent event = null;
		 * 
		 * try { repMetrics.processReputationRating(event); } catch (Exception
		 * e) { logger.error(e.getMessage()); } }
		 * 
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); }
		 * 
		 * // ////////////////////////////////////////
		 * 
		 * logger.info("sending metric as empty");
		 * 
		 * for (int i = 0; i < 2; i++) {
		 * 
		 * ConsumerRatingEvent event = new ConsumerRatingEventImpl("", "", "",
		 * "", "", "");
		 * 
		 * try { repMetrics.processReputationRating(event); } catch (Exception
		 * e) { logger.error(e.getMessage()); } }
		 * 
		 * // ////////////////////////////////////////
		 * 
		 * logger.info("invalid timestamp"); try { Thread.sleep(1000); } catch
		 * (InterruptedException e) {
		 * 
		 * logger.error(e.getMessage()); }
		 * 
		 * for (int i = 0; i < 3; i++) {
		 * 
		 * ConsumerRatingEvent event = new ConsumerRatingEventImpl();
		 * 
		 * event.setServiceId("testId10");
		 * 
		 * event.setConsumerId("c155");
		 * 
		 * event.setTransactionId("t237");
		 * 
		 * event.setProperty("reputation");
		 * 
		 * event.setValue(Double.toString(0.9 + r.nextDouble() / 10));
		 * 
		 * event.setTimestamp("34523453z");
		 * 
		 * try { repMetrics.processReputationRating(event); } catch (Exception
		 * e) { logger.error(e.getMessage()); } }
		 */

	}
}
