// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.mapper;

import com.azure.autorest.extension.base.model.codemodel.ArraySchema;
import com.azure.autorest.extension.base.model.codemodel.ConstantSchema;
import com.azure.autorest.extension.base.model.codemodel.ObjectSchema;
import com.azure.autorest.extension.base.model.codemodel.Property;
import com.azure.autorest.extension.base.model.codemodel.Schema;
import com.azure.autorest.extension.base.model.codemodel.XmlSerlializationFormat;
import com.azure.autorest.extension.base.plugin.JavaSettings;
import com.azure.autorest.model.clientmodel.ClassType;
import com.azure.autorest.model.clientmodel.ClientModelProperty;
import com.azure.autorest.model.clientmodel.EnumType;
import com.azure.autorest.model.clientmodel.IType;
import com.azure.autorest.model.clientmodel.PrimitiveType;
import com.azure.autorest.util.CodeNamer;
import com.azure.autorest.util.SchemaUtil;
import com.azure.core.util.CoreUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * A mapper that maps {@link Property} to {@link ClientModelProperty} .
 */
public class ModelPropertyMapper implements IMapper<Property, ClientModelProperty> {
    private static final ModelPropertyMapper INSTANCE = new ModelPropertyMapper();

    private ModelPropertyMapper() {
    }

