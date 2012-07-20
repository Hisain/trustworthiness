package eu.aniketos.wp2.components.trustworthiness.impl.trust.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao;

/**
 *  data access service for an atomic or composite Web service
 *  
 * @author Hisain Elshaafi (TSSG)
 *
 */
@Transactional(propagation = Propagation.REQUIRED,noRollbackFor={Exception.class})
public class ServiceEntityServiceImpl implements ServiceEntityService {

	ServiceDao serviceDao;
	
	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService#addAtomic(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic)
	 */
	public void addAtomic(Atomic service) {
		serviceDao.addAtomic(service);

	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService#updateAtomic(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic)
	 */
	public void updateAtomic(Atomic service) {
		serviceDao.updateAtomic(service);

	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService#getAtomic(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Atomic getAtomic(String source) {
		return serviceDao.getAtomic(source);
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService#getAllAtomics()
	 */
	@Transactional(readOnly=true)
	public List<Atomic> getAllAtomics() {
		return serviceDao.getAllAtomics();
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService#deleteAtomic(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic)
	 */
	public void deleteAtomic(Atomic service) {
		 serviceDao.deleteAtomic(service);

	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService#getAllAtomicNames()
	 */
	@Transactional(readOnly=true)
	public List<String> getAllAtomicNames() {
		return serviceDao.getAllAtomicNames();
	}

	/**
	 * @return Web service DAO
	 */
	public ServiceDao getServiceDao() {
		return serviceDao;
	}

	/**
	 * @param serviceDao Web service DAO
	 */
	public void setServiceDao(ServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService#addComposite(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite)
	 */
	public void addComposite(Composite service) {
		serviceDao.addComposite(service);		
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService#updateComposite(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite)
	 */
	public void updateComposite(Composite service) {
		serviceDao.updateComposite(service);		
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService#getComposite(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Composite getComposite(String id) {
		return serviceDao.getComposite(id);
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService#deleteComposite(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite)
	 */
	public void deleteComposite(Composite service) {
		serviceDao.deleteComposite(service);
	}

	
}
