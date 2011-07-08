package eu.aniketos.wp2.components.trustworthiness.impl;


import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.TrustworthinessService;

public class TrustworthinessServiceImpl implements TrustworthinessService {

    public double getTrustworthinessPrediction(String serviceInfo){
        return 0.5;
    }
	
    public void receiveNotification(String notification){
        return;
    }
    
    public void deriveMonitoringRules(String securityContract){
    	return;
    }
    
    public void receiveQoSUpdate(List<String> metrics){
    	return;
    }
    
    public void receiveReputationUpdate(double reputation){
    	return;
    }

}
