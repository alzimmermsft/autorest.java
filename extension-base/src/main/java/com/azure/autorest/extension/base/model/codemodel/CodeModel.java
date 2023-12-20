// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.extension.base.model.codemodel;

import java.util.ArrayList;
import java.util.List;

/**
 * CodeModel
 * <p>
 * the model that contains all the information required to generate a service api
 */
public class CodeModel extends Client {

    /**
     * code model information (Required)
     */
    private Info info;

    /**
     * the full set of schemas for a given service, categorized into convenient collections (Required)
     */
    private Schemas schemas;

    private List<Client> clients = new ArrayList<>();

    /**
     * test model definition
     */
    private TestModel testModel;

    /**
     * Gets the code model information.
     *
     * @return the code model information
     */
    public Info getInfo() {
        return info;
    }

    /**
     * Sets the code model information.
     *
     * @param info the code model information
     */
    public void setInfo(Info info) {
        this.info = info;
    }
    /**
     * Gets the full set of schemas for a given service, categorized into convenient collections.
     *
     * @return the full set of schemas for a given service, categorized into convenient collections
     */
    public Schemas getSchemas() {
        return schemas;
    }

    /**
     * Sets the full set of schemas for a given service, categorized into convenient collections.
     *
     * @param schemas the full set of schemas for a given service, categorized into convenient collections
     */
    public void setSchemas(Schemas schemas) {
        this.schemas = schemas;
    }

    /**
     * Gets the list of clients.
     *
     * @return the list of clients
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Sets the list of clients.
     *
     * @param clients the list of clients
     */
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    /**
     * Gets the test model definition.
     *
     * @return the test model definition
     */
    public TestModel getTestModel() {
        return testModel;
    }

    /**
     * Sets the test model definition.
     *
     * @param testModel the test model definition
     */
    public void setTestModel(TestModel testModel) {
        this.testModel = testModel;
    }
}
