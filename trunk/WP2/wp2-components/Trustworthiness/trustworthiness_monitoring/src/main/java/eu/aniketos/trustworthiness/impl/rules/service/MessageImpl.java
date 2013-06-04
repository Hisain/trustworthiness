/**
 * 
 */
package eu.aniketos.trustworthiness.impl.rules.service;

import java.io.Serializable;
import java.util.List;

/**
 * @author miba
 * 
 */
// @extract-start 03 54
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
//... other imports

import eu.aniketos.trustworthiness.rules.service.Message;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class MessageImpl implements Message, Serializable {

	private static final long serialVersionUID = 4305479964335028958L;
	private Message.Type type;
	private String messageKey;
	private List<Object> context;

	/**
	 * @param type
	 * @param messageKey
	 * @param context
	 */
	public MessageImpl(Message.Type type, String messageKey,
			List<Object> context) {
		if (type == null || messageKey == null) {
			throw new IllegalArgumentException(
					"Type and messageKey cannot be null");
		}
		this.type = type;
		this.messageKey = messageKey;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.rules.service.Message#getMessageKey()
	 */
	public String getMessageKey() {
		return messageKey;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.rules.service.Message#getType()
	 */
	public Message.Type getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.rules.service.Message#getContextOrdered()
	 */
	public List<Object> getContextOrdered() {
		return context;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof MessageImpl))
			return false;
		MessageImpl castOther = (MessageImpl) other;
		return new EqualsBuilder().append(type, castOther.type)
				.append(messageKey, castOther.messageKey)
				.append(context, castOther.context).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(98587969, 810426655).append(type)
				.append(messageKey).append(context).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("type", type)
				.append("messageKey", messageKey).append("context", context)
				.toString();
	}
}
// @extract-end