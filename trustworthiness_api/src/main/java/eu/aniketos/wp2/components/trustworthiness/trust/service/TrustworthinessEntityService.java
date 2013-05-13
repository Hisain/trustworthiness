package eu.aniketos.wp2.components.trustworthiness.trust.service;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.TrustworthinessEntity;

/**
 * data access service for trustworthiness
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface TrustworthinessEntityService {

	/**
	 * @param trustworthinessEntity
	 */
	public abstract void addTrustworthiness(TrustworthinessEntity trustworthinessEntity);

	/**
	 * @param trustworthinessEntity
	 */
	public abstract void updateTrustworthiness(TrustworthinessEntity trustworthinessEntity);

	/**
	 * @param source
	 * @return
	 */
	public abstract TrustworthinessEntity getTrustworthiness(String source);

	/**
	 * @param trustworthinessEntity
	 */
	public abstract void deleteTrustworthiness(TrustworthinessEntity trustworthinessEntity);

}
