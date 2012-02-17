package eu.aniketos.wp2.components.trustworthiness.trust.dao;

import java.util.List;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;


/**
 * Data Access Object interface for members
 *
 * @author: Hisain Elshaafi
 */
public interface ServiceDao {
	
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
