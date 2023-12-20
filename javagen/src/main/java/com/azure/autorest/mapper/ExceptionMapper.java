// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.mapper;

import com.azure.autorest.extension.base.model.codemodel.ObjectSchema;
import com.azure.autorest.extension.base.plugin.JavaSettings;
import com.azure.autorest.model.clientmodel.ClientException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A mapper that maps a {@link ObjectSchema} to a {@link ClientException}.
 */
public class ExceptionMapper implements IMapper<ObjectSchema, ClientException> {
    private static final ExceptionMapper INSTANCE = new ExceptionMapper();
    private static final Map<ObjectSchema, ClientException> PARSED = new ConcurrentHashMap<>();

    protected ExceptionMapper() {
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
    public ClientException map(ObjectSchema compositeType) {
        JavaSettings settings = JavaSettings.getInstance();
        if (compositeType == null
                // there is no need to generate Exception class, if we use Exceptions from azure-core
                || (settings.isDataPlaneClient() && settings.isUseDefaultHttpStatusCodeToExceptionTypeMapping())) {
            return null;
        }

        ClientException exception = PARSED.get(compositeType);
        if (exception != null) {
            return exception;
        }

        return PARSED.computeIfAbsent(compositeType, cType -> buildException(cType, settings));
    }

    /**
     * Builds a {@link ClientException} from an {@link ObjectSchema}.
     *
     * @param compositeType The {@link ObjectSchema} to build the {@link ClientException} from.
     * @param settings The current settings.
     * @return The {@link ClientException} created from the {@link ObjectSchema}.
     */
    protected ClientException buildException(ObjectSchema compositeType, JavaSettings settings) {
        String errorName = compositeType.getLanguage().getJava().getName();
        String methodOperationExceptionTypeName = errorName + "Exception";

        if (compositeType.getExtensions() != null && compositeType.getExtensions().getXmsClientName() != null) {
            methodOperationExceptionTypeName = compositeType.getExtensions().getXmsClientName();
        }

        boolean isCustomType = settings.isCustomType(methodOperationExceptionTypeName);
        String exceptionSubPackage = isCustomType
            ? settings.getCustomTypesSubpackage()
            : settings.getModelsSubpackage();
        String packageName = settings.getPackage(exceptionSubPackage);

        return createClientExceptionBuilder()
            .packageName(packageName)
            .name(methodOperationExceptionTypeName)
            .errorName(errorName)
            .build();
    }

    /**
     * Creates a new {@link ClientException.Builder}.
     *
     * @return A new {@link ClientException.Builder}.
     */
    protected ClientException.Builder createClientExceptionBuilder() {
        return new ClientException.Builder();
    }
}
