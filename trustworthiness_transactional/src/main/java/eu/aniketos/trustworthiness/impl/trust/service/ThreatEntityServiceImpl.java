package eu.aniketos.trustworthiness.impl.trust.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import eu.aniketos.trustworthiness.impl.trust.pojo.ThreatLevel;
import eu.aniketos.trustworthiness.trust.dao.ThreatLevelDao;
import eu.aniketos.trustworthiness.trust.service.ThreatEntityService;

/**
 *  data access service for scores
 *  
 * @author Hisain Elshaafi (TSSG)
 *
 */
@Transactional(propagation = Propagation.REQUIRES_NEW,noRollbackFor={Exception.class})
public class ThreatEntityServiceImpl implements ThreatEntityService {
	
	ThreatLevelDao threatLevelDao;
	
	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.service.RatingEntityService#addThreatLevel(eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void addThreatLevel(ThreatLevel threatLevel) {
		threatLevelDao.addThreatLevel(threatLevel);

	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.service.RatingEntityService#updateThreatLevel(eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void updateThreatLevel(ThreatLevel threatLevel) {
		threatLevelDao.updateThreatLevel(threatLevel);

	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.service.RatingEntityService#getThreatLevelsByServiceId(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public List<ThreatLevel> getThreatLevelsByServiceId(String source) {
		return threatLevelDao.getThreatLevelsByServiceId(source);
	}
	
	public ThreatLevel getThreatLevel(String serviceId, String threatLevel) {
		return threatLevelDao.getThreatLevel(serviceId, threatLevel);
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.service.RatingEntityService#deleteThreatLevel(eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void deleteThreatLevel(ThreatLevel threatLevel) {
		threatLevelDao.deleteThreatLevel(threatLevel);

	}
	
	/**
	 * @return score DAO object
	 */
	public ThreatLevelDao getThreatLevelDao() {
		return threatLevelDao;
	}

	/**
	 * @param ratingDao score DAO object
	 */
	public void setThreatLevelDao(ThreatLevelDao threatLevelDao) {
		this.threatLevelDao = threatLevelDao;
	}

	

	


	
}
