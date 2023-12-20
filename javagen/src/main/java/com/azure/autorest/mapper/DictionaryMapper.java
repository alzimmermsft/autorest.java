// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.mapper;

import com.azure.autorest.extension.base.model.codemodel.DictionarySchema;
import com.azure.autorest.model.clientmodel.IType;
import com.azure.autorest.model.clientmodel.MapType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A mapper that maps a {@link DictionarySchema} to a {@link MapType}.
 */
public class DictionaryMapper implements IMapper<DictionarySchema, IType> {
    private static final DictionaryMapper INSTANCE = new DictionaryMapper();
    private static final Map<DictionarySchema, IType> PARSED = new ConcurrentHashMap<>();

    private DictionaryMapper() {
    }

    /**
     * Gets the global {@link DictionaryMapper} instance.
     *
     * @return The global {@link DictionaryMapper} instance.
     */
    public static DictionaryMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public IType map(DictionarySchema dictionaryType) {
        if (dictionaryType == null) {
            return null;
        }

        IType dictType = PARSED.get(dictionaryType);
        if (dictType != null) {
            return dictType;
        }

        IType elementType = Mappers.getSchemaMapper().map(dictionaryType.getElementType());
        boolean elementNullable = dictionaryType.getNullableItems() != null && dictionaryType.getNullableItems();
        if (elementNullable) {
            elementType = elementType.asNullable();
        }

        final IType finalElementType = elementType;
        return PARSED.computeIfAbsent(dictionaryType, dt -> new MapType(finalElementType, elementNullable));
    }
}
