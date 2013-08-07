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
package eu.aniketos.trustworthiness.runtime.test.prediction.management;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;

import eu.aniketos.data.ICompositionPlan;
import eu.aniketos.trustworthiness.ext.messaging.ICompositeTrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.ITrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.Trustworthiness;
import eu.aniketos.trustworthiness.runtime.test.prediction.management.CompositionPlan;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class TrustworthinessRequestTest {

	private static Logger logger = Logger
			.getLogger(TrustworthinessRequestTest.class);

	ITrustworthinessPrediction twPrediction;
	ICompositeTrustworthinessPrediction ctwPrediction;

	/**
	 * @param twPrediction
	 * @param ctwPrediction
	 */
	public TrustworthinessRequestTest(ITrustworthinessPrediction twPrediction,
			ICompositeTrustworthinessPrediction ctwPrediction) {
		super();
		this.twPrediction = twPrediction;
		this.ctwPrediction = ctwPrediction;
	}

	/**
	 * 
	 */
	public void requestTrustworthiness() {

		logger.info("sending nonexisting service id");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		try {
			twPrediction.getTrustworthiness("testId00");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		logger.info("sending existing service id");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		try {
			twPrediction.getTrustworthiness("testId07");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		try {
			twPrediction.getTrustworthiness("testId08");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		Set<String> componentServices = new HashSet<String>();
		componentServices.add("testId05");
		componentServices.add("testId07");
		componentServices.add("testId08");

		Trustworthiness tw = null;
		try {
			tw = ctwPrediction.getCompositeTrustworthiness("testXId02",
					componentServices);

			if (tw != null) {
				logger.info("testXId02 trustworthiness score = "
						+ tw.getTrustworthinessScore() + " qos confidence = "
						+ tw.getQosConfidence());
			} else {
				logger.info("null was returned for testXId02 trustworthiness request");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		logger.info("send empty composite service");
		componentServices = new HashSet<String>();
		try {
			tw = ctwPrediction.getCompositeTrustworthiness("testXId03",
					componentServices);

			if (tw == null) {
				logger.info("testXId03 trustworthiness = null");
			} else {
				logger.info("testXId03 trustworthiness score = "
						+ tw.getTrustworthinessScore() + " qos confidence = "
						+ tw.getQosConfidence());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		logger.info("send null composite service");
		componentServices = null;
		try {
			tw = ctwPrediction.getCompositeTrustworthiness("testXId03",
					componentServices);
			if (tw == null) {
				logger.info("testXId03 trustworthiness = null");
			} else {
				logger.info("testXId03 trustworthiness score = "
						+ tw.getTrustworthinessScore() + " qos confidence = "
						+ tw.getQosConfidence());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		logger.info("send null composite service but with components");
		componentServices = new HashSet<String>();
		componentServices.add("id05");
		componentServices.add("id07");
		componentServices.add("id08");

		try {
			tw = ctwPrediction.getCompositeTrustworthiness(null,
					componentServices);

			if (tw == null) {
				logger.info("CS (null) trustworthiness = null");
			} else {
				logger.info("CS (null) trustworthiness score = "
						+ tw.getTrustworthinessScore() + " qos confidence = "
						+ tw.getQosConfidence());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		// ////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("data/cs_example_test.xml"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}

		String xml = "";

		while (scanner != null && scanner.hasNext()) {

			String line = scanner.nextLine();

			xml = xml.concat(line + "\n");

		}

		ICompositionPlan plan = new CompositionPlan(xml);

		Trustworthiness trustworthiness = ctwPrediction
				.getCompositeTrustworthiness(plan);

		if (logger.isDebugEnabled()) {
			if (trustworthiness != null) {
				logger.debug("received trustworthiness for composite service "
						+ trustworthiness.getServiceId() + " = "
						+ trustworthiness.getTrustworthinessScore());
			} else {
				logger.debug("received trustworthiness is null");
			}
		}
	}
}
