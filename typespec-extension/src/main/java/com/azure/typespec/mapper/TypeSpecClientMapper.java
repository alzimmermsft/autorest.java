// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.typespec.mapper;

import com.azure.autorest.extension.base.model.codemodel.Client;
import com.azure.autorest.extension.base.model.codemodel.CodeModel;
import com.azure.autorest.mapper.ClientMapper;
import com.azure.autorest.model.clientmodel.ClientModel;
import com.azure.autorest.model.clientmodel.ClientResponse;
import com.azure.autorest.model.clientmodel.EnumType;
import com.azure.autorest.model.clientmodel.ServiceClient;
import com.azure.typespec.util.ModelUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeSpecClientMapper extends ClientMapper {

    private static final ClientMapper INSTANCE = new TypeSpecClientMapper();

    public static ClientMapper getInstance() {
        return INSTANCE;
    }

    protected TypeSpecClientMapper() {
    }

    @Override
    protected Map<ServiceClient, Client> processClients(List<Client> clients, CodeModel codeModel) {
        Map<ServiceClient, Client> serviceClientsMap = new LinkedHashMap<>();
        TypeSpecServiceClientMapper mapper = new TypeSpecServiceClientMapper();
        for (Client client : clients) {
            serviceClientsMap.put(mapper.map(client, codeModel), client);
        }
        return serviceClientsMap;
    }

    @Override
    protected List<String> getModelsPackages(List<ClientModel> clientModels, List<EnumType> enumTypes,
        List<ClientResponse> responseModels) {
        Set<String> packages = new HashSet<>();

        clientModels.stream().filter(ModelUtil::isGeneratingModel).map(ClientModel::getPackage).forEach(packages::add);
        enumTypes.stream().filter(ModelUtil::isGeneratingModel).map(EnumType::getPackage).forEach(packages::add);
        responseModels.stream().filter(ModelUtil::isGeneratingModel).map(ClientResponse::getPackage)
            .forEach(packages::add);

        return new ArrayList<>(packages);
    }
}
