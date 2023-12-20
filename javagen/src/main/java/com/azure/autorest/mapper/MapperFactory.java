// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.mapper;

/**
 * The factory that creates mappers.
 */
public interface MapperFactory {
    /**
     * Gets the {@link ChoiceMapper} instance.
     *
     * @return The {@link ChoiceMapper} instance.
     */
    ChoiceMapper getChoiceMapper();

    /**
     * Gets the {@link SealedChoiceMapper} instance.
     *
     * @return The {@link SealedChoiceMapper} instance.
     */
    SealedChoiceMapper getSealedChoiceMapper();

    /**
     * Gets the {@link PrimitiveMapper} instance.
     *
     * @return The {@link PrimitiveMapper} instance.
     */
    PrimitiveMapper getPrimitiveMapper();

    /**
     * Gets the {@link SchemaMapper} instance.
     *
     * @return The {@link SchemaMapper} instance.
     */
    SchemaMapper getSchemaMapper();

    /**
     * Gets the {@link ArrayMapper} instance.
     *
     * @return The {@link ArrayMapper} instance.
     */
    ArrayMapper getArrayMapper();

    /**
     * Gets the {@link DictionaryMapper} instance.
     *
     * @return The {@link DictionaryMapper} instance.
     */
    DictionaryMapper getDictionaryMapper();

    /**
     * Gets the {@link ObjectMapper} instance.
     *
     * @return The {@link ObjectMapper} instance.
     */
    ObjectMapper getObjectMapper();

    /**
     * Gets the {@link ConstantMapper} instance.
     *
     * @return The {@link ConstantMapper} instance.
     */
    ConstantMapper getConstantMapper();

    /**
     * Gets the {@link ModelPropertyMapper} instance.
     *
     * @return The {@link ModelPropertyMapper} instance.
     */
    ModelPropertyMapper getModelPropertyMapper();

    /**
     * Gets the {@link ModelMapper} instance.
     *
     * @return The {@link ModelMapper} instance.
     */
    ModelMapper getModelMapper();

    /**
     * Gets the {@link ProxyParameterMapper} instance.
     *
     * @return The {@link ProxyParameterMapper} instance.
     */
    ProxyParameterMapper getProxyParameterMapper();

    /**
     * Gets the {@link ProxyMethodMapper} instance.
     *
     * @return The {@link ProxyMethodMapper} instance.
     */
    ProxyMethodMapper getProxyMethodMapper();

    /**
     * Gets the {@link ProxyMethodExampleMapper} instance.
     *
     * @return The {@link ProxyMethodExampleMapper} instance.
     */
    ProxyMethodExampleMapper getProxyMethodExampleMapper();

    /**
     * Gets the {@link MethodGroupMapper} instance.
     *
     * @return The {@link MethodGroupMapper} instance.
     */
    MethodGroupMapper getMethodGroupMapper();

    /**
     * Gets the {@link ClientParameterMapper} instance.
     *
     * @return The {@link ClientParameterMapper} instance.
     */
    ClientParameterMapper getClientParameterMapper();

    /**
     * Gets the {@link ClientMethodMapper} instance.
     *
     * @return The {@link ClientMethodMapper} instance.
     */
    ClientMethodMapper getClientMethodMapper();

    /**
     * Gets the {@link ExceptionMapper} instance.
     *
     * @return The {@link ExceptionMapper} instance.
     */
    ExceptionMapper getExceptionMapper();

    /**
     * Gets the {@link ServiceClientMapper} instance.
     *
     * @return The {@link ServiceClientMapper} instance.
     */
    ServiceClientMapper getServiceClientMapper();

    /**
     * Gets the {@link ClientMapper} instance.
     *
     * @return The {@link ClientMapper} instance.
     */
    ClientMapper getClientMapper();

    /**
     * Gets the {@link AnyMapper} instance.
     *
     * @return The {@link AnyMapper} instance.
     */
    AnyMapper getAnyMapper();

    /**
     * Gets the {@link BinaryMapper} instance.
     *
     * @return The {@link BinaryMapper} instance.
     */
    BinaryMapper getBinaryMapper();

    /**
     * Gets the {@link UnionMapper} instance.
     *
     * @return The {@link UnionMapper} instance.
     */
    UnionMapper getUnionMapper();

    /**
     * Gets the {@link UnionModelMapper} instance.
     *
     * @return The {@link UnionModelMapper} instance.
     */
    UnionModelMapper getUnionModelMapper();

    /**
     * Gets the {@link GraalVmConfigMapper} instance.
     *
     * @return The {@link GraalVmConfigMapper} instance.
     */
    GraalVmConfigMapper getGraalVmConfigMapper();
}
