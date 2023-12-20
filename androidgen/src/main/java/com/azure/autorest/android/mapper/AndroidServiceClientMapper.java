// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.android.mapper;

import com.azure.autorest.android.model.clientmodel.AndroidProxy;
import com.azure.autorest.android.model.clientmodel.AndroidServiceClient;
import com.azure.autorest.extension.base.model.codemodel.CodeModel;
import com.azure.autorest.extension.base.plugin.JavaSettings;
import com.azure.autorest.mapper.ServiceClientMapper;
import com.azure.autorest.model.clientmodel.ClassType;
import com.azure.autorest.model.clientmodel.Proxy;
import com.azure.autorest.model.clientmodel.ServiceClient;
import com.azure.autorest.model.clientmodel.ServiceClientProperty;
import com.azure.autorest.model.javamodel.JavaVisibility;

import java.util.List;

/**
 * A mapper that maps a service client in {@link CodeModel} to {@link ServiceClient}.
 */
public class AndroidServiceClientMapper extends ServiceClientMapper {

    private static final ServiceClientMapper INSTANCE = new AndroidServiceClientMapper();

    /**
     * Creates an instance of the {@link ServiceClientMapper} class.
     */
    protected AndroidServiceClientMapper() {
    }

    /**
     * Gets the singleton instance of the {@link ServiceClientMapper} class.
     *
     * @return the singleton instance of the {@link ServiceClientMapper} class.
     */
    public static ServiceClientMapper getInstance() {
        return INSTANCE;
    }

    @Override
    protected ServiceClient.Builder createClientBuilder() {
        return new AndroidServiceClient.Builder();
    }

    @Override
    protected Proxy.Builder getProxyBuilder() {
        return new AndroidProxy.Builder();
    }

    @Override
    protected void addHttpPipelineProperty(List<ServiceClientProperty> serviceClientProperties) {
        serviceClientProperties.add(new ServiceClientProperty("The HTTP pipeline to send requests through.",
                ClassType.ANDROID_HTTP_PIPELINE, "httpPipeline", true, null));
    }

    @Override
    protected void addSerializerAdapterProperty(List<ServiceClientProperty> serviceClientProperties, JavaSettings settings) {
        serviceClientProperties.add(new ServiceClientProperty("The serializer to serialize an object into a string.",
                ClassType.ANDROID_JACKSON_SERDER, "jacksonSerder", true, null,
                settings.isFluent() ? JavaVisibility.PackagePrivate : JavaVisibility.Public));
    }

    @Override
    protected com.azure.autorest.model.clientmodel.IType getHttpPipelineClassType() {
        return ClassType.ANDROID_HTTP_PIPELINE;
    }

    @Override
    protected com.azure.autorest.model.clientmodel.ClientMethodParameter createSerializerAdapterParameter() {
        return  new com.azure.autorest.model.clientmodel.ClientMethodParameter.Builder()
                .description("The serializer to serialize an object into a string")
                .finalParameter(false)
                .wireType(com.azure.autorest.model.clientmodel.ClassType.ANDROID_JACKSON_SERDER)
                .name("jacksonSerder")
                .required(true)
                .constant(false)
                .fromClient(true)
                .defaultValue(null)
                .annotations(JavaSettings.getInstance().isNonNullAnnotations()
                        ? java.util.Arrays.asList(ClassType.NON_NULL)
                        : new java.util.ArrayList<>())
                .build();
    }
}
