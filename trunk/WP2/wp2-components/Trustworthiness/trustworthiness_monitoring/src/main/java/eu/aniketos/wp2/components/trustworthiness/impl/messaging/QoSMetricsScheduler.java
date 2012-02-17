package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import org.apache.log4j.Logger;
//import org.quartz.Job;

import eu.aniketos.wp2.components.trustworthiness.messaging.QoSMetricsJms;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class QoSMetricsScheduler extends QuartzJobBean
{

	private static Logger logger = Logger.getLogger(QoSMetricsScheduler.class);

	private QoSMetricsJms qosMetrics;
	
	private int timeout;
	  
	  /**
	   * Setter called after the ExampleJob is instantiated
	   * with the value from the JobDetailBean (5)
	   */ 
	  public void setTimeout(int timeout) {
	    this.timeout = timeout;
	  }

	  protected void executeInternal(JobExecutionContext ctx)
			  throws JobExecutionException {

		logger.debug("executed comifinmetricimpl");
		
		qosMetrics.receiveMetrics();
		
	}

}
