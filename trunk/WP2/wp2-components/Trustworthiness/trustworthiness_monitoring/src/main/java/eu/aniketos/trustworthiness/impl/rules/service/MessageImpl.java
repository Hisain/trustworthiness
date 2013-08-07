/**
 * Copyright (c) 2013, Waterford Institute of Technology
 * All rights reserved.

 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *    - Neither the name of Waterford Institute of Technology nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.rules.service.Message#getMessageKey()
	 */
	public String getMessageKey() {
		return messageKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.rules.service.Message#getType()
	 */
	public Message.Type getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.rules.service.Message#getContextOrdered()
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