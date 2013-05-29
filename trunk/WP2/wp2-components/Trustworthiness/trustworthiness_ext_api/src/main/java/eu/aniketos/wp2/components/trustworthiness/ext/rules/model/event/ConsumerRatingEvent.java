package eu.aniketos.wp2.components.trustworthiness.ext.rules.model.event;

/**
 * Interface for receiving consumer ratings
 * 
 * @author Hisain Elshaafi
 *
 */
public interface ConsumerRatingEvent extends TrustEvent {
	
	
	/**
	 * @return consumer Id
	 */
	public abstract String getConsumerId();
	
	/**
	 * @param consumerId consumer Id
	 */
	public abstract void setConsumerId(String consumerId);
	
	/**
	 * @return transaction Id
	 */
	public abstract String getTransactionId();
	
	/**
	 * @param transactionId transaction Id
	 */
	public abstract void setTransactionId(String transactionId);
	
}
