package eu.aniketos.wp2.components.trustworthiness.impl.rules.persistence;


//import org.drools.impl.EnvironmentFactory;
//import org.drools.marshalling.MarshallerFactory;
//import org.drools.marshalling.ObjectMarshallingStrategy;
//import org.drools.persistence.jpa.JPAKnowledgeService;
//import org.drools.runtime.Environment;
//import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
//import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;

import eu.aniketos.wp2.components.trustworthiness.impl.rules.model.KnowledgeBaseFactoryManagement;

//import org.drools.runtime.process.WorkItemHandler;
//import org.drools.runtime.process.WorkItemManager;

/**
 * works with persistable knowledge sessions
 * NOTE: Persistence removed because of bugs in drools 5.1.0M1
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class JPAKnowledgeSessionLookup implements
    KnowledgeSessionLookup {

	/**TODO: revise
	 * 
	 */
 // @PersistenceUnit(unitName="org.drools.persistence.jpa")
 // private EntityManagerFactory emf;

  private KnowledgeBaseFactoryManagement knowledgeBaseFactory;
  
 // private Environment environment;
 

  public void init() {
   /* environment = EnvironmentFactory.newEnvironment();
    environment.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
    environment.set(
        EnvironmentName.OBJECT_MARSHALLING_STRATEGIES,
        new ObjectMarshallingStrategy[] { MarshallerFactory
            .newSerializeMarshallingStrategy() });*/
  }

  /**
 * @param knowledgeBaseFactory
 */
public JPAKnowledgeSessionLookup(KnowledgeBaseFactoryManagement knowledgeBaseFactory) {
	super();
	this.knowledgeBaseFactory = knowledgeBaseFactory;
}

/* 
 * 
 */
public StatefulKnowledgeSession newSession() {
   /* StatefulKnowledgeSession session = JPAKnowledgeService
        .newStatefulKnowledgeSession(knowledgeBaseFactory.getKnowledgeBase(), null,
            environment);
    registerWorkItemHandlers(session);*/
	  
	  StatefulKnowledgeSession session = knowledgeBaseFactory.getKnowledgeBase().newStatefulKnowledgeSession(); 
	  
	  KnowledgeRuntimeLoggerFactory.newFileLogger(session, "log/drools");
	  //KnowledgeRuntimeLoggerFactory.newConsoleLogger(session);
	  
	  return session;
  }

  //TODO: persistence currently disabled
  /*public StatefulKnowledgeSession loadSession(int sessionId) {
    StatefulKnowledgeSession session = JPAKnowledgeService
        .loadStatefulKnowledgeSession(sessionId,
            knowledgeBaseFactory.getKnowledgeBase(), null, environment);
    //registerWorkItemHandlers(session);
    return session;
  }*/

  /**
   * helper method for registering work item handlers 
   * (they are not persisted) 
   */
//  private void registerWorkItemHandlers(
//      StatefulKnowledgeSession session) {
//    WorkItemManager manager = session.getWorkItemManager();
//    manager.registerWorkItemHandler("Task", ataskHandler);
//   
//  }


  /*public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
    this.knowledgeBase = knowledgeBase;
  }*/
  
  

}
