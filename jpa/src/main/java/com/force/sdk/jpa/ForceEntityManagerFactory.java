/**
 * Copyright (c) 2011, salesforce.com, inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice, this list of conditions and the
 *    following disclaimer.
 *
 *    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
 *    the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 *    Neither the name of salesforce.com, inc. nor the names of its contributors may be used to endorse or
 *    promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.force.sdk.jpa;

import java.util.Map;

import javax.jdo.PersistenceManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContextType;
import javax.persistence.spi.PersistenceUnitInfo;

import org.datanucleus.NucleusContext;
import org.datanucleus.api.jpa.JPAEntityManager;
import org.datanucleus.api.jpa.JPAEntityManagerFactory;

/**
 * 
 * Factory for creating the ForceEntityManager.
 *
 * @author Fiaz Hossain
 */
public class ForceEntityManagerFactory extends JPAEntityManagerFactory {

    /**
     * Creates an entity manager factory with the given persistence unit info and property overrides.
     * 
     * @param unitInfo the persistence unit info for this application
     * @param overridingProps a map of properties that should override the properties in the persistence unit
     */
    public ForceEntityManagerFactory(PersistenceUnitInfo unitInfo, Map overridingProps) {
        super(unitInfo, overridingProps);
    }

    /**
     * Creates an entity manager factory via the unit name.
     * 
     * @param unitName  the name of the persistence unit to use
     * @param overridingProps  a map of properties that should override the properties in the persistence unit
     */
    public ForceEntityManagerFactory(String unitName, Map overridingProps) {
        super(unitName, overridingProps);
    }

    @Override
    protected EntityManager newEntityManager(NucleusContext nucleusCtx, PersistenceContextType contextType) {
        return new ForceEntityManager(this, nucleusCtx, contextType);
    }
}
