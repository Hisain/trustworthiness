package eu.aniketos.wp2.components.trustworthiness.trust.dao;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.TrustworthinessEntity;


/**
 * Data Access Object interface for members
 *
 * @author: Hisain Elshaafi
 */
public interface TrustworthinessDao {
		
	/**
	 * add 
	 * @param 
	 */
	public abstract void addTrustworthiness(TrustworthinessEntity trustworthinessEntity);
	
	
	/**
	 * @param service composite service object 
	 */
	public abstract void updateTrustworthiness(TrustworthinessEntity trustworthinessEntity);

	/**
	 * @param 
	 * @return
	 */
	public abstract TrustworthinessEntity getTrustworthiness(String serviceId);
	
	
	/**
	 * @param
	 */
	public abstract void deleteTrustworthiness(TrustworthinessEntity trustworthinessEntity);
	
	


}
