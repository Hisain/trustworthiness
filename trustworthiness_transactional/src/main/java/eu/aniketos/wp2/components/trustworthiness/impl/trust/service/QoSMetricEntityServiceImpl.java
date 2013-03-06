package eu.aniketos.wp2.components.trustworthiness.impl.trust.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.QoSMetric;
import eu.aniketos.wp2.components.trustworthiness.trust.service.QoSMetricEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.dao.QoSMetricDao;

/**
 *  data access service for ratings
 *  
 * @author Hisain Elshaafi (TSSG)
 *
 */
@Transactional(propagation = Propagation.REQUIRES_NEW,noRollbackFor={Exception.class})
public class QoSMetricEntityServiceImpl implements QoSMetricEntityService {
	
	QoSMetricDao qosDao;
	
	
	public void addMetric(QoSMetric rating) {
		qosDao.addMetric(rating);
	}


	public void updateMetric(QoSMetric rating) {
		qosDao.updateMetric(rating);
	}

	
	@Transactional(readOnly=true)
	public List<QoSMetric> getMetricsByServiceId(String source) {
		return qosDao.getMetricsByServiceId(source);
	}

	public void deleteMetric(QoSMetric rating) {
		qosDao.deleteMetric(rating);
	}

	/**
	 * @return metric DAO object
	 */
	public QoSMetricDao getQosDao() {
		return qosDao;
	}

	/**
	 * @param qosDao metric DAO object
	 */
	public void setQosDao(QoSMetricDao qosDao) {
		this.qosDao = qosDao;
	}


	
}
