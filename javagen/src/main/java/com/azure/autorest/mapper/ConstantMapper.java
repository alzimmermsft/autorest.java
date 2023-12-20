// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.mapper;

import com.azure.autorest.extension.base.model.codemodel.ConstantSchema;
import com.azure.autorest.model.clientmodel.IType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A mapper that maps a {@link ConstantSchema} to a type.
 */
public class ConstantMapper implements IMapper<ConstantSchema, IType> {
    private static final ConstantMapper INSTANCE = new ConstantMapper();
    private static final Map<ConstantSchema, IType> PARSED = new ConcurrentHashMap<>();

    private ConstantMapper() {
    }

    /**
     * Gets the global {@link ConstantMapper} instance.
     *
     * @return The global {@link ConstantMapper} instance.
     */
    public static ConstantMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public IType map(ConstantSchema constantSchema) {
        if (constantSchema == null) {
            return null;
        }

        IType constantType = PARSED.get(constantSchema);
        if (constantType != null) {
            return constantType;
        }

        return PARSED.computeIfAbsent(constantSchema, cs -> Mappers.getSchemaMapper().map(cs.getValueType()));
    }
}
