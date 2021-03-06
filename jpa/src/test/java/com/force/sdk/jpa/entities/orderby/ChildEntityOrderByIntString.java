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

package com.force.sdk.jpa.entities.orderby;

import javax.persistence.*;

import com.force.sdk.jpa.mock.MockApiEntity;
import com.force.sdk.jpa.mock.MockApiField;
import com.sforce.soap.partner.FieldType;

/**
 * Test child entity for ordering by (int, String) tuples.
 *
 * @author Jeff Lai
 */
@Entity
@MockApiEntity
public class ChildEntityOrderByIntString {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MockApiField(name = "Id", type = FieldType.id, custom = false)
    private String id;
    
    @Column(name = "ParentEntityOrderByIntString")
    @ManyToOne
    @MockApiField(name = "ParentEntityOrderByIntString__c", type = FieldType.reference, custom = true,
                  attrs = { "setRelationshipName=ParentEntityOrderByIntString__r" })
    private ParentEntityOrderByIntString parent;
    
    @MockApiField(name = "myInt__c", type = FieldType._double, custom = true)
    private int myInt;
    
    @MockApiField(name = "myString__c", type = FieldType.string, custom = true)
    private int myString;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setParent(ParentEntityOrderByIntString parent) {
        this.parent = parent;
    }

    public ParentEntityOrderByIntString getParent() {
        return parent;
    }

    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    public int getMyInt() {
        return myInt;
    }

    public void setMyString(int myString) {
        this.myString = myString;
    }

    public int getMyString() {
        return myString;
    }

}
