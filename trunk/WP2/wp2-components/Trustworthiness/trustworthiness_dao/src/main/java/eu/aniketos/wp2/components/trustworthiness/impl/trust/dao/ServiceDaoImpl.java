package eu.aniketos.wp2.components.trustworthiness.impl.trust.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;

/**
 * Data Access Object for source members
 * 
 * @author: Hisain Elshaafi
 */
public class ServiceDaoImpl extends JpaDaoSupport implements ServiceDao {

	private static Logger logger = Logger.getLogger(ServiceDaoImpl.class);

	/**
	 * 
	 */
	public ServiceDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao#addAtomic
	 * (eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic)
	 */
	public void addAtomic(Atomic service) {

		try {
			getJpaTemplate().persist(service);
			getJpaTemplate().flush();

			// services.put(service.getName(), service);
			logger.debug("addAtomic: service saved");
		} catch (Exception e) {
			logger.error("addAtomic: " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao#addComposite
	 * (eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite)
	 */
	public void addComposite(Composite service) {

		try {
			getJpaTemplate().persist(service);
			getJpaTemplate().flush();

			// services.put(service.getName(), service);
			logger.debug("addComposite: service saved");
		} catch (Exception e) {
			logger.error("addComposite: " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao#updateAtomic
	 * (eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic)
	 */
	public void updateAtomic(Atomic service) {

		try {
			getJpaTemplate().merge(service);
			getJpaTemplate().flush();

			// services.put(service.getName(), service);
			logger.debug("updateAtomic: service saved");
		} catch (Exception e) {
			logger.error("updateAtomic: " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao#
	 * updateComposite
	 * (eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite)
	 */
	public void updateComposite(Composite service) {

		try {
			getJpaTemplate().merge(service);
			getJpaTemplate().flush();

			// services.put(service.getName(), service);
			logger.debug("updateComposite: service saved");
		} catch (Exception e) {
			logger.error("updateComposite: " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao#getAllAtomics
	 * ()
	 */
	@SuppressWarnings("unchecked")
	public List<Atomic> getAllAtomics() {
		List<Atomic> services = new ArrayList<Atomic>();
		List<Object> results = null;

		try {
			results = (ArrayList<Object>) getJpaTemplate().find("from Service");
			getJpaTemplate().flush();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if (results != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("results  " + results.size());
			}

			for (Object result : results) {
				Atomic service = (Atomic) result;
				services.add(service);
			}
		} else {
			logger.debug("getAllAtomics: query returned null");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getAllAtomics: loaded services " + services.size());
		}
		return services;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao#getAtomic
	 * (java.lang.String)
	 */
	public Atomic getAtomic(final String id) {
		Atomic service = null;
		try {

			// service = (Atomic)
			// getJpaTemplate().find("Select a from Service a where a.id='"+id+"'");
			service = (Atomic) getJpaTemplate().getReference(Atomic.class, id);
			getJpaTemplate().flush();

		} catch (EntityNotFoundException enf) {
			logger.warn("getAtomic: " + enf.getMessage());
		} catch (Exception e) {

			logger.error("getAtomic: " + e.getMessage());
		}
		if (service != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getAtomic: found service: " + service.getId());
			}
		} else {
			logger.debug("getAtomic: no service found");
		}
		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao#getComposite
	 * (java.lang.String)
	 */
	public Composite getComposite(final String id) {
		Composite service = null;
		try {
			logger.debug("getComposite: attempting to retrieve composite service "
					+ id);
			// service = (Composite)
			// getJpaTemplate().find("Select a from Service a where a.id='"+id+"'");
			service = (Composite) getJpaTemplate().getReference(
					Composite.class, id);
			getJpaTemplate().flush();

		} catch (EntityNotFoundException enf) {
			logger.warn("getComposite: " + enf.getMessage());

		} catch (Exception e) {
			logger.error("getComposite: " + e.getMessage());
		}
		if (service != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getComposite: found service: " + service.getId());
			}
		} else {
			logger.debug("getComposite: no service found");
		}
		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao#deleteAtomic
	 * (eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic)
	 */
	public void deleteAtomic(Atomic service) {
		String serviceName = service.getId();

		try {

			service = getJpaTemplate().merge(service);
			getJpaTemplate().remove(service);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("deleteAtomic: deleted record for service "
						+ serviceName);
			}
		} catch (Exception e) {
			logger.error("deleteAtomic: " + e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao#
	 * deleteComposite
	 * (eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite)
	 */
	public void deleteComposite(Composite service) {
		String serviceName = service.getId();

		try {

			service = getJpaTemplate().merge(service);
			getJpaTemplate().remove(service);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("deleteComposite: deleted record for service "
						+ serviceName);
			}
		} catch (Exception e) {
			logger.error("deleteComposite: " + e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.ServiceDao#
	 * getAllAtomicNames()
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllAtomicNames() {

		List<String> serviceNames = new ArrayList<String>();
		List<Object> results = null;

		try {
			results = (ArrayList<Object>) getJpaTemplate().find(
					"select name from Service");
			getJpaTemplate().flush();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if (results != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllAtomicNames: results  " + results.size());
			}

			for (Object result : results) {
				String serviceName = (String) result;
				serviceNames.add(serviceName);
			}
		} else {
			logger.debug("getAllAtomicNames: query returned null");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getAllAtomicNames: loaded services "
					+ serviceNames.size());
		}
		return serviceNames;
	}
}
