package eu.aniketos.trustworthiness.rules.model.event;

import eu.aniketos.trustworthiness.ext.rules.model.event.ConsumerRatingEvent;

public interface RuleConsumerRatingEvent extends ConsumerRatingEvent {

	/**
	 * @return
	 */
	public abstract String getContractValue();

	/**
	 * @param contractValue
	 */
	public abstract void setContractValue(String contractValue);

	/**
	 * @param type
	 */
	public abstract void setType(String type);

	/**
	 * @return
	 */
	public abstract String getType();

	/**
	 * @param limit
	 */
	public abstract void setLimit(String limit);

	/**
	 * @return
	 */
	public abstract String getLimit();
}
