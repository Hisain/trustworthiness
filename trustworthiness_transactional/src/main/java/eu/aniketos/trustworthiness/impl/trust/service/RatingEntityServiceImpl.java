package eu.aniketos.trustworthiness.impl.trust.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import eu.aniketos.trustworthiness.impl.trust.pojo.Rating;
import eu.aniketos.trustworthiness.trust.dao.RatingDao;
import eu.aniketos.trustworthiness.trust.service.RatingEntityService;

/**
 *  data access service for ratings
 *  
 * @author Hisain Elshaafi (TSSG)
 *
 */
@Transactional(propagation = Propagation.REQUIRES_NEW,noRollbackFor={Exception.class})
public class RatingEntityServiceImpl implements RatingEntityService {
	
	RatingDao ratingDao;
	
	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.service.RatingEntityService#addRating(eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void addRating(Rating rating) {
		ratingDao.addRating(rating);

	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.service.RatingEntityService#updateRating(eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void updateRating(Rating rating) {
		ratingDao.updateRating(rating);

	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.service.RatingEntityService#getRatingsByServiceId(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public List<Rating> getRatingsByServiceId(String source) {
		return ratingDao.getRatingsByServiceId(source);
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.service.RatingEntityService#deleteRating(eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void deleteRating(Rating rating) {
		ratingDao.deleteRating(rating);

	}

	/**
	 * @return rating DAO object
	 */
	public RatingDao getRatingDao() {
		return ratingDao;
	}

	/**
	 * @param ratingDao rating DAO object
	 */
	public void setRatingDao(RatingDao ratingDao) {
		this.ratingDao = ratingDao;
	}


	
}
