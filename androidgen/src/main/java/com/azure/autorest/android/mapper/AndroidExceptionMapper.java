// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.android.mapper;

import com.azure.autorest.android.model.clientmodel.AndroidClientException;
import com.azure.autorest.extension.base.model.codemodel.ObjectSchema;
import com.azure.autorest.mapper.ExceptionMapper;
import com.azure.autorest.model.clientmodel.ClientException;

/**
 * A mapper that maps a {@link ObjectSchema} to a {@link ClientException}.
 */
public class AndroidExceptionMapper extends ExceptionMapper {
    private static final ExceptionMapper INSTANCE = new AndroidExceptionMapper();

    /**
     * Creates an instance of the {@link ExceptionMapper}.
     */
    protected AndroidExceptionMapper() {
    }

    /**
     * Gets the global {@link ExceptionMapper} instance.
     *
     * @return The global {@link ExceptionMapper} instance.
     */
    public static ExceptionMapper getInstance() {
        return INSTANCE;
    }

    @Override
    protected ClientException.Builder createClientExceptionBuilder() {
        return new AndroidClientException.Builder();
    }
}
