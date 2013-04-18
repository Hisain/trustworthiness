package eu.aniketos.wp2.components.trustworthiness.trust.dao;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Trustworthiness;


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
	public abstract void addTrustworthiness(Trustworthiness trustworthiness);
	
	
	/**
	 * @param service composite service object 
	 */
	public abstract void updateTrustworthiness(Trustworthiness trustworthiness);

	/**
	 * @param 
	 * @return
	 */
	public abstract Trustworthiness getTrustworthiness(String serviceId);
	
	
	/**
	 * @param
	 */
	public abstract void deleteTrustworthiness(Trustworthiness trustworthiness);
	
	


}
