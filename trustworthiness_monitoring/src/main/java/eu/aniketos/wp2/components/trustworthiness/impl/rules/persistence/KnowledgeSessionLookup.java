package eu.aniketos.wp2.components.trustworthiness.impl.rules.persistence;

import org.drools.runtime.StatefulKnowledgeSession;

/**
 * knows how to create a new or lookup existing knowledge 
 * session
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface KnowledgeSessionLookup {
  /**
   * creates a new session 
   */
  StatefulKnowledgeSession newSession();
  
  /**
   * loads an existing session 
   */
  //StatefulKnowledgeSession loadSession(int sessionId);
}
