package eu.aniketos.trustworthiness.ext.messaging;

import java.util.Map;

public interface ServiceManagement {

	abstract public void addService(Map<String, String> serviceData);
	
	abstract public void addService(String serviceId);
	
	abstract public void removeService(String serviceId);
}
