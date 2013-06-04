package eu.aniketos.trustworthiness.impl.rules.model;

import java.io.IOException;

import org.drools.KnowledgeBase;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface KnowledgeBaseFactoryManagement {

	/**
	 * builds the knowledge base and caches it
	 * @param resourceMap source resources (DRL, RF files ...)
	 * @throws IOException in case of problems while reading 
	 *          resources
	 */
	void create() throws Exception;

	/**
	 * returns cached knowledge base
	 */
	//@Override
	KnowledgeBase getKnowledgeBase();

	/**
	 * returns the KnowledgeBase class
	 */
	//@Override
	Class<KnowledgeBase> getKnowledgeBaseType();

	void destroy() throws Exception;
}