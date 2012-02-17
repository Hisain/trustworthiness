package eu.aniketos.wp2.components.trustworthiness.trust.service;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ServiceEntityService {
	
public abstract void addAtomic(Atomic service); 
	
	public abstract void addComposite(Composite service);
	
	public abstract void updateAtomic(Atomic service);
	
	public abstract void updateComposite(Composite service);

	public abstract Atomic getAtomic(final String id);
	
	public abstract Composite getComposite(final String id);
	
	public abstract void deleteAtomic(Atomic service);
	
	public abstract void deleteComposite(Composite service);
	
	public abstract List <Atomic> getAllAtomics();
	
	public abstract List <String> getAllAtomicNames();
}
