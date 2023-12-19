// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.util;

/**
 * Factory for creating a ModelNamer.
 */
public interface NamerFactory {

    /**
     * Gets a ModelNamer.
     *
     * @return A ModelNamer.
     */
    ModelNamer getModelNamer();
}
