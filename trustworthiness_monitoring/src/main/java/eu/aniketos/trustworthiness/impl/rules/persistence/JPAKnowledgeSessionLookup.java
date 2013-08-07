/**
 * Copyright (c) 2013, Waterford Institute of Technology
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *    - Neither the name of Waterford Institute of Technology nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *      
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL WATERFORD INSTITUTE OF TECHNOLOGY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.aniketos.trustworthiness.impl.rules.persistence;

//import org.drools.impl.EnvironmentFactory;
//import org.drools.marshalling.MarshallerFactory;
//import org.drools.marshalling.ObjectMarshallingStrategy;
//import org.drools.persistence.jpa.JPAKnowledgeService;
//import org.drools.runtime.Environment;
//import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
//import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;

import eu.aniketos.trustworthiness.impl.rules.model.KnowledgeBaseFactoryManagement;

//import org.drools.runtime.process.WorkItemHandler;
//import org.drools.runtime.process.WorkItemManager;

/**
 * works with persistable knowledge sessions NOTE: Persistence removed because
 * of bugs in drools 5.1.0M1
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class JPAKnowledgeSessionLookup implements KnowledgeSessionLookup {

	/**
	 * TODO: revise
	 * 
	 */
	// @PersistenceUnit(unitName="org.drools.persistence.jpa")
	// private EntityManagerFactory emf;

	private KnowledgeBaseFactoryManagement knowledgeBaseFactory;

	// private Environment environment;

	public void init() {
		/*
		 * environment = EnvironmentFactory.newEnvironment();
		 * environment.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
		 * environment.set( EnvironmentName.OBJECT_MARSHALLING_STRATEGIES, new
		 * ObjectMarshallingStrategy[] { MarshallerFactory
		 * .newSerializeMarshallingStrategy() });
		 */
	}

	/**
	 * @param knowledgeBaseFactory
	 */
	public JPAKnowledgeSessionLookup(
			KnowledgeBaseFactoryManagement knowledgeBaseFactory) {
		super();
		this.knowledgeBaseFactory = knowledgeBaseFactory;
	}

	/* 
 * 
 */
	public StatefulKnowledgeSession newSession() {
		/*
		 * StatefulKnowledgeSession session = JPAKnowledgeService
		 * .newStatefulKnowledgeSession(knowledgeBaseFactory.getKnowledgeBase(),
		 * null, environment); registerWorkItemHandlers(session);
		 */

		StatefulKnowledgeSession session = knowledgeBaseFactory
				.getKnowledgeBase().newStatefulKnowledgeSession();

		KnowledgeRuntimeLoggerFactory.newFileLogger(session, "log/drools");
		// KnowledgeRuntimeLoggerFactory.newConsoleLogger(session);

		return session;
	}

	// TODO: persistence currently disabled
	/*
	 * public StatefulKnowledgeSession loadSession(int sessionId) {
	 * StatefulKnowledgeSession session = JPAKnowledgeService
	 * .loadStatefulKnowledgeSession(sessionId,
	 * knowledgeBaseFactory.getKnowledgeBase(), null, environment);
	 * //registerWorkItemHandlers(session); return session; }
	 */

	/**
	 * helper method for registering work item handlers (they are not persisted)
	 */
	// private void registerWorkItemHandlers(
	// StatefulKnowledgeSession session) {
	// WorkItemManager manager = session.getWorkItemManager();
	// manager.registerWorkItemHandler("Task", ataskHandler);
	//
	// }

	/*
	 * public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
	 * this.knowledgeBase = knowledgeBase; }
	 */

}
