/**
 * Copyright (c) 2013, Waterford Institute of Technology
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    - Neither the name of Waterford Institute of Technology nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *      
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
package eu.aniketos.trustworthiness.trust.service;

import java.util.List;

import eu.aniketos.trustworthiness.impl.trust.pojo.ThreatLevel;

/**
 * data access service for scores
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface ThreatEntityService {

	/**
	 * @param score
	 *            Rating object to add
	 */
	public abstract void addThreatLevel(ThreatLevel level);

	/**
	 * @param score
	 *            Rating object to update
	 */
	public abstract void updateThreatLevel(ThreatLevel level);

	/**
	 * @param serviceId
	 *            String service id
	 * @return list of existing scores for service
	 */
	public abstract List<ThreatLevel> getThreatLevelsByServiceId(
			final String serviceId);

	public abstract ThreatLevel getThreatLevel(String serviceId, String property);

	/**
	 * @param score
	 *            Rating object to delete
	 */
	public abstract void deleteThreatLevel(ThreatLevel level);

}
