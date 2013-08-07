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
package eu.aniketos.trustworthiness.impl.rules.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.drools.KnowledgeBase;
//import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
//import org.drools.SystemEventListener;
//import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.io.Resource;

//note that builder configuration nor knowledge base configuration is supplied
//maybe easy to add as another constructor arguments? via util.properties?

//doesn't support complex resources types like decision table
/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class KnowledgeBaseFactoryService implements
		KnowledgeBaseFactoryManagement {

	private KnowledgeBase knowledgeBase;

	/**
	 * builds the knowledge base and caches it
	 * 
	 * @param resourceMap
	 *            source resources (DRL, RF files ...)
	 * @throws IOException
	 *             in case of problems while reading resources
	 */
	public void create() throws IOException {

		knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();

		KnowledgeBuilder builder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();

		Map<Resource, ResourceType> resourceMap = new HashMap<Resource, ResourceType>();
		/**
		 * TODO: DI + support multiple resources
		 */
		resourceMap.put(ResourceFactory.newClassPathResource("experience.drl"),
				ResourceType.DRL);
		/*
		 * resourceMap.put(ResourceFactory.newClassPathResource("role-based.drl")
		 * , ResourceType.DRL);
		 * resourceMap.put(ResourceFactory.newClassPathResource("referral.drl"),
		 * ResourceType.DRL);
		 */

		for (Entry<Resource, ResourceType> entry : resourceMap.entrySet()) {
			builder.add(ResourceFactory.newInputStreamResource(entry.getKey()
					.getInputStream()), entry.getValue());
		}

		if (builder.hasErrors()) {
			throw new RuntimeException(builder.getErrors().toString());
		}

		/*
		 * KnowledgeBaseConfiguration config = KnowledgeBaseFactory
		 * .newKnowledgeBaseConfiguration(); config.setOption(
		 * EventProcessingOption.STREAM );
		 */

		knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());
	}

	/**
	 * returns cached knowledge base
	 */
	public KnowledgeBase getKnowledgeBase() {
		return this.knowledgeBase;
	}

	/**
	 * returns the KnowledgeBase class
	 */
	public Class<KnowledgeBase> getKnowledgeBaseType() {
		return KnowledgeBase.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.impl.rules.model.KnowledgeBaseFactoryManagement
	 * #destroy()
	 */
	public void destroy() throws Exception {
		// TODO Auto-generated method stub

	}

}
