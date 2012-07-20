package eu.aniketos.wp2.components.trustworthiness.mock.trust.management.atomic;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.messaging.ICompositeTrustworthinessPrediction;
import eu.aniketos.wp2.components.trustworthiness.messaging.ITrustworthinessPrediction;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

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
			twPrediction.getTrustworthiness("id00");
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
			twPrediction.getTrustworthiness("id07");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		try {
			twPrediction.getTrustworthiness("id08");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		Set<String> componentServices = new HashSet<String>();
		componentServices.add("id05");
		componentServices.add("id07");
		componentServices.add("id08");

		Trustworthiness tw = null;
		try {
			tw = ctwPrediction.getCompositeTrustworthiness("xid02",
					componentServices);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if (tw != null) {
			logger.info("Trustworthiness score = " + tw.getScore());
			logger.info("Trustworthiness confidence = " + tw.getConfidence());
		} else {
			logger.info("null was returned for trustworthiness request");
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		logger.info("send empty composite service");
		componentServices = new HashSet<String>();
		try {
			tw = ctwPrediction.getCompositeTrustworthiness("xid03",
					componentServices);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if (tw == null) {
			logger.info("Trustworthiness = null");
		} else {
			logger.info("Trustworthiness score = " + tw.getScore());
			logger.info("Trustworthiness confidence = " + tw.getConfidence());
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		logger.info("send null composite service");
		componentServices = null;
		try {
			tw = ctwPrediction.getCompositeTrustworthiness("xid03",
					componentServices);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if (tw == null) {
			logger.info("Trustworthiness = null");
		} else {
			logger.info("Trustworthiness score = " + tw.getScore());
			logger.info("Trustworthiness confidence = " + tw.getConfidence());
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		logger.info("send null composite service 2");
		componentServices = new HashSet<String>();
		componentServices.add("id05");
		componentServices.add("id07");
		componentServices.add("id08");

		try {
			tw = ctwPrediction.getCompositeTrustworthiness(null,
					componentServices);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if (tw == null) {
			logger.info("Trustworthiness = null");
		} else {
			logger.info("Trustworthiness score = " + tw.getScore());
			logger.info("Trustworthiness confidence = " + tw.getConfidence());
		}
	}
}
