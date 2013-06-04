package eu.aniketos.trustworthiness.impl.rules.model;

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