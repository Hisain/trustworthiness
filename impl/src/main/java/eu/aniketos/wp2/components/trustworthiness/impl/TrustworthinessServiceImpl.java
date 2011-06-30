package eu.aniketos.wp2.components.trustworthiness.impl;


import eu.aniketos.wp2.components.trustworthiness.TrustworthinessService;

public class TrustworthinessServiceImpl implements TrustworthinessService {

    public double getTrustworthinessPrediction(String serviceInfo){
        return 0.5;
    }
	
    public void receiveNotification(String notification){
        return;
    }

}
