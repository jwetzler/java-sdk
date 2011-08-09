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

import com.sforce.soap.partner.sobject.SObject;
import org.datanucleus.NucleusContext;
import org.datanucleus.ObjectManagerImpl;
import org.datanucleus.exceptions.NucleusDataStoreException;
import org.datanucleus.exceptions.NucleusObjectNotFoundException;
import org.datanucleus.exceptions.NucleusOptimisticException;
import org.datanucleus.state.FetchPlanState;
import org.datanucleus.state.StateManager;
import org.datanucleus.store.ObjectProvider;

import java.util.*;

/**
 * 
 * Object manager for the objects being created, updated, or deleted.
 * Special handling for all-or-nothing operations.
 *
 * @author Fiaz Hossain
 */
public class ForceObjectManagerImpl extends ObjectManagerImpl {

    private final boolean allOrNothingEnabled;
    //private boolean inAllOrNothingMode;
    private LinkedHashMap<ObjectProvider, SObject> createObjectList;
    private IdentityHashMap<Object, SObject> pcToSObject;
    private List<SObject> updateObjectList;
    private List<Calendar> versionList;
    private List<String> deleteObjectList;
    
    /**
     * Creates an object manager with datastore credentials.
     * 
     * @param nCtx the Nucleus Context
     * @param owner  the owning persistence manager or entity manager
     * @param userName  the username to the datastore
     * @param password  the password to the datastore
     */
    public ForceObjectManagerImpl(NucleusContext nCtx, Object owner, String userName, String password) {
        super(nCtx, owner, userName, password);
        this.allOrNothingEnabled = nCtx.getPersistenceConfiguration().getBooleanProperty("force.AllOrNothing");
    }

    
    /**
     * Flushes all dirty, new, and deleted instances to the
     * datastore. It has no effect if a transaction is not active.
     *<p>
     * If a datastore transaction is active, this method synchronizes the cache with
     * the datastore and reports any exceptions. If an optimistic transaction is
     * active, this method obtains a datastore connection and synchronizes the
     * cache with the datastore using this connection. The connection obtained
     * by this method is held until the end of the transaction.
     * 
     * @param flushToDatastore Whether to ensure any changes reach the datastore.
     *     Otherwise, they will be flushed to the datastore manager which
     *     determines the opportune moment to actually flush them to the datastore
     */
    @Override
    public synchronized void flushInternal(boolean flushToDatastore) {
        if (flushToDatastore && allOrNothingEnabled) {
            //inAllOrNothingMode = true;

        ((ForceStoreManager) getStoreManager()).setInAllOrNothingMode(true);
            try {
                super.flushInternal(flushToDatastore);
                if (createObjectList != null) {
                    ((ForcePersistenceHandler) getStoreManager().getPersistenceHandler()).createObjects(createObjectList.values(),
                            createObjectList.keySet(), this);
                }
                if (updateObjectList != null) {
                    ((ForcePersistenceHandler) getStoreManager().getPersistenceHandler())
                        .updateObjects(updateObjectList.toArray(new SObject[updateObjectList.size()]),
                            versionList.toArray(new Calendar[versionList.size()]), this);
                }
                if (deleteObjectList != null) {
                    ((ForcePersistenceHandler) getStoreManager().getPersistenceHandler())
                        .deleteObjects(deleteObjectList.toArray(new String[deleteObjectList.size()]), this);
                }
            } catch (NucleusOptimisticException noe) {
                throw new NucleusOptimisticException(LOCALISER.msg("010031"), noe.getFailedObject());
            } finally {
        ((ForceStoreManager) getStoreManager()).setInAllOrNothingMode(false);
                createObjectList = null;
                updateObjectList = null;
                versionList = null;
                deleteObjectList = null;
            }
        } else {
            super.flushInternal(flushToDatastore);
        }
    }

    /**
     * Retrieves the Force.com object (SObject) for the given parent.
     * 
     * @param parent Object
     * @return the Force.com object (SObject) corresponding to the given parent
     */
    public synchronized SObject getParentSObject(Object parent) {
        SObject parentSObject;
        if (pcToSObject == null || (parentSObject = pcToSObject.get(parent)) == null) {
            throw new NucleusDataStoreException("Parent entity has not been saved");
        }
        return parentSObject;
    }
    
    /**
     * Adds an object to the current list of entities to be created.
     * 
     * @param object  the object to be created
     * @param op  the object provider
     */
    public synchronized void addToCreateList(SObject object, ObjectProvider op) {
        if (createObjectList == null) {
            createObjectList = new LinkedHashMap<ObjectProvider, SObject>();
            pcToSObject = new IdentityHashMap<Object, SObject>();
        }
        createObjectList.put(op, object);
        pcToSObject.put(op.getObject(), object);
    }
    
    /**
     * Adds an object to the current list of entities to be updated.
     * 
     * @param object  the object to update (complete with updated fields) 
     * @param version  this should be the time of modification. Pass in a version if we're checking
     *                  if-modified-before headers for optimistic transactions
     */
    public synchronized void addToUpdateList(SObject object, Calendar version) {
        if (updateObjectList == null) {
            updateObjectList = new ArrayList<SObject>();
            versionList = new ArrayList<Calendar>();
        }
        updateObjectList.add(object);
        if (version != null) {
            versionList.add(version);
        }
    }
    
    /**
     * Adds an entity to the current list of entities to be deleted.
     * 
     * @param id the id of the entity to delete
     */
    public synchronized void addToDeleteList(String id) {
        if (deleteObjectList == null) {
            deleteObjectList = new ArrayList<String>();
        }
        deleteObjectList.add(id);
    }

    /**
     * There are some cases when a postRollback tries to detach an object that has not been persisted.
     * In the case of detachment, an object reload is attempted, which throws a {@code NucleusObjectNotFoundException}
     * since the object was never stored in the database. We can ignore that exception.
     */
    @Override
    public synchronized void postRollback() {
        try {
            super.postRollback();
        } catch (NucleusObjectNotFoundException ne) {
            // We can ignore this exception
        }
    }
    
    /**
     * Added this method so that we can support detachment of a newly created
     * persistent object that has never been saved to the database.
     * {@inheritDoc}
     */
    @Override
    public synchronized void detachObject(Object obj, FetchPlanState state) {
        StateManager sm = findStateManager(obj);
        super.detachObject(obj, state);
        if (sm != null) {
            clearDirty(sm);
        }
    }
}
