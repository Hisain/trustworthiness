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
package eu.aniketos.trustworthiness.client.remote.trust.management;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.aniketos.data.ICompositionPlan;
import eu.aniketos.trustworthiness.ext.messaging.ICompositeTrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.ITrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.Trustworthiness;

@SuppressWarnings("rawtypes")
public class Activator implements BundleActivator {

	private static Logger logger = LoggerFactory.getLogger(Activator.class);

	private static BundleContext context;

	private ServiceTracker tracker;

	private ServiceTracker tracker2;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	@SuppressWarnings("unchecked")
	public void start(BundleContext bundleContext) throws Exception {

		logger.debug("Starting trustworthiness democlient");
		System.out.println("Starting trustworthiness democlient");

		Activator.context = bundleContext;

		tracker = new ServiceTracker(bundleContext,
				ITrustworthinessPrediction.class.getName(), null);
		tracker.open();

		tracker2 = new ServiceTracker(bundleContext,
				ICompositeTrustworthinessPrediction.class.getName(), null);
		tracker2.open();

		Thread thread = new Thread(new Runnable() {

			public void run() {
				System.out.println("Thread started");

				System.out.println("Trying to access service...");

				Scanner scanner = null;

				try {
					scanner = new Scanner(new File("data/cs_example.xml"));

				} catch (FileNotFoundException e) {
					logger.error(e.getMessage());
				}

				String xml2 = "";

				while (scanner != null && scanner.hasNext()) {

					String line = scanner.nextLine();

					xml2 = xml2.concat(line + "\n");

				}

				ICompositionPlan plan1 = new CompositionPlan(xml2);

				if (logger.isDebugEnabled()) {
					logger.debug("XML file length "
							+ plan1.getBPMNXML().length());
				}

				int counter = 1;

				while (counter < 11) {

					ITrustworthinessPrediction predictionService = (ITrustworthinessPrediction) tracker
							.getService();
					if (predictionService != null) {
						try {
							Trustworthiness trustworthiness = predictionService
									.getTrustworthiness("http://83.235.133.36/AniketosWS/DoUPModuleSoap12HttpPort?wsdl");

							if (trustworthiness != null) {
								System.out.println(trustworthiness
										.getServiceId());
								System.out.println(trustworthiness
										.getTrustworthinessScore());
							} else {
								System.out.println("Trustworthiness is null");
							}
						} catch (RuntimeException ex) {
							ex.printStackTrace();
							System.err
									.println("Error in atomic service trustworthiness processing: "
											+ ex.getMessage());
						}
					}

					ICompositeTrustworthinessPrediction compositePredictionService = (ICompositeTrustworthinessPrediction) tracker2
							.getService();

					if (compositePredictionService != null) {

						try {
							Trustworthiness trustworthiness = compositePredictionService
									.getCompositeTrustworthiness(plan1);

							if (logger.isDebugEnabled()) {
								logger.debug("received trustworthiness for composite service "
										+ trustworthiness.getServiceId()
										+ " = "
										+ trustworthiness
												.getTrustworthinessScore());
							}

							if (trustworthiness != null) {
								System.out.println(trustworthiness
										.getServiceId());
								System.out.println(trustworthiness
										.getTrustworthinessScore());
							} else {
								System.out
										.println("Composite Trustworthiness is null");
							}

						} catch (RuntimeException ex) {
							ex.printStackTrace();
							System.err
									.println("Error in composite service trustworthiness processing: "
											+ ex.getMessage());
						}
					} else {
						logger.debug("composite prediction service "
								+ compositePredictionService);
					}

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println("Retry..." + counter++);
				}

				System.out.println("Thread stopped");
			}

		});
		thread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;

		tracker2.close();
	}

}
