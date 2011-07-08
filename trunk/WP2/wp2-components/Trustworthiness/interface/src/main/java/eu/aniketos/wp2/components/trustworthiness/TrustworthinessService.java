package eu.aniketos.wp2.components.trustworthiness;

import java.util.List;


public interface TrustworthinessService {

    public double getTrustworthinessPrediction(String serviceInfo);
	
    public void receiveNotification(String notification);
    
    public void deriveMonitoringRules(String securityContract);
    
    public void receiveQoSUpdate(List<String> metrics);
    
    public void receiveReputationUpdate(double reputation);
}
