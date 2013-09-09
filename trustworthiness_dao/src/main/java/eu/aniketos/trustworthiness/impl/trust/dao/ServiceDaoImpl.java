/**
 * Copyright (c) 2013, Waterford Institute of Technology
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    - Neither the name of Waterford Institute of Technology nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *      
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL WATERFORD INSTITUTE OF TECHNOLOGY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.aniketos.trustworthiness.impl.trust.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import eu.aniketos.trustworthiness.trust.dao.ServiceDao;
import eu.aniketos.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.trustworthiness.impl.trust.pojo.Atomic;

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
	 * @see eu.aniketos.trustworthiness.trust.dao.ServiceDao#addAtomic
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Atomic)
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
	 * @see eu.aniketos.trustworthiness.trust.dao.ServiceDao#addComposite
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Composite)
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
	 * @see eu.aniketos.trustworthiness.trust.dao.ServiceDao#updateAtomic
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Atomic)
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
	 * @see eu.aniketos.trustworthiness.trust.dao.ServiceDao# updateComposite
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Composite)
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
	 * @see eu.aniketos.trustworthiness.trust.dao.ServiceDao#getAllAtomics ()
	 */
	@SuppressWarnings("unchecked")
	public List<Atomic> getAllAtomics() {
		List<Atomic> services = new ArrayList<Atomic>();
		List<Object> results = null;

		try {
			results = (ArrayList<Object>) getJpaTemplate().find("from Service");
			getJpaTemplate().flush();

		} catch (EntityNotFoundException enf) {
			logger.warn("getAtomic: " + enf.getMessage());
		} catch (DataAccessException e) {
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
	 * @see eu.aniketos.trustworthiness.trust.dao.ServiceDao#getAtomic
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

		} catch (DataAccessException e) {

			logger.error("getAtomic: " + e.getMessage());
		}

		if (logger.isDebugEnabled()) {

			if (service != null) {

				logger.debug("getAtomic: found service: " + id);

			} else {
				logger.debug("getAtomic: service " + id + " not found");
			}
		}
		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.trust.dao.ServiceDao#getComposite
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

		} catch (DataAccessException e) {
			logger.error("getComposite: " + e.getMessage());
		}

		if (service != null) {

			if (logger.isDebugEnabled()) {
				logger.debug("getComposite: found service: " + id);
			}

		} else {
			logger.debug("getComposite: service " + id + " not found");
		}

		return service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.trust.dao.ServiceDao#deleteAtomic
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Atomic)
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

		// TODO; delete cascading records

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.trust.dao.ServiceDao# deleteComposite
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Composite)
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

	public boolean isComposite(String serviceId) {

		Composite service = null;
		boolean exists = false;

		try {

			logger.debug("getComposite: attempting to retrieve composite service "
					+ serviceId);

			service = (Composite) getJpaTemplate().getReference(
					Composite.class, serviceId);
			getJpaTemplate().flush();

		} catch (EntityNotFoundException enf) {
			logger.warn("isComposite: " + enf.getMessage());

		} catch (Exception e) {
			logger.error("isComposite: " + e.getMessage());
		}
		if (service != null) {

			exists = true;

			if (logger.isDebugEnabled()) {
				logger.debug("isComposite: found service: " + serviceId);
			}
		} else {
			logger.debug("isComposite: service " + serviceId + " not found");
		}

		return exists;
	}

	public boolean isAtomic(String serviceId) {

		Atomic service = null;
		boolean exists = false;

		try {

			// service = (Atomic)
			// getJpaTemplate().find("Select a from Service a where a.id='"+id+"'");
			service = (Atomic) getJpaTemplate().getReference(Atomic.class,
					serviceId);
			getJpaTemplate().flush();

		} catch (EntityNotFoundException enf) {
			logger.warn("isAtomic: " + enf.getMessage());
		} catch (Exception e) {

			logger.error("isAtomic: " + e.getMessage());
		}
		if (service != null) {

			exists = true;

			if (logger.isDebugEnabled()) {
				logger.debug("isAtomic: found service: " + serviceId);
			}
		} else {
			logger.debug("isAtomic: service " + serviceId + " not found");
		}
		return exists;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.trust.dao.ServiceDao#
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
