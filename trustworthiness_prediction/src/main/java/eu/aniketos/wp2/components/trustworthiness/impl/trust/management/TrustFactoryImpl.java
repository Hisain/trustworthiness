package eu.aniketos.wp2.components.trustworthiness.impl.trust.management;

import java.util.UUID;

import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.QoSMetric;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.SecProperty;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Service;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class TrustFactoryImpl implements TrustFactory {

  /* (non-Javadoc)
 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory#createService(java.lang.String)
 */
public Atomic createService(String serviceId) {
    Atomic service = new Atomic(serviceId);    
   
    return service;
   }

  /* (non-Javadoc)
 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory#createRating(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Service)
 */
public Rating createReputationRating(Service service) {
    Rating rating = new Rating();
    rating.setService(service);
    //id should be shared with member table
    rating.setId(UUID.randomUUID().toString());
   
    return rating; 
  }

/* (non-Javadoc)
 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory#createComposite(java.lang.String)
 */
public Composite createComposite(String serviceId) {
	Composite service = new Composite(serviceId);
	
	return service;
}

public SecProperty createSecPropertyRating(Service service) {
	SecProperty sec = new SecProperty();
	sec.setService(service);
	sec.setId(UUID.randomUUID().toString());
	return sec;
}

public QoSMetric createQoSRating(Service service) {
	QoSMetric metric = new QoSMetric();
	    metric.setService(service);
	    //id should be shared with member table
	    metric.setId(UUID.randomUUID().toString());
	   
	    return metric;
}
  

}
