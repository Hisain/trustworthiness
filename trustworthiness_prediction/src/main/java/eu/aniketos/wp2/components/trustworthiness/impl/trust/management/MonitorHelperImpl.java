package eu.aniketos.wp2.components.trustworthiness.impl.trust.management;

import eu.aniketos.wp2.components.trustworthiness.rules.service.ScoreUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.management.MonitorHelper;
import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustMonitor;

public class MonitorHelperImpl implements MonitorHelper {

	TrustMonitor trustMonitor;
	 
	public MonitorHelperImpl(TrustMonitor trustMonitor) {
		super();
		this.trustMonitor = trustMonitor;
	}

	public void setupObservers (ScoreUpdate scoreUpdate){
	    	//scoreUpdate.addRemoteObserver(trustMonitor);
	}

}
