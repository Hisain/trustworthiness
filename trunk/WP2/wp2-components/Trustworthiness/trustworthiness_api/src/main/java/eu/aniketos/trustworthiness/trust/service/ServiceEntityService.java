package eu.aniketos.trustworthiness.trust.service;

import java.util.List;

import eu.aniketos.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.trustworthiness.impl.trust.pojo.Composite;

/**
 * data access service for atomic or composite Web services
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface ServiceEntityService {

	/**
	 * add new atomic service to database
	 * 
	 * @param service
	 *            atomic service object
	 */
	public abstract void addAtomic(Atomic service);

	/**
	 * add new composite service to database
	 * 
	 * @param service
	 *            composite service object
	 */
	public abstract void addComposite(Composite service);

	/**
	 * @param service
	 *            atomic service object
	 */
	public abstract void updateAtomic(Atomic service);

	/**
	 * @param service
	 *            composite service object
	 */
	public abstract void updateComposite(Composite service);

	/**
	 * @param id
	 *            service id
	 * @return atomic service object
	 */
	public abstract Atomic getAtomic(final String id);

	/**
	 * @param id
	 *            service id
	 * @return composite service object
	 */
	public abstract Composite getComposite(final String id);

	/**
	 * @param service
	 *            atomic service object
	 */
	public abstract void deleteAtomic(Atomic service);

	/**
	 * @param service
	 *            composite service object
	 */
	public abstract void deleteComposite(Composite service);

	/**
	 * @param serviceId
	 * @return
	 */
	public abstract boolean isComposite(String serviceId);

	/**
	 * @param serviceId
	 * @return
	 */
	public abstract boolean isAtomic(String serviceId);

	/**
	 * @return list of all atomic service objects
	 */
	public abstract List<Atomic> getAllAtomics();

	/**
	 * @return list of all atomic service names
	 */
	public abstract List<String> getAllAtomicNames();

}
