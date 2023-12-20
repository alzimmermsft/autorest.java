// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.mapper;

/**
 * Utility class that provides access to all mappers.
 */
public class Mappers {

    private static MapperFactory factory = new DefaultMapperFactory();

    /**
     * Sets the factory that creates mappers.
     *
     * @param mapperFactory The factory that creates mappers.
     */
    public static void setFactory(MapperFactory mapperFactory) {
        factory = mapperFactory;
    }

    /**
     * Gets the {@link ChoiceMapper} instance.
     *
     * @return The {@link ChoiceMapper} instance.
     */
    public static ChoiceMapper getChoiceMapper() {
        return factory.getChoiceMapper();
    }

    /**
     * Gets the {@link SealedChoiceMapper} instance.
     *
     * @return The {@link SealedChoiceMapper} instance.
     */
    public static SealedChoiceMapper getSealedChoiceMapper() {
        return factory.getSealedChoiceMapper();
    }

    /**
     * Gets the {@link PrimitiveMapper} instance.
     *
     * @return The {@link PrimitiveMapper} instance.
     */
    public static PrimitiveMapper getPrimitiveMapper() {
        return factory.getPrimitiveMapper();
    }

    /**
     * Gets the {@link SchemaMapper} instance.
     *
     * @return The {@link SchemaMapper} instance.
     */
    public static SchemaMapper getSchemaMapper() {
        return factory.getSchemaMapper();
    }

    /**
     * Gets the {@link ArrayMapper} instance.
     *
     * @return The {@link ArrayMapper} instance.
     */
    public static ArrayMapper getArrayMapper() {
        return factory.getArrayMapper();
    }

    /**
     * Gets the {@link DictionaryMapper} instance.
     *
     * @return The {@link DictionaryMapper} instance.
     */
    public static DictionaryMapper getDictionaryMapper() {
        return factory.getDictionaryMapper();
    }

    /**
     * Gets the {@link ObjectMapper} instance.
     *
     * @return The {@link ObjectMapper} instance.
     */
    public static ObjectMapper getObjectMapper() {
        return factory.getObjectMapper();
    }

    /**
     * Gets the {@link ConstantMapper} instance.
     *
     * @return The {@link ConstantMapper} instance.
     */
    public static ConstantMapper getConstantMapper() {
        return factory.getConstantMapper();
    }

    /**
     * Gets the {@link ModelPropertyMapper} instance.
     *
     * @return The {@link ModelPropertyMapper} instance.
     */
    public static ModelPropertyMapper getModelPropertyMapper() {
        return factory.getModelPropertyMapper();
    }

    /**
     * Gets the {@link ModelMapper} instance.
     *
     * @return The {@link ModelMapper} instance.
     */
    public static ModelMapper getModelMapper() {
        return factory.getModelMapper();
    }

    /**
     * Gets the {@link ProxyParameterMapper} instance.
     *
     * @return The {@link ProxyParameterMapper} instance.
     */
    public static ProxyParameterMapper getProxyParameterMapper() {
        return factory.getProxyParameterMapper();
    }

    /**
     * Gets the {@link ProxyMethodMapper} instance.
     *
     * @return The {@link ProxyMethodMapper} instance.
     */
    public static ProxyMethodMapper getProxyMethodMapper() {
        return factory.getProxyMethodMapper();
    }

    /**
     * Gets the {@link ProxyMethodExampleMapper} instance.
     *
     * @return The {@link ProxyMethodExampleMapper} instance.
     */
    public static ProxyMethodExampleMapper getProxyMethodExampleMapper() {
        return factory.getProxyMethodExampleMapper();
    }

    /**
     * Gets the {@link MethodGroupMapper} instance.
     *
     * @return The {@link MethodGroupMapper} instance.
     */
    public static MethodGroupMapper getMethodGroupMapper() {
        return factory.getMethodGroupMapper();
    }

    /**
     * Gets the {@link ClientParameterMapper} instance.
     *
     * @return The {@link ClientParameterMapper} instance.
     */
    public static ClientParameterMapper getClientParameterMapper() {
        return factory.getClientParameterMapper();
    }

    /**
     * Gets the {@link ClientMethodMapper} instance.
     *
     * @return The {@link ClientMethodMapper} instance.
     */
    public static ClientMethodMapper getClientMethodMapper() {
        return factory.getClientMethodMapper();
    }

    /**
     * Gets the {@link ExceptionMapper} instance.
     *
     * @return The {@link ExceptionMapper} instance.
     */
    public static ExceptionMapper getExceptionMapper() {
        return factory.getExceptionMapper();
    }

    /**
     * Gets the {@link ServiceClientMapper} instance.
     *
     * @return The {@link ServiceClientMapper} instance.
     */
    public static ServiceClientMapper getServiceClientMapper() {
        return factory.getServiceClientMapper();
    }

    /**
     * Gets the {@link ClientMapper} instance.
     *
     * @return The {@link ClientMapper} instance.
     */
    public static ClientMapper getClientMapper() {
        return factory.getClientMapper();
    }

    /**
     * Gets the {@link AnyMapper} instance.
     *
     * @return The {@link AnyMapper} instance.
     */
    public static AnyMapper getAnyMapper() {
        return factory.getAnyMapper();
    }

    /**
     * Gets the {@link BinaryMapper} instance.
     *
     * @return The {@link BinaryMapper} instance.
     */
    public static BinaryMapper getBinaryMapper() {
        return factory.getBinaryMapper();
    }

    /**
     * Gets the {@link UnionMapper} instance.
     *
     * @return The {@link UnionMapper} instance.
     */
    public static UnionMapper getUnionMapper() {
        return factory.getUnionMapper();
    }

    /**
     * Gets the {@link UnionModelMapper} instance.
     *
     * @return The {@link UnionModelMapper} instance.
     */
    public static UnionModelMapper getUnionModelMapper() {
        return factory.getUnionModelMapper();
    }

    /**
     * Gets the {@link GraalVmConfigMapper} instance.
     *
     * @return The {@link GraalVmConfigMapper} instance.
     */
    public static GraalVmConfigMapper getGraalVmConfigMapper() {
        return factory.getGraalVmConfigMapper();
    }
}
