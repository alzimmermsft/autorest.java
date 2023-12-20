// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.fluent.mapper;

import com.azure.autorest.extension.base.model.codemodel.ObjectSchema;
import com.azure.autorest.extension.base.plugin.JavaSettings;
import com.azure.autorest.fluent.model.FluentType;
import com.azure.autorest.fluent.util.Utils;
import com.azure.autorest.mapper.ExceptionMapper;
import com.azure.autorest.model.clientmodel.ClassType;
import com.azure.autorest.model.clientmodel.ClientException;

/**
 * A mapper that maps a {@link ObjectSchema} to a {@link ClientException}.
 */
public class FluentExceptionMapper extends ExceptionMapper {

    private static final FluentExceptionMapper INSTANCE = new FluentExceptionMapper();

    /**
     * Creates an instance of the {@link ExceptionMapper}.
     */
    protected FluentExceptionMapper() {
    }

    /**
     * Gets the global {@link ExceptionMapper} instance.
     *
     * @return The global {@link ExceptionMapper} instance.
     */
    public static FluentExceptionMapper getInstance() {
        return INSTANCE;
    }

    /**
     * Builds a {@link ClientException} from an {@link ObjectSchema}.
     *
     * @param compositeType The {@link ObjectSchema} to build the {@link ClientException} from.
     * @param settings The current settings.
     * @return The {@link ClientException} created from the {@link ObjectSchema}.
     */
    @Override
    protected ClientException buildException(ObjectSchema compositeType, JavaSettings settings) {
        if (!FluentType.nonManagementError(Utils.getJavaName(compositeType))) {
            // Use ManagementException directly, no need to build new Exception class.
            return null;
        }

        String errorName = compositeType.getLanguage().getJava().getName();
        String methodOperationExceptionTypeName = errorName + "Exception";

        boolean isManagementException = compositeType.getParents() != null
                && !FluentType.nonManagementError(Utils.getJavaName(compositeType.getParents().getImmediate().get(0)));

        return new ClientException.Builder()
            .packageName(settings.getPackage(settings.getModelsSubpackage()))
            .name(methodOperationExceptionTypeName)
            .errorName(errorName)
            .parentType(isManagementException ? FluentType.MANAGEMENT_EXCEPTION : ClassType.HTTP_RESPONSE_EXCEPTION)
            .build();
    }
}
