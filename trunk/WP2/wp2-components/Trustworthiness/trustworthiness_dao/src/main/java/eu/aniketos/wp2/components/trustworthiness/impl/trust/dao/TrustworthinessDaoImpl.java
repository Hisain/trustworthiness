package eu.aniketos.wp2.components.trustworthiness.impl.trust.dao;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Trustworthiness;
import eu.aniketos.wp2.components.trustworthiness.trust.dao.TrustworthinessDao;

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

	public void addTrustworthiness(Trustworthiness trustworthiness) {
		try {
			getJpaTemplate().persist(trustworthiness);
			getJpaTemplate().flush();

			logger.debug("addTrustworthiness: trustworthiness saved");
		} catch (Exception e) {
			logger.error("addTrustworthiness: " + e.getMessage());
		}
	}

	public void updateTrustworthiness(Trustworthiness trustworthiness) {
		try {
			getJpaTemplate().merge(trustworthiness);
			getJpaTemplate().flush();

			logger.debug("updateTrustworthiness: trustworthiness saved");
		} catch (Exception e) {
			logger.error("updateTrustworthiness: " + e.getMessage());
		}
	}

	public Trustworthiness getTrustworthiness(String id) {

		Trustworthiness trustworthiness = null;

		try {
			trustworthiness = (Trustworthiness) getJpaTemplate().getReference(
					Trustworthiness.class, id);
			getJpaTemplate().flush();

		} catch (EntityNotFoundException enf) {
			logger.warn("getTrustworthiness: " + enf.getMessage());
		} catch (Exception e) {

			logger.error("getTrustworthiness: " + e.getMessage());
		}
		if (trustworthiness != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getTrustworthiness: found service: " + id);
			}
		} else {
			logger.debug("getTrustworthiness: service " + id + " not found");
		}

		return trustworthiness;
	}

	public void deleteTrustworthiness(Trustworthiness trustworthiness) {

		String serviceName = trustworthiness.getId();

		try {

			trustworthiness = getJpaTemplate().merge(trustworthiness);
			getJpaTemplate().remove(trustworthiness);
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
