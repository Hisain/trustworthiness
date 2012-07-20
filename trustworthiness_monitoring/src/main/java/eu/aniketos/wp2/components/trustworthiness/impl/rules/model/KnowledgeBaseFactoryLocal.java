package eu.aniketos.wp2.components.trustworthiness.impl.rules.model;

import org.drools.KnowledgeBase;

public interface KnowledgeBaseFactoryLocal {

	/**
	 * returns cached knowledge base
	 */
	KnowledgeBase getKnowledgeBase();

	/**
	 * returns the KnowledgeBase class
	 */
	Class<KnowledgeBase> getKnowledgeBaseType();

}