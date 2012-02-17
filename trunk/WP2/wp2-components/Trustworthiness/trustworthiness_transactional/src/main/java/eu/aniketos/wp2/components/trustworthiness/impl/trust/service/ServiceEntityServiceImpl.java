package eu.aniketos.wp2.components.trustworthiness.impl.trust.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
@Transactional(propagation = Propagation.REQUIRED,noRollbackFor={Exception.class})
public class ServiceEntityServiceImpl implements ServiceEntityService {

	ServiceDao serviceDao;
	
	public void addAtomic(Atomic service) {
		serviceDao.addAtomic(service);

	}

	public void updateAtomic(Atomic service) {
		serviceDao.updateAtomic(service);

	}

	@Transactional(readOnly=true)
	public Atomic getAtomic(String source) {
		return serviceDao.getAtomic(source);
	}

	@Transactional(readOnly=true)
	public List<Atomic> getAllAtomics() {
		return serviceDao.getAllAtomics();
	}

	public void deleteAtomic(Atomic service) {
		 serviceDao.deleteAtomic(service);

	}

	@Transactional(readOnly=true)
	public List<String> getAllAtomicNames() {
		return serviceDao.getAllAtomicNames();
	}

	public ServiceDao getServiceDao() {
		return serviceDao;
	}

	public void setServiceDao(ServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}

	public void addComposite(Composite service) {
		serviceDao.addComposite(service);		
	}

	public void updateComposite(Composite service) {
		serviceDao.updateComposite(service);		
	}

	@Transactional(readOnly=true)
	public Composite getComposite(String id) {
		return serviceDao.getComposite(id);
	}

	public void deleteComposite(Composite service) {
		serviceDao.deleteComposite(service);
	}

	
}
