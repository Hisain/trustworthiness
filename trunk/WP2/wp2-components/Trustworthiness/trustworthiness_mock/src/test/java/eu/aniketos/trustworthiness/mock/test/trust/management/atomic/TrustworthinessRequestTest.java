package eu.aniketos.trustworthiness.mock.test.trust.management.atomic;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.ICompositeTrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.ITrustworthinessPrediction;
import eu.aniketos.trustworthiness.ext.messaging.Trustworthiness;

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
				logger.info("testXId02 trustworthiness score = " + tw.getTrustworthinessScore() 
						+ " qos confidence = " + tw.getQosConfidence());
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
				logger.info("testXId03 trustworthiness score = " + tw.getTrustworthinessScore() +
						" qos confidence = " + tw.getQosConfidence());
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
				logger.info("testXId03 trustworthiness score = " + tw.getTrustworthinessScore()
						+ " qos confidence = " + tw.getQosConfidence());
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
				logger.info("CS (null) trustworthiness score = " + tw.getTrustworthinessScore()
						+ " qos confidence = " + tw.getQosConfidence());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
}