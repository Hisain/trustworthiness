package eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.DiscriminatorValue;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OrderBy;

@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@DiscriminatorValue("composite")
public class Composite extends Service {

	private static final long serialVersionUID = -1915945322854410539L;
	
	private Set<Atomic>componentServices = new HashSet<Atomic>();
	
	public Composite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Composite(String serviceId) {
		this.setId(serviceId);	
	}
	
	@ManyToMany(fetch = FetchType.EAGER)//, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "LNK_SERVICE_COMPOSITE", joinColumns = @JoinColumn(name = "composite_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "atomic_id", referencedColumnName = "id"))
	@OrderBy("id")
	public Set<Atomic> getComponentServices() {
		return componentServices;
	}

	public void setComponentServices(Set<Atomic> componentServices) {
		this.componentServices = componentServices;
	}

}
