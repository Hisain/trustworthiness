package eu.aniketos.wp2.components.trustworthiness.impl.trust.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import eu.aniketos.wp2.components.trustworthiness.trust.dao.SecPropertyDao;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.SecProperty;

/**
 * Data Access Object for source secPropertys
 * 
 * @author: Hisain Elshaafi
 */
public class SecPropertyDaoImpl  extends JpaDaoSupport implements SecPropertyDao {

	private static Logger logger = Logger.getLogger(SecPropertyDaoImpl.class);

	/**
	 * 
	 */
	public SecPropertyDaoImpl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.SecPropertyDao#addSecProperty(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.SecProperty)
	 */
	public void addThreatLevel(SecProperty secProperty) {

		try {
			getJpaTemplate().persist(secProperty);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("secProperty saved");
			}
		} 
		catch (Exception e) {
			logger.error("addSecProperty: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.SecPropertyDao#updateSecProperty(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.SecProperty)
	 */
	public void updateSecProperty(SecProperty secProperty) {

		try {
			getJpaTemplate().merge(secProperty);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("secProperty saved");
			}
		} 
		catch (Exception e) {
			logger.error("updateSecProperty: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.SecPropertyDao#getSecPropertysByServiceId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<SecProperty> getSecPropertiesByServiceId(final String serviceId) {

		List<SecProperty> serviceSecProperties = new ArrayList<SecProperty>();
		
		List<Object> results = null;
		try {
			//TODO: needs checking
			results = (ArrayList<Object>) getJpaTemplate().find("from SecProperty s where s.service = ?",serviceId);
			getJpaTemplate().flush();
			//em.createQuery("s from SecProperty s, Agent a where s.agent = a and a.name='"+agentName+"'")
		} catch (Exception e) {
			logger.error("loadSecPropertys: " + e.getMessage());
		}
		
		if (results != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("results  " + results.size());
			}

			for (Object result : results) {

				SecProperty secProperty = (SecProperty) result;
				serviceSecProperties.add(secProperty);
			}
		} 
		else {
			logger.warn("query returned null");
		}
		
		if (serviceSecProperties != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("loaded secPropertys " + serviceSecProperties.size());
				logger.debug("found secPropertys");
			}
		} 
				
		return serviceSecProperties;
	}
	
	@SuppressWarnings("unchecked")
	public SecProperty getSecProperty(final String serviceId, final String secProperty) {

		SecProperty serviceSecProperty = null;
		
		List<Object> results = null;
		try {
			//TODO: needs checking
			results = (ArrayList<Object>) getJpaTemplate()
					.find("from SecProperty s where s.service = ? and s.property = ?",serviceId,secProperty);
			getJpaTemplate().flush();
			//em.createQuery("s from SecProperty s, Agent a where s.agent = a and a.name='"+agentName+"'")
		} catch (Exception e) {
			logger.error("loadSecPropertys: " + e.getMessage());
		}
		
		if (results != null) {
			if (results.size()>1){
				logger.warn("more than one score of security property may exist");
			}
			if (logger.isDebugEnabled()) {
				logger.debug("results  " + results.size());
			}

			for (Object result : results) {

				serviceSecProperty = (SecProperty) result;
				break;
			}
		} 
		else {
			logger.warn("query returned null");
		}
		
		if (serviceSecProperty != null) {
			if (logger.isDebugEnabled()) {
				
				logger.debug("found secPropertys");
			}
		} 
				
		return serviceSecProperty;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.dao.SecPropertyDao#deleteSecProperty(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.SecProperty)
	 */
	public void deleteSecProperty(SecProperty secProperty) {

		String serviceId = secProperty.getService().getId();

		logger.info("deleting secProperty from " + serviceId);
		
		try {
			
			secProperty = getJpaTemplate().merge(secProperty);
			getJpaTemplate().remove(secProperty);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("deleted record for secProperty " + secProperty.getId());
			}

		} catch (Exception e) {
			logger.error("deleteSecProperty: " + e.getMessage());
		}

	}

	
}
