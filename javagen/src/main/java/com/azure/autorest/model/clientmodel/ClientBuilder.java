// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.model.clientmodel;

import com.azure.autorest.extension.base.plugin.JavaSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a builder for a ServiceClient.
 */
public class ClientBuilder {

    private final String packageName;
    private final String className;
    private final ServiceClient serviceClient;

    // There is naturally ClientBuilder to Client reference, via "buildClient" method and via
    // "@ServiceClientBuilder(serviceClients = {Client.class, AsyncClient.class})"
    // syncClients and asyncClients can be empty. In this case, ClientBuilder build serviceClient directly. Note this
    // usually is only used for internal implementation, as this pattern does not match Java guidelines.
    private final List<AsyncSyncClient> syncClients;
    private final List<AsyncSyncClient> asyncClients;
    private final List<ClientBuilderTrait> builderTraits = new ArrayList<>();
    private final String crossLanguageDefinitionId;

    /**
     * Create a new ClientBuilder with the provided properties.
     *
     * @param packageName The package for the ClientBuilder.
     * @param className The name of the ClientBuilder.
     * @param serviceClient The ServiceClient.
     * @param syncClients The sync clients.
     * @param asyncClients The async clients.
     * @param crossLanguageDefinitionId The cross language definition id.
     */
    public ClientBuilder(String packageName, String className, ServiceClient serviceClient,
        List<AsyncSyncClient> syncClients, List<AsyncSyncClient> asyncClients, String crossLanguageDefinitionId) {
        this.packageName = Objects.requireNonNull(packageName);
        this.className = Objects.requireNonNull(className);
        this.serviceClient = Objects.requireNonNull(serviceClient);
        this.syncClients = Objects.requireNonNull(syncClients);
        this.asyncClients = Objects.requireNonNull(asyncClients);
        this.crossLanguageDefinitionId = crossLanguageDefinitionId;
    }

    /**
     * Get the package for the ClientBuilder.
     *
     * @return The package for the ClientBuilder.
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Get the name of the ClientBuilder.
     *
     * @return The name of the ClientBuilder.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Get the ServiceClient.
     *
     * @return The ServiceClient.
     */
    public ServiceClient getServiceClient() {
        return serviceClient;
    }

    /**
     * Get the sync clients.
     *
     * @return The sync clients.
     */
    public List<AsyncSyncClient> getSyncClients() {
        return syncClients;
    }

    /**
     * Get the async clients.
     *
     * @return The async clients.
     */
    public List<AsyncSyncClient> getAsyncClients() {
        return asyncClients;
    }

    /**
     * Get the builder method name for the sync client.
     *
     * @param syncClient The sync client.
     * @return The builder method name for the sync client.
     */
    public String getBuilderMethodNameForSyncClient(AsyncSyncClient syncClient) {
        boolean singleClient = asyncClients.size() == 1 || syncClient.getMethodGroupClient() == null;
        return singleClient ? "buildClient" : ("build" + syncClient.getClassName());
    }

    /**
     * Get the builder method name for the async client.
     *
     * @param asyncClient The async client.
     * @return The builder method name for the async client.
     */
    public String getBuilderMethodNameForAsyncClient(AsyncSyncClient asyncClient) {
        boolean singleClient = asyncClients.size() == 1 || asyncClient.getMethodGroupClient() == null;
        return singleClient ? "buildAsyncClient" : ("build" + asyncClient.getClassName());
    }

    /**
     * Adds the imports for this ClientBuilder to the provided set.
     *
     * @param imports The set of imports to add to.
     * @param includeImplementationImports Whether to include implementation imports.
     */
    public void addImportsTo(Set<String> imports, boolean includeImplementationImports) {
        JavaSettings settings = JavaSettings.getInstance();
        imports.add(packageName + "." + className);
        serviceClient.addImportsTo(imports, includeImplementationImports, true, settings);
        getSyncClients().forEach(c -> c.addImportsTo(imports, includeImplementationImports));
        getAsyncClients().forEach(c -> c.addImportsTo(imports, includeImplementationImports));
    }

    /**
     * Adds the traits for this ClientBuilder to the provided set.
     *
     * @param trait The trait to add.
     */
    public void addBuilderTrait(ClientBuilderTrait trait) {
        this.builderTraits.add(trait);
    }

    /**
     * Get the traits for this ClientBuilder.
     *
     * @return The traits for this ClientBuilder.
     */
    public List<ClientBuilderTrait> getBuilderTraits() {
        return this.builderTraits;
    }

    /**
     * Get the cross language definition id.
     *
     * @return The cross language definition id.
     */
    public String getCrossLanguageDefinitionId() {
        return this.crossLanguageDefinitionId;
    }
}
