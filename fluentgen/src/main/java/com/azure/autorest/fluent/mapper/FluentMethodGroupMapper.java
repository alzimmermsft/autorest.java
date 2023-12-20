// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.fluent.mapper;

import com.azure.autorest.extension.base.model.codemodel.OperationGroup;
import com.azure.autorest.extension.base.plugin.JavaSettings;
import com.azure.autorest.extension.base.plugin.PluginLogger;
import com.azure.autorest.fluent.FluentGen;
import com.azure.autorest.fluent.model.FluentType;
import com.azure.autorest.fluent.model.WellKnownMethodName;
import com.azure.autorest.fluent.util.TypeConversionUtils;
import com.azure.autorest.fluent.util.Utils;
import com.azure.autorest.mapper.MethodGroupMapper;
import com.azure.autorest.model.clientmodel.ClientMethod;
import com.azure.autorest.model.clientmodel.GenericType;
import com.azure.autorest.model.clientmodel.IType;
import com.azure.autorest.model.clientmodel.MethodGroupClient;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A mapper that maps a {@link OperationGroup} to a {@link MethodGroupClient}.
 */
public class FluentMethodGroupMapper extends MethodGroupMapper {

    private static final Logger LOGGER = new PluginLogger(FluentGen.getPluginInstance(), FluentMethodGroupMapper.class);

    private static final FluentMethodGroupMapper INSTANCE = new FluentMethodGroupMapper();

    /**
     * Gets the global {@link FluentMethodGroupMapper} instance.
     *
     * @return the global {@link FluentMethodGroupMapper} instance.
     */
    public static FluentMethodGroupMapper getInstance() {
        return INSTANCE;
    }

    @Override
    protected List<IType> supportedInterfaces(OperationGroup operationGroup, List<ClientMethod> clientMethods) {
        if (!JavaSettings.getInstance().isFluentLite()) {
            return findSupportedInterfaces(operationGroup, clientMethods);
        } else {
            return Collections.emptyList();
        }
    }

    List<IType> findSupportedInterfaces(OperationGroup operationGroup, List<ClientMethod> clientMethods) {
        List<IType> interfaces = new ArrayList<>();
        IType classTypeForGet = supportGetMethod(clientMethods);
        if (classTypeForGet != null) {
            interfaces.add(FluentType.InnerSupportsGet(classTypeForGet));
        }

        IType classTypeForList = supportListMethod(clientMethods);
        if (classTypeForList != null) {
            interfaces.add(FluentType.InnerSupportsList(classTypeForList));
        }

        IType classTypeForDelete = supportDeleteMethod(clientMethods);
        if (classTypeForDelete != null) {
            interfaces.add(FluentType.InnerSupportsDelete(classTypeForDelete));
        }

        if (!interfaces.isEmpty()) {
            LOGGER.info("Method group '{}' support interfaces {}",
                    Utils.getJavaName(operationGroup),
                    interfaces.stream().map(IType::toString).collect(Collectors.toList()));
        }

        return interfaces;
    }

    private static IType supportGetMethod(List<ClientMethod> clientMethods) {
        for (ClientMethod clientMethod : clientMethods) {
            if (WellKnownMethodName.GET_BY_RESOURCE_GROUP.getMethodName().equals(clientMethod.getName())
                && checkNonClientRequiredParameters(clientMethod, 2)) {
                return clientMethod.getReturnValue().getType();
            }
        }

        return null;
    }

    private static IType supportDeleteMethod(List<ClientMethod> clientMethods) {
        for (ClientMethod clientMethod : clientMethods) {
            if (WellKnownMethodName.DELETE.getMethodName().equals(clientMethod.getName())
                && checkNonClientRequiredParameters(clientMethod, 2)) {
                return clientMethod.getReturnValue().getType();
            }
        }

        return null;
    }

    private static IType supportListMethod(List<ClientMethod> clientMethods) {
        boolean listTypeFound = false;
        IType listType = null;
        boolean listByResourceGroupTypeFound = false;
        IType listByResourceGroupType = null;

        for (ClientMethod clientMethod : clientMethods) {
            if (WellKnownMethodName.LIST.getMethodName().equals(clientMethod.getName())
                    && checkNonClientRequiredParameters(clientMethod, 0)) {
                listTypeFound = true;
                listType = clientMethod.getReturnValue().getType();
            } else if (WellKnownMethodName.LIST_BY_RESOURCE_GROUP.getMethodName().equals(clientMethod.getName())
                    && checkNonClientRequiredParameters(clientMethod, 1)) {
                listByResourceGroupTypeFound = true;
                listByResourceGroupType = clientMethod.getReturnValue().getType();
            }

            if (listTypeFound && listByResourceGroupTypeFound) {
                break;
            }
        }

        IType commonListType = (listTypeFound && listByResourceGroupTypeFound
            && Objects.equals(listType.toString(), listByResourceGroupType.toString()))
                ? listType
                : null;

        return (commonListType != null && TypeConversionUtils.isPagedIterable(commonListType))
            ? ((GenericType) commonListType).getTypeArguments()[0] : null;
    }

    private static boolean checkNonClientRequiredParameters(ClientMethod clientMethod, int requiredCount) {
        final boolean countRequiredParametersOnly = JavaSettings.getInstance().isRequiredParameterClientMethods();
        return requiredCount == clientMethod.getParameters().stream()
                .filter(p -> (!countRequiredParametersOnly || p.isRequired()) && !p.isConstant() && !p.isFromClient())
                .count();
    }
}
