// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.mapper;

import com.azure.autorest.extension.base.model.codemodel.SealedChoiceSchema;
import com.azure.autorest.model.clientmodel.IType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A mapper that maps a sealed choice schema in {@link SealedChoiceSchema} to {@link IType}.
 */
public class SealedChoiceMapper implements IMapper<SealedChoiceSchema, IType> {
    private static final SealedChoiceMapper INSTANCE = new SealedChoiceMapper();
    private static final Map<SealedChoiceSchema, IType> PARSED = new ConcurrentHashMap<>();

    private SealedChoiceMapper() {
    }

    /**
     * Gets the global {@link SealedChoiceMapper} instance.
     *
     * @return the global {@link SealedChoiceMapper} instance.
     */
    public static SealedChoiceMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public IType map(SealedChoiceSchema enumType) {
        if (enumType == null) {
            return null;
        }

        IType sealedChoiceType = PARSED.get(enumType);
        if (sealedChoiceType != null) {
            return sealedChoiceType;
        }

        return PARSED.computeIfAbsent(enumType, et -> MapperUtils.createEnumType(enumType, false));
    }
}
