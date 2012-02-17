package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.messaging.QoSMetricsService;
import eu.aniketos.wp2.components.trustworthiness.rules.service.ScoreUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class QoSMetricsServiceImpl implements QoSMetricsService {
	private static Logger logger = Logger.getLogger(QoSMetricsService.class);

	private ConfigurationManagement config;

	ScoreUpdate scoreUpdate;

	private ServiceEntityService serviceEntityService;

	public void receiveMetrics(Map<String, String> metric) throws Exception {

		if (metric == null
				|| metric.size() == 0
				|| !metric.containsKey("serviceId")
				|| metric.get("serviceId") == null
				|| metric.get("serviceId") == ""
				|| !metric.containsKey("property")
				|| metric.get("property") == null
				|| metric.get("property") == ""
				|| !metric.containsKey("type")
				|| metric.get("type") == null
				|| metric.get("type") == ""
				|| (metric.containsKey("subproperty") && (metric
						.get("subproperty") == null || metric
						.get("subproperty") == ""))) {
			logger.warn("received metric contains null or empty data");
			throw new Exception("received metric contains null or empty data");

		} else {

			scoreUpdate.updateScore(metric);
		}
	}

	public void setConfig(ConfigurationManagement config) {
		this.config = config;
	}

	public ConfigurationManagement getConfig() {
		return config;
	}

	public void setServiceEntityService(
			ServiceEntityService serviceEntityService) {
		this.serviceEntityService = serviceEntityService;
	}

	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	public ScoreUpdate getScoreUpdate() {
		return scoreUpdate;
	}

	public void setScoreUpdate(ScoreUpdate scoreUpdate) {
		this.scoreUpdate = scoreUpdate;
	}

}
