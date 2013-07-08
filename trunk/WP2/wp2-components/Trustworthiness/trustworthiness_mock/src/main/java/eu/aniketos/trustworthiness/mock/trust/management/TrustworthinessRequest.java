package eu.aniketos.trustworthiness.mock.trust.management;

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

			Trustworthiness trustworthiness = twPrediction.getTrustworthiness(line);

			if (logger.isDebugEnabled()) {
				logger.debug("received trustworthiness for service: " + serviceId + "=" + trustworthiness);
			}
		}
		
		//////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\
		
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
		
		Trustworthiness trustworthiness = ctwPrediction.getCompositeTrustworthiness(plan);

		if (logger.isDebugEnabled()) {
			logger.debug("received trustworthiness for composite service " + trustworthiness.getServiceId()+ " = " + trustworthiness.getTrustworthinessScore());
		}
		

		//////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\
		
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
		
		Trustworthiness trustworthiness2 = ctwPrediction.getCompositeTrustworthiness(plan1);

		if (logger.isDebugEnabled()) {
			logger.debug("received trustworthiness for composite service " + trustworthiness2.getServiceId()+ " = " + trustworthiness2.getTrustworthinessScore());
		}
	}
}
