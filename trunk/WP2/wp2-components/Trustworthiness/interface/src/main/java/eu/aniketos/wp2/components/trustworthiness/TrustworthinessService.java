package eu.aniketos.wp2.components.trustworthiness;


public interface TrustworthinessService {

    public double getTrustworthinessPrediction(String serviceInfo);
	
    public void receiveNotification(String notification);
}
