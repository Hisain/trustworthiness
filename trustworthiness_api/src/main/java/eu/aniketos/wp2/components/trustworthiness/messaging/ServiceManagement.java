package eu.aniketos.wp2.components.trustworthiness.messaging;

import java.util.Map;

public interface ServiceManagement {

	abstract public void addService(Map<String, String> serviceData) throws Exception;
	
	abstract public void addService(String serviceId) throws Exception;
	
	abstract public void removeService(String serviceId) throws Exception;
}
