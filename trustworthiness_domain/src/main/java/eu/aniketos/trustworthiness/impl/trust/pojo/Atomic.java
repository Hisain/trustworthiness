package eu.aniketos.trustworthiness.impl.trust.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@DiscriminatorValue("atomic")
public class Atomic extends Service {

	private static final long serialVersionUID = 6669239437846743354L;
	
	/**
	 * 
	 */
	public Atomic() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param serviceId
	 */
	public Atomic(String serviceId) {
		this.setId(serviceId);
	}

	

	

}
