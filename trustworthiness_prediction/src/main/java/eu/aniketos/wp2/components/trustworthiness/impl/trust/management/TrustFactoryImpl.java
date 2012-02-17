package eu.aniketos.wp2.components.trustworthiness.impl.trust.management;

import java.util.UUID;

import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Service;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class TrustFactoryImpl implements TrustFactory {

  public Atomic createService(String serviceId) {
    Atomic service = new Atomic(serviceId);    
   
   // Score score = new ScoreImpl(); 
    //service.setScore(score);
    return service;
   }

  public Score createScore(Service service) {
    Score score = new Score();
    score.setService(service);
    //id should be shared with member table
    score.setId(UUID.randomUUID().toString());
   
    return score; 
  }

public Composite createComposite(String serviceId) {
	Composite service = new Composite(serviceId);
	
	return service;
}
  

}
