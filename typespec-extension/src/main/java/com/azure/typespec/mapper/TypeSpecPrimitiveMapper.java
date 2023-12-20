// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.typespec.mapper;

import com.azure.autorest.extension.base.model.codemodel.PrimitiveSchema;
import com.azure.autorest.extension.base.model.codemodel.Schema;
import com.azure.autorest.mapper.PrimitiveMapper;
import com.azure.autorest.model.clientmodel.ClassType;
import com.azure.autorest.model.clientmodel.IType;
import com.azure.autorest.model.clientmodel.PrimitiveType;

/**
 * A mapper that maps a primitive type in {@link PrimitiveSchema} to {@link IType}.
 */
public class TypeSpecPrimitiveMapper extends PrimitiveMapper {

    private static final PrimitiveMapper INSTANCE = new TypeSpecPrimitiveMapper();

    /**
     * Gets the global {@link PrimitiveMapper} instance.
     *
     * @return the global {@link PrimitiveMapper} instance.
     */
    public static PrimitiveMapper getInstance() {
        return INSTANCE;
    }

    @Override
    protected IType createPrimitiveType(PrimitiveSchema primaryType) {
        if (primaryType.getType() == Schema.AllSchemaTypes.DATE) {
            return ClassType.LOCAL_DATE;
        } else if (primaryType.getType() == Schema.AllSchemaTypes.UNIXTIME) {
            return PrimitiveType.UNIX_TIME_LONG;
        } else {
            return super.createPrimitiveType(primaryType);
        }
    }
}
