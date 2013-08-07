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
package eu.aniketos.trustworthiness.mock.prediction.management;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import eu.aniketos.data.ICompositionPlan;
import eu.aniketos.trustworthiness.ext.messaging.ICompositeTrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.ITrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.Trustworthiness;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class TrustworthinessRequest {

	private static Logger logger = Logger
			.getLogger(TrustworthinessRequest.class);

	ITrustworthinessPrediction twPrediction;
	ICompositeTrustworthinessPrediction ctwPrediction;

	/**
	 * @param twPrediction
	 * @param ctwPrediction
	 */
	public TrustworthinessRequest(ITrustworthinessPrediction twPrediction,
			ICompositeTrustworthinessPrediction ctwPrediction) {
		super();
		this.twPrediction = twPrediction;
		this.ctwPrediction = ctwPrediction;
	}

	/**
	 * 
	 */
	public void requestTrustworthiness() {

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("data/services_data.txt"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}

		while (scanner != null && scanner.hasNext()) {

			String line = scanner.nextLine();

			String serviceId = line;

			Trustworthiness trustworthiness = twPrediction
					.getTrustworthiness(line);

			if (logger.isDebugEnabled()) {
				logger.debug("received trustworthiness for service: "
						+ serviceId + "=" + trustworthiness);
			}
		}

		// ////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\

		Scanner scanner2 = null;
		try {
			scanner2 = new Scanner(new File("data/cs_example.xml"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}

		String xml = "";

		while (scanner2 != null && scanner2.hasNext()) {

			String line = scanner2.nextLine();

			xml = xml.concat(line + "\n");

		}

		ICompositionPlan plan = new CompositionPlan(xml);

		Trustworthiness trustworthiness = ctwPrediction
				.getCompositeTrustworthiness(plan);

		if (logger.isDebugEnabled()) {
			logger.debug("received trustworthiness for composite service "
					+ trustworthiness.getServiceId() + " = "
					+ trustworthiness.getTrustworthinessScore());
		}

		// ////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\

		Scanner scanner3 = null;
		try {
			scanner3 = new Scanner(new File("data/composition_plan1.xml"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}

		String xml2 = "";

		while (scanner3 != null && scanner3.hasNext()) {

			String line = scanner3.nextLine();

			xml2 = xml2.concat(line + "\n");

		}

		ICompositionPlan plan1 = new CompositionPlan(xml2);

		if (logger.isDebugEnabled()) {
			logger.debug("XML file length " + plan1.getBPMNXML().length());
		}

		Trustworthiness trustworthiness2 = ctwPrediction
				.getCompositeTrustworthiness(plan1);

		if (logger.isDebugEnabled()) {
			logger.debug("received trustworthiness for composite service "
					+ trustworthiness2.getServiceId() + " = "
					+ trustworthiness2.getTrustworthinessScore());
		}
	}
}
