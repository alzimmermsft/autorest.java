// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.locks.implementation;

import com.azure.core.util.Context;
import com.azure.resourcemanager.locks.fluent.models.ManagementLockObjectInner;
import com.azure.resourcemanager.locks.models.LockLevel;
import com.azure.resourcemanager.locks.models.ManagementLockObject;
import com.azure.resourcemanager.locks.models.ManagementLockOwner;
import java.util.Collections;
import java.util.List;

public final class ManagementLockObjectImpl
    implements ManagementLockObject, ManagementLockObject.Definition, ManagementLockObject.Update {
    private ManagementLockObjectInner innerObject;

    private final com.azure.resourcemanager.locks.ManagementLockManager serviceManager;

    public String id() {
        return this.innerModel().id();
    }

    public String name() {
        return this.innerModel().name();
    }

    public String type() {
        return this.innerModel().type();
    }

    public LockLevel level() {
        return this.innerModel().level();
    }

    public String notes() {
        return this.innerModel().notes();
    }

    public List<ManagementLockOwner> owners() {
        List<ManagementLockOwner> inner = this.innerModel().owners();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public ManagementLockObjectInner innerModel() {
        return this.innerObject;
    }

    private com.azure.resourcemanager.locks.ManagementLockManager manager() {
        return this.serviceManager;
    }

    private String resourceGroupName;

    private String lockName;

    public ManagementLockObjectImpl withExistingResourceGroup(String resourceGroupName) {
        this.resourceGroupName = resourceGroupName;
        return this;
    }

    public ManagementLockObject create() {
        this.innerObject =
            serviceManager
                .serviceClient()
                .getManagementLocks()
                .createOrUpdateAtResourceGroupLevelWithResponse(
                    resourceGroupName, lockName, this.innerModel(), Context.NONE)
                .getValue();
        return this;
    }

    public ManagementLockObject create(Context context) {
        this.innerObject =
            serviceManager
                .serviceClient()
                .getManagementLocks()
                .createOrUpdateAtResourceGroupLevelWithResponse(resourceGroupName, lockName, this.innerModel(), context)
                .getValue();
        return this;
    }

    ManagementLockObjectImpl(String name, com.azure.resourcemanager.locks.ManagementLockManager serviceManager) {
        this.innerObject = new ManagementLockObjectInner();
        this.serviceManager = serviceManager;
        this.lockName = name;
    }

    public ManagementLockObjectImpl update() {
        return this;
    }

    public ManagementLockObject apply() {
        this.innerObject =
            serviceManager
                .serviceClient()
                .getManagementLocks()
                .createOrUpdateAtResourceGroupLevelWithResponse(
                    resourceGroupName, lockName, this.innerModel(), Context.NONE)
                .getValue();
        return this;
    }

    public ManagementLockObject apply(Context context) {
        this.innerObject =
            serviceManager
                .serviceClient()
                .getManagementLocks()
                .createOrUpdateAtResourceGroupLevelWithResponse(resourceGroupName, lockName, this.innerModel(), context)
                .getValue();
        return this;
    }

    ManagementLockObjectImpl(
        ManagementLockObjectInner innerObject, com.azure.resourcemanager.locks.ManagementLockManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
        this.resourceGroupName = Utils.getValueFromIdByName(innerObject.id(), "resourceGroups");
        this.lockName = Utils.getValueFromIdByName(innerObject.id(), "locks");
    }

    public ManagementLockObject refresh() {
        this.innerObject =
            serviceManager
                .serviceClient()
                .getManagementLocks()
                .getByResourceGroupWithResponse(resourceGroupName, lockName, Context.NONE)
                .getValue();
        return this;
    }

    public ManagementLockObject refresh(Context context) {
        this.innerObject =
            serviceManager
                .serviceClient()
                .getManagementLocks()
                .getByResourceGroupWithResponse(resourceGroupName, lockName, context)
                .getValue();
        return this;
    }

    public ManagementLockObjectImpl withLevel(LockLevel level) {
        this.innerModel().withLevel(level);
        return this;
    }

    public ManagementLockObjectImpl withNotes(String notes) {
        this.innerModel().withNotes(notes);
        return this;
    }

    public ManagementLockObjectImpl withOwners(List<ManagementLockOwner> owners) {
        this.innerModel().withOwners(owners);
        return this;
    }
}