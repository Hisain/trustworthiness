package eu.aniketos.trustworthiness.impl.trust.dao;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;

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
		} catch (Exception e) {
			logger.error("addTrustworthiness: " + e.getMessage());
		}
	}

	public void updateTrustworthiness(TrustworthinessEntity trustworthinessEntity) {
		try {
			getJpaTemplate().merge(trustworthinessEntity);
			getJpaTemplate().flush();

			logger.debug("updateTrustworthiness: trustworthiness saved");
		} catch (Exception e) {
			logger.error("updateTrustworthiness: " + e.getMessage());
		}
	}

	public TrustworthinessEntity getTrustworthiness(String id) {

		TrustworthinessEntity trustworthinessEntity = null;

		try {
			trustworthinessEntity = (TrustworthinessEntity) getJpaTemplate().getReference(
					TrustworthinessEntity.class, id);
			getJpaTemplate().flush();

		} catch (EntityNotFoundException enf) {
			logger.warn("getTrustworthiness: " + enf.getMessage());
		} catch (Exception e) {

			logger.error("getTrustworthiness: " + e.getMessage());
		}
		if (trustworthinessEntity != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getTrustworthiness: found service: " + id);
			}
		} else {
			logger.debug("getTrustworthiness: service " + id + " not found");
		}

		return trustworthinessEntity;
	}

	public void deleteTrustworthiness(TrustworthinessEntity trustworthinessEntity) {

		String serviceName = trustworthinessEntity.getId();

		try {

			trustworthinessEntity = getJpaTemplate().merge(trustworthinessEntity);
			getJpaTemplate().remove(trustworthinessEntity);
			getJpaTemplate().flush();

			if (logger.isDebugEnabled()) {
				logger.debug("deleteTrustworthiness: deleted record for trustworthiness "
						+ serviceName);
			}
		} catch (Exception e) {
			logger.error("deleteTrustworthiness: " + e.getMessage());
		}
	}

}
