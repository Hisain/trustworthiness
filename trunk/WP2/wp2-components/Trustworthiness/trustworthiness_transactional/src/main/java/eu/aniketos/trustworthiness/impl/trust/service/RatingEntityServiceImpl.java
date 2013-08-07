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
 * documentation and/or other materials provided with the distribution.
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
package eu.aniketos.trustworthiness.impl.trust.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import eu.aniketos.trustworthiness.impl.trust.pojo.Rating;
import eu.aniketos.trustworthiness.trust.dao.RatingDao;
import eu.aniketos.trustworthiness.trust.service.RatingEntityService;

/**
 * data access service for ratings
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = { Exception.class })
public class RatingEntityServiceImpl implements RatingEntityService {

	RatingDao ratingDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.trust.service.RatingEntityService#addRating
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void addRating(Rating rating) {
		ratingDao.addRating(rating);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.trust.service.RatingEntityService#updateRating
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
	 */
	public void updateRating(Rating rating) {
		ratingDao.updateRating(rating);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.trust.service.RatingEntityService#
	 * getRatingsByServiceId(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public List<Rating> getRatingsByServiceId(String source) {
		return ratingDao.getRatingsByServiceId(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.trust.service.RatingEntityService#deleteRating
	 * (eu.aniketos.trustworthiness.impl.trust.pojo.Rating)
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
	 * @param ratingDao
	 *            rating DAO object
	 */
	public void setRatingDao(RatingDao ratingDao) {
		this.ratingDao = ratingDao;
	}

}
