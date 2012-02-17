package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.rules.service.ScoreUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.wp2.components.trustworthiness.messaging.QoSMetricsJms;

//import eu.comifin.interfaces.memo.MetricFilter;
//import eu.comifin.interfaces.memo.MetricType;
//import eu.comifin.interfaces.memo.MetricValue;
//import eu.comifin.interfaces.memo.rmi.MetricStore;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class QoSMetricsJmsImpl implements QoSMetricsJms {

	private static Logger logger = Logger.getLogger(QoSMetricsJmsImpl.class);

	private ConfigurationManagement config;

	ScoreUpdate scoreUpdate;

	private ServiceEntityService serviceEntityService;

	// MetricStore metricStore;

	private boolean active = false;

	public void receiveMetrics() {

		/*
		 * try {
		 * 
		 * logger.debug("new run of metrics query");
		 * 
		 * MetricFilter filter = new MetricFilter();
		 * 
		 * logger.debug("lookup rmi"); Registry registry =
		 * LocateRegistry.getRegistry
		 * (config.getConfig().getString("memo_address"),
		 * Integer.parseInt(config.getConfig().getString("memo_port")));
		 * 
		 * metricStore = (MetricStore)
		 * registry.lookup(config.getConfig().getString("memo_server"));
		 * 
		 * logger.debug("returned metricstore");
		 * 
		 * List<String> services = serviceEntityService.getAllServiceNames();
		 * logger.debug("loaded services");
		 * 
		 * for (String service : services) {
		 * 
		 * //1st query sla violations filter.setMemberId(service);
		 * filter.setMetricType(MetricType.SLA_VIOLATION_NUMBER.name());
		 * //considering last 60 secs only Date fromDate = new Date();
		 * fromDate.setTime(System.currentTimeMillis()-60000);
		 * filter.setFromDate(fromDate);
		 * 
		 * Object message = metricStore.getLatestMetricValue(filter); String
		 * property = "sla"; String subproperty = "sla-violation";
		 * 
		 * if (message instanceof MetricValue) {
		 * 
		 * MetricValue metricValue = (MetricValue) message;
		 * 
		 * // String service = metricValue.getComponentId();
		 * logger.debug("metrics received for " + service); Map<String, String>
		 * metricMessage = new HashMap<String, String>();
		 * metricMessage.put("type", "metric"); // TODO: metric type needs work
		 * to align with // properties/subproperties
		 * 
		 * if (logger.isDebugEnabled()){ logger.debug("metric type: " +
		 * metricValue.getMetricValueType()); logger.debug("metric value: " +
		 * metricValue.getValue().toString()); logger.debug("start time: " +
		 * metricValue.getStartTime()); logger.debug("end time: " +
		 * metricValue.getEndTime()); }
		 * 
		 * metricMessage.put("property", property);
		 * metricMessage.put("subproperty", subproperty);
		 * metricMessage.put("service", service); metricMessage.put("metric",
		 * metricValue.getValue().toString());
		 * 
		 * scoreUpdate.updateScore(metricMessage);
		 * 
		 * } else if (message == null) { logger.debug("received null for " +
		 * service); }
		 * 
		 * //2nd query message no.
		 * 
		 * //3rd query bad message no.
		 * 
		 * //4th query uptime
		 * 
		 * //5th query no. of alerts }
		 * 
		 * } catch (Exception e) { logger.error(e.getMessage()); }
		 */
	}

	public void setConfig(ConfigurationManagement config) {
		this.config = config;
	}

	public ConfigurationManagement getConfig() {
		return config;
	}

	public void setServiceEntityService(ServiceEntityService serviceEntityService) {
		this.serviceEntityService = serviceEntityService;
	}

	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

}
