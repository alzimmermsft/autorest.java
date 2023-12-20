// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.android.mapper;

import com.azure.autorest.android.model.clientmodel.AndroidProxyMethodParameter;
import com.azure.autorest.extension.base.model.codemodel.Parameter;
import com.azure.autorest.mapper.ProxyParameterMapper;
import com.azure.autorest.model.clientmodel.ProxyMethodParameter;

/**
 * A mapper that maps a proxy method parameter in {@link Parameter} to {@link ProxyMethodParameter}.
 */
public class AndroidProxyParameterMapper extends ProxyParameterMapper {
    private static final ProxyParameterMapper INSTANCE = new AndroidProxyParameterMapper();

    /**
     * Creates an instance of the {@link ProxyParameterMapper} class.
     */
    protected AndroidProxyParameterMapper() {
    }

    /**
     * Gets the global {@link ProxyParameterMapper} instance.
     *
     * @return the global {@link ProxyParameterMapper} instance.
     */
    public static ProxyParameterMapper getInstance() {
        return INSTANCE;
    }

    @Override
    protected ProxyMethodParameter.Builder createProxyMethodParameterBuilder() {
        return new AndroidProxyMethodParameter.Builder();
    }
}
