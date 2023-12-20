// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.mapper;

import com.azure.autorest.extension.base.model.codemodel.ChoiceSchema;
import com.azure.autorest.model.clientmodel.EnumType;
import com.azure.autorest.model.clientmodel.IType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A mapper that maps a {@link ChoiceSchema} to an {@link EnumType}.
 */
public class ChoiceMapper implements IMapper<ChoiceSchema, IType> {
    private static final ChoiceMapper INSTANCE = new ChoiceMapper();
    private static final Map<ChoiceSchema, IType> PARSED = new ConcurrentHashMap<>();

    private ChoiceMapper() {
    }

    /**
     * Gets the global {@link ChoiceMapper} instance.
     *
     * @return The global {@link ChoiceMapper} instance.
     */
    public static ChoiceMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public IType map(ChoiceSchema enumType) {
        if (enumType == null) {
            return null;
        }

        IType choiceType = PARSED.get(enumType);
        if (choiceType != null) {
            return choiceType;
        }

        return PARSED.computeIfAbsent(enumType, ChoiceMapper::createChoiceType);
    }

    private static IType createChoiceType(ChoiceSchema enumType) {
        return MapperUtils.createEnumType(enumType, true);
    }
}
