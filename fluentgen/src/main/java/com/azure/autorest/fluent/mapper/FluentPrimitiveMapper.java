// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.fluent.mapper;

import com.azure.autorest.extension.base.model.codemodel.PrimitiveSchema;
import com.azure.autorest.extension.base.model.codemodel.Schema;
import com.azure.autorest.mapper.PrimitiveMapper;
import com.azure.autorest.model.clientmodel.ClassType;
import com.azure.autorest.model.clientmodel.IType;

/**
 * A mapper that maps a primitive type in {@link PrimitiveSchema} to {@link IType}.
 */
public class FluentPrimitiveMapper extends PrimitiveMapper {

    private static final FluentPrimitiveMapper INSTANCE = new FluentPrimitiveMapper();

    /**
     * Gets the global {@link PrimitiveMapper} instance.
     *
     * @return the global {@link PrimitiveMapper} instance.
     */
    public static FluentPrimitiveMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public IType map(PrimitiveSchema primaryType) {
        if (primaryType == null) {
            return null;
        }

        IType result = PARSED.get(primaryType);
        if (result != null) {
            return result;
        }

        if (primaryType.getType() == Schema.AllSchemaTypes.CREDENTIAL) {
            // swagger is "format": "password", which mostly serve as a hint
            return PARSED.computeIfAbsent(primaryType, ignored -> ClassType.STRING);
        } else {
            return super.map(primaryType);
        }
    }
}