    /**
     * Gets the global {@link ModelPropertyMapper} instance.
     *
     * @return the global {@link ModelPropertyMapper} instance.
     */
    public static ModelPropertyMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ClientModelProperty map(Property property) {
        JavaSettings settings = JavaSettings.getInstance();

        ClientModelProperty.Builder builder = new ClientModelProperty.Builder()
            .name(property.getLanguage().getJava().getName())
            .required(property.isRequired())
            .readOnly(property.isReadOnly());


        String description;
        String summaryInProperty = property.getSummary();
        if (summaryInProperty == null) {
            summaryInProperty = property.getSchema() == null ? null : property.getSchema().getSummary();
        }
        String descriptionInProperty = property.getLanguage().getJava() == null
            ? null : property.getLanguage().getJava().getDescription();
        if (CoreUtils.isNullOrEmpty(summaryInProperty) && CoreUtils.isNullOrEmpty(descriptionInProperty)) {
            description = "The " + property.getSerializedName() + " property.";
        } else {
            description = SchemaUtil.mergeSummaryWithDescription(summaryInProperty, descriptionInProperty);
        }
        builder.description(description);

        boolean flattened = false;
        if (settings.getModelerSettings().isFlattenModel()) {   // enabled by modelerfour
            if (settings.getClientFlattenAnnotationTarget() == JavaSettings.ClientFlattenAnnotationTarget.TYPE) {
                if (property.getParentSchema() != null) {
                    flattened = property.getParentSchema().getProperties().stream()
                            .anyMatch(p -> !CoreUtils.isNullOrEmpty(p.getFlattenedNames()));
                    if (!flattened) {
                        String discriminatorSerializedName = SchemaUtil.getDiscriminatorSerializedName(property.getParentSchema());
                        flattened = discriminatorSerializedName.contains(".");
                    }
                } else {
                    flattened = !CoreUtils.isNullOrEmpty(property.getFlattenedNames());
                }
            } else if (settings.getClientFlattenAnnotationTarget() == JavaSettings.ClientFlattenAnnotationTarget.FIELD) {
                flattened = !CoreUtils.isNullOrEmpty(property.getFlattenedNames());
            }
        }
        builder.needsFlatten(flattened);

        if (property.getExtensions() != null && property.getExtensions().isXmsClientFlatten()
            // avoid non-object schema or a plain object schema without any properties
            && property.getSchema() instanceof ObjectSchema && !ObjectMapper.isPlainObject((ObjectSchema) property.getSchema())
            && settings.getClientFlattenAnnotationTarget() == JavaSettings.ClientFlattenAnnotationTarget.NONE) {
            // avoid naming conflict
            builder.name("inner" + CodeNamer.toPascalCase(property.getLanguage().getJava().getName()));
            builder.clientFlatten(true);
        }

        StringBuilder serializedName = new StringBuilder();
        if (property.getFlattenedNames() != null && !property.getFlattenedNames().isEmpty()) {
            for (String flattenedName : property.getFlattenedNames()) {
                serializedName.append(CodeNamer.linearReplace(flattenedName, ".", "\\\\.")).append(".");
            }
            serializedName.deleteCharAt(serializedName.length() - 1);
        } else if (flattened) {
            serializedName.append(CodeNamer.linearReplace(property.getSerializedName(), ".", "\\\\."));
        } else {
            serializedName.append(property.getSerializedName());
        }

        String serializedNameString = serializedName.toString();
        builder.serializedName(serializedNameString);
        if (serializedNameString.isEmpty() && "additionalProperties".equals(property.getLanguage().getJava().getName())) {
            builder.additionalProperties(true);
        }

        boolean propertyIsSecret = false;
        if (property.getExtensions() != null) {
            if (property.getExtensions().getXmsSecret() != null) {
                propertyIsSecret = property.getExtensions().getXmsSecret();
            }
        }

        XmlSerlializationFormat xmlSerlializationFormat = null;
        if (property.getSchema().getSerialization() != null) {
            xmlSerlializationFormat = property.getSchema().getSerialization().getXml();
        }

        String xmlName = null;
        String xmlNamespace = null;
        boolean isXmlWrapper = false;
        boolean isXmlAttribute = false;
        boolean isXmlText = false;
        String xmlPrefix = null;
        if (xmlSerlializationFormat != null) {
            isXmlWrapper = xmlSerlializationFormat.isWrapped();
            isXmlAttribute = xmlSerlializationFormat.isAttribute();
            xmlName = xmlSerlializationFormat.getName();
            xmlNamespace = xmlSerlializationFormat.getNamespace();
            isXmlText = xmlSerlializationFormat.isText();
            xmlPrefix = xmlSerlializationFormat.getPrefix();
        }

        final String xmlParamName = xmlName == null ? serializedName.toString() : xmlName;
        builder.xmlName(xmlParamName)
            .xmlWrapper(isXmlWrapper)
            .xmlAttribute(isXmlAttribute)
            .xmlNamespace(xmlNamespace)
            .xmlText(isXmlText)
            .xmlPrefix(xmlPrefix);

        List<String> annotationArgumentList = new ArrayList<>();
        annotationArgumentList.add("value = \"" + xmlParamName + "\"");

        if (property.isRequired() && !propertyIsSecret && !settings.isDisableRequiredJsonAnnotation()) {
            annotationArgumentList.add("required = true");
        }

        // Though this looks odd to add WRITE_ONLY access when the property is marked as read-only it is the correct
        // behavior. The Swagger definition for read-only is from the perspective of the service which correlates to
        // write-only behavior in an SDK.
        if (property.isReadOnly()) {
            annotationArgumentList.add("access = JsonProperty.Access.WRITE_ONLY");
        }
        builder.annotationArguments(CoreUtils.stringJoin(", ", annotationArgumentList));

        String headerCollectionPrefix = null;
        if (property.getExtensions() != null && property.getExtensions().getXmsHeaderCollectionPrefix() != null) {
            headerCollectionPrefix = property.getExtensions().getXmsHeaderCollectionPrefix();
        }
        builder.headerCollectionPrefix(headerCollectionPrefix);

        IType propertyWireType = Mappers.getSchemaMapper().map(property.getSchema());
        if (property.isNullable() || !property.isRequired()) {
            propertyWireType = propertyWireType.asNullable();
        }
        // Invariant: clientType == wireType.getClientType()
        IType propertyClientType = propertyWireType.getClientType();
        builder.wireType(propertyWireType).clientType(propertyClientType);

        Schema autoRestPropertyModelType = property.getSchema();
        if (autoRestPropertyModelType instanceof ArraySchema) {
            ArraySchema sequence = (ArraySchema) autoRestPropertyModelType;
            if (sequence.getElementType().getSerialization() != null
                && sequence.getElementType().getSerialization().getXml() != null
                && sequence.getElementType().getSerialization().getXml().getName() != null) {
                builder.xmlListElementName(sequence.getElementType().getSerialization().getXml().getName());
                builder.xmlListElementNamespace(sequence.getElementType().getSerialization().getXml().getNamespace());
                builder.xmlListElementPrefix(sequence.getElementType().getSerialization().getXml().getPrefix());
            } else {
                builder.xmlListElementName(sequence.getElementType().getLanguage().getDefault().getName());
                builder.xmlListElementNamespace(sequence.getElementType().getLanguage().getDefault().getNamespace());
            }
        }

        if (property.getSchema() instanceof ConstantSchema) {
            Object objValue = ((ConstantSchema) property.getSchema()).getValue().getValue();
            builder.constant(true);
            builder.defaultValue(objValue == null ? null : propertyClientType.defaultValueExpression(String.valueOf(objValue)));
        }

        // x-ms-mutability
        if (property.getExtensions() != null) {
            List<String> xmsMutability = property.getExtensions().getXmsMutability();
            if (xmsMutability != null) {
                builder.mutabilities(xmsMutability.stream()
                    .map(m -> ClientModelProperty.Mutability.valueOf(m.toUpperCase(Locale.ROOT)))
                    .collect(Collectors.toList()));
            }
        }

        // handle x-ms-client-default for primitive type, enum, boxed type and string
        if (property.getClientDefaultValue() != null &&
            (propertyWireType instanceof PrimitiveType || propertyWireType instanceof EnumType
                || (propertyWireType instanceof ClassType && ((ClassType) propertyWireType).isBoxedType())
                || propertyWireType.equals(ClassType.STRING))) {
            builder.defaultValue(propertyWireType.defaultValueExpression(property.getClientDefaultValue()));
        }

        return builder.build();
    }
}
