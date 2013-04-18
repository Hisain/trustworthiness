package eu.aniketos.wp2.components.trustworthiness.impl.trust.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.SecProperty;
import eu.aniketos.wp2.components.trustworthiness.trust.service.SecurityEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.dao.SecPropertyDao;

/**
 *  data access service for scores
 *  
 * @author Hisain Elshaafi (TSSG)
 *
 */
@Transactional(propagation = Propagation.REQUIRES_NEW,noRollbackFor={Exception.class})
public class SecurityEntityServiceImpl implements SecurityEntityService {
	
	SecPropertyDao secPropertyDao;
	
	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.RatingEntityService#addSecProperty(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void addSecProperty(SecProperty secProperty) {
		secPropertyDao.addThreatLevel(secProperty);

	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.RatingEntityService#updateSecProperty(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void updateSecProperty(SecProperty secProperty) {
		secPropertyDao.updateSecProperty(secProperty);

	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.RatingEntityService#getSecPropertysByServiceId(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public List<SecProperty> getSecPropertiesByServiceId(String source) {
		return secPropertyDao.getSecPropertiesByServiceId(source);
	}
	
	public SecProperty getSecProperty(String serviceId, String secProperty) {
		return secPropertyDao.getSecProperty(serviceId, secProperty);
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.service.RatingEntityService#deleteSecProperty(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void deleteSecProperty(SecProperty secProperty) {
		secPropertyDao.deleteSecProperty(secProperty);

	}
	
	/**
	 * @return score DAO object
	 */
	public SecPropertyDao getSecPropertyDao() {
		return secPropertyDao;
	}

	/**
	 * @param ratingDao score DAO object
	 */
	public void setSecPropertyDao(SecPropertyDao secPropertyDao) {
		this.secPropertyDao = secPropertyDao;
	}

	

	


	
}
