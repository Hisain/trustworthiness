package eu.aniketos.wp2.components.trustworthiness.trust.service;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Trustworthiness;

/**
 * data access service for trustworthiness
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface TrustworthinessEntityService {

	/**
	 * @param trustworthiness
	 */
	public abstract void addTrustworthiness(Trustworthiness trustworthiness);

	/**
	 * @param trustworthiness
	 */
	public abstract void updateTrustworthiness(Trustworthiness trustworthiness);

	/**
	 * @param source
	 * @return
	 */
	public abstract Trustworthiness getTrustworthiness(String source);

	/**
	 * @param trustworthiness
	 */
	public abstract void deleteTrustworthiness(Trustworthiness trustworthiness);

}
