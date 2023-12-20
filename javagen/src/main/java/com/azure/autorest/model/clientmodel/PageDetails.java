// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.model.clientmodel;

/**
 * A page class that contains results that are received from a service request.
 */
public class PageDetails {
    private final String packageName;
    private final String nextLinkName;
    private final String itemName;
    private final String className;

    public PageDetails(String packageKeyword, String nextLinkName, String itemName, String className) {
        this.packageName = packageKeyword;
        this.nextLinkName = nextLinkName;
        this.itemName = itemName;
        this.className = className;
    }

    public final String getNextLinkName() {
        return nextLinkName;
    }

    public final String getItemName() {
        return itemName;
    }

    public final String getClassName() {
        return className;
    }
}
