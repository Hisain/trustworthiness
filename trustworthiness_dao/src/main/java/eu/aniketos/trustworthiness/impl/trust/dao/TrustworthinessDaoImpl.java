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

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import eu.aniketos.trustworthiness.trust.dao.TrustworthinessDao;
import eu.aniketos.trustworthiness.impl.trust.pojo.TrustworthinessEntity;

/**
 * data access trustworthiness for trustworthiness
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class TrustworthinessDaoImpl extends JpaDaoSupport implements
		TrustworthinessDao {

	private static Logger logger = Logger
			.getLogger(TrustworthinessDaoImpl.class);

	public TrustworthinessDaoImpl() {
		super();
	}

	public void addTrustworthiness(TrustworthinessEntity trustworthinessEntity) {
		try {
			getJpaTemplate().persist(trustworthinessEntity);
			getJpaTemplate().flush();

			logger.debug("addTrustworthiness: trustworthiness saved");
		} catch (DataAccessException e) {
			logger.error("addTrustworthiness: " + e.getMessage());
		}
	}

	public void updateTrustworthiness(
			TrustworthinessEntity trustworthinessEntity) {
		try {
			getJpaTemplate().merge(trustworthinessEntity);
			getJpaTemplate().flush();

			logger.debug("updateTrustworthiness: trustworthiness saved");
		} catch (DataAccessException e) {
			logger.error("updateTrustworthiness: " + e.getMessage());
		}
	}

	public TrustworthinessEntity getTrustworthiness(String id) {

		TrustworthinessEntity trustworthinessEntity = null;

		try {
			trustworthinessEntity = (TrustworthinessEntity) getJpaTemplate()
					.getReference(TrustworthinessEntity.class, id);
			getJpaTemplate().flush();

		} catch (EntityNotFoundException enf) {

			logger.warn("getTrustworthiness: " + enf.getMessage());

		} catch (DataAccessException e) {

			logger.error("getTrustworthiness: " + e.getMessage());
		}

		if (logger.isDebugEnabled()) {

			if (trustworthinessEntity != null) {

				logger.debug("getTrustworthiness: found service: " + id);

			} else {
				logger.debug("getTrustworthiness: service " + id + " not found");
			}
		}
		
		return trustworthinessEntity;
	}

	public void deleteTrustworthiness(
			TrustworthinessEntity trustworthinessEntity) {

		String serviceName = trustworthinessEntity.getId();

		try {

			trustworthinessEntity = getJpaTemplate().merge(
					trustworthinessEntity);
			getJpaTemplate().remove(trustworthinessEntity);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("deleteTrustworthiness: deleted record for trustworthiness "
						+ serviceName);
			}
		} catch (DataAccessException e) {
			logger.error("deleteTrustworthiness: " + e.getMessage());
		}
	}

}
