// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.mapper;

import com.azure.autorest.extension.base.plugin.JavaSettings;
import com.azure.autorest.model.clientmodel.ClientException;
import com.azure.autorest.model.clientmodel.ClientModel;
import com.azure.autorest.model.clientmodel.EnumType;
import com.azure.autorest.model.clientmodel.GraalVmConfig;
import com.azure.autorest.model.clientmodel.ServiceClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * A mapper that maps a {@link GraalVmConfigMapper.ServiceAndModel} to a {@link GraalVmConfig}.
 */
public class GraalVmConfigMapper implements IMapper<GraalVmConfigMapper.ServiceAndModel, GraalVmConfig> {
    /**
     * A class that contains the service client and model.
     */
    public static class ServiceAndModel {
        private final Collection<ServiceClient> serviceClients;
        private final Collection<ClientException> exceptions;
        private final Collection<ClientModel> models;
        private final Collection<EnumType> enums;

        /**
         * Creates an instance of the ServiceAndModel class.
         *
         * @param serviceClients The service clients.
         * @param exceptions The exceptions.
         * @param models The models.
         * @param enums The enums.
         */
        public ServiceAndModel(Collection<ServiceClient> serviceClients, Collection<ClientException> exceptions,
            Collection<ClientModel> models, Collection<EnumType> enums) {
            this.serviceClients = serviceClients;
            this.exceptions = exceptions;
            this.models = models;
            this.enums = enums;
        }
    }

    private static final GraalVmConfigMapper INSTANCE = new GraalVmConfigMapper();

    /**
     * Creates an instance of the {@link GraalVmConfigMapper} class.
     */
    protected GraalVmConfigMapper() {
    }

    /**
     * Gets the global {@link GraalVmConfigMapper} instance.
     *
     * @return The global {@link GraalVmConfigMapper} instance.
     */
    public static GraalVmConfigMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public GraalVmConfig map(ServiceAndModel data) {
        // Reflect
        List<String> reflects = new ArrayList<>(data.exceptions.size() + data.models.size() + data.enums.size());

        data.exceptions.stream().map(e -> e.getPackage() + "." + e.getName()).forEach(reflects::add);
        if (!JavaSettings.getInstance().isStreamStyleSerialization()) {
            data.models.stream().map(e -> e.getPackage() + "." + e.getName()).forEach(reflects::add);
            data.enums.stream().map(m -> m.getPackage() + "." + m.getName()).forEach(reflects::add);
        }

        // Proxy
        List<String> proxies = new ArrayList<>();

        data.serviceClients.stream().flatMap(sc -> {
            if (sc.getMethodGroupClients() != null) {
                return sc.getMethodGroupClients().stream();
            } else {
                return Stream.empty();
            }
        }).filter(m -> m.getProxy() != null)
            .map(m -> m.getPackage() + "." + m.getClassName() + "$" + m.getProxy().getName()).forEach(proxies::add);
        data.serviceClients.stream().filter(sc -> sc.getProxy() != null)
            .map(sc -> sc.getPackage() + "." + sc.getClassName() + "$" + sc.getProxy().getName())
            .forEach(proxies::add);

        return new GraalVmConfig(proxies, reflects, JavaSettings.getInstance().isFluent());
    }
}
