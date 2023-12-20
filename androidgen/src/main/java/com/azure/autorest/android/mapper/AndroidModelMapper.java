// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.android.mapper;

import com.azure.autorest.android.model.clientmodel.AndroidClientModel;
import com.azure.autorest.mapper.ModelMapper;
import com.azure.autorest.model.clientmodel.ClientModel;

/**
 * The model mapper for Android.
 */
public class AndroidModelMapper extends ModelMapper {
    private static final ModelMapper INSTANCE = new AndroidModelMapper();

    /**
     * Create a new ModelMapper instance.
     */
    protected AndroidModelMapper() {
    }

    /**
     * Get the singleton ModelMapper instance.
     * @return The singleton ModelMapper instance.
     */
    public static ModelMapper getInstance() {
        return INSTANCE;
    }

    @Override
    protected ClientModel.Builder createModelBuilder() {
        return new AndroidClientModel.Builder();
    }
}
