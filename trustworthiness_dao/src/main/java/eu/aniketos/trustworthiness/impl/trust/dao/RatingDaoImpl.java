package eu.aniketos.trustworthiness.impl.trust.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import eu.aniketos.trustworthiness.trust.dao.RatingDao;
import eu.aniketos.trustworthiness.impl.trust.pojo.Rating;

/**
 * Data Access Object for source scores
 * 
 * @author: Hisain Elshaafi
 */
public class RatingDaoImpl  extends JpaDaoSupport implements RatingDao {

	private static Logger logger = Logger.getLogger(RatingDaoImpl.class);

	/**
	 * 
	 */
	public RatingDaoImpl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.dao.RatingDao#addRating(eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void addRating(Rating rating) {

		try {
			getJpaTemplate().persist(rating);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("score saved");
			}
		} 
		catch (Exception e) {
			logger.error("addRating: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.dao.RatingDao#updateRating(eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void updateRating(Rating rating) {

		try {
			getJpaTemplate().merge(rating);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("score saved");
			}
		} 
		catch (Exception e) {
			logger.error("updateRating: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.dao.RatingDao#getRatingsByServiceId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Rating> getRatingsByServiceId(final String serviceId) {

		List<Rating> serviceRatings = new ArrayList<Rating>();
		
		List<Object> results = null;
		try {
			//TODO: needs checking
			results = (ArrayList<Object>) getJpaTemplate().find("from Rating s where s.service = ?",serviceId);
			getJpaTemplate().flush();
			//em.createQuery("s from Rating s, Agent a where s.agent = a and a.name='"+agentName+"'")
		} catch (Exception e) {
			logger.error("loadRatings: " + e.getMessage());
		}
		
		if (results != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("results  " + results.size());
			}

			for (Object result : results) {

				Rating rating = (Rating) result;
				serviceRatings.add(rating);
			}
		} 
		else {
			logger.warn("query returned null");
		}
		
		if (serviceRatings != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("loaded scores " + serviceRatings.size());
				logger.debug("found scores");
			}
		} 
				
		return serviceRatings;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.trust.dao.RatingDao#deleteRating(eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void deleteRating(Rating rating) {

		String serviceId = rating.getService().getId();

		logger.info("deleting score from " + serviceId);
		
		try {
			
			rating = getJpaTemplate().merge(rating);
			getJpaTemplate().remove(rating);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("deleted record for score " + rating.getId());
			}

		} catch (Exception e) {
			logger.error("deleteRating: " + e.getMessage());
		}

	}

	


}
