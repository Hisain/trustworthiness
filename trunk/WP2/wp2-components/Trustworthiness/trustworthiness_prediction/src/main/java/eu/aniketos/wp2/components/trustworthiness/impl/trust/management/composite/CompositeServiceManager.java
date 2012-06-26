package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.composite;

import java.util.Set;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.management.TrustFactoryImpl;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class CompositeServiceManager {

	

	Composite compositeService;
	ServiceEntityService serviceEntityService;
	
	/**
	 * @param service
	 * @param serviceEntityService
	 */
	public CompositeServiceManager(Composite service, ServiceEntityService serviceEntityService) {
		this.compositeService = service;
		this.serviceEntityService = serviceEntityService;
	}

	/**
	 * @return
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * @param sEntityService
	 */
	public void setServiceEntityService(ServiceEntityService sEntityService) {
		this.serviceEntityService = sEntityService;
	}

	/**
	 * @return
	 */
	public Composite getCompositeService() {
		return compositeService;
	}

	/**
	 * @param service
	 */
	public void setCompositeService(Composite service) {
		this.compositeService = service;
	}

	/**
	 * @param serviceId
	 */
	public void createCompositeService(String serviceId){
		TrustFactory tf = new TrustFactoryImpl();
		compositeService =  tf.createComposite(serviceId);
		serviceEntityService.addComposite(compositeService);
	}
	
	/**
	 * @param s
	 */
	public void addComponentService(Atomic s){
		
		Set<Atomic>cs = compositeService.getComponentServices();
		
		cs.add(s);
		compositeService.setComponentServices(cs);
		serviceEntityService.updateComposite(compositeService);
	}
	
	/**
	 * @param s
	 */
	public void removeComponentService(Atomic s){
		
		Set<Atomic>cs = compositeService.getComponentServices();
		
		cs.remove(s);
		compositeService.setComponentServices(cs);
		serviceEntityService.updateComposite(compositeService);
	}
	
	/**
	 * @param s1
	 * @param s2
	 */
	public void replaceComponentService(Atomic s1, Atomic s2){
			
		Set<Atomic>cs = compositeService.getComponentServices();
		
		cs.remove(s1);
		cs.add(s2);
		
		compositeService.setComponentServices(cs);
		serviceEntityService.updateComposite(compositeService);
	}
	
	
}
