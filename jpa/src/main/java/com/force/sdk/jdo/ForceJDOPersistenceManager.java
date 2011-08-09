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

package com.force.sdk.jdo;

import com.force.sdk.jpa.ForceObjectManagerImpl;
import org.datanucleus.NucleusContext;
import org.datanucleus.api.jdo.JDOFetchPlan;
import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;

/**
 * 
 * We don't support JDO. Use JPA instead.
 * Extension of the JDOPersistenceManager to provide our own object manager and fetch plan.
 *
 * @author Fiaz Hossain
 */
public class ForceJDOPersistenceManager extends org.datanucleus.api.jdo.JDOPersistenceManager {

    /**
     * 
     * Constructor for instantiating the Force.com object manager.
     * 
     * @param apmf Persistence Manager Factory
     * @param userName Username for the datastore
     * @param password Password for the datastore
     */
    public ForceJDOPersistenceManager(JDOPersistenceManagerFactory apmf, String userName, String password) {
        super(apmf, userName, password);
        this.om = new ForceObjectManagerImpl(apmf.getNucleusContext(), this, userName, password);
        fetchPlan = new JDOFetchPlan(om.getFetchPlan());
        setTransaction(om.getTransaction());
    }

    @Override
    public void setQueryTimeoutMillis(Integer integer) {
        // todo: Auto-generated method stub

    }

    @Override
    public Integer getQueryTimeoutMillis() {
        // todo: Auto-generated method stub
        return null;
    }
}
