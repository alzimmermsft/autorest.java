// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.extension.base.model.codemodel;

import com.azure.autorest.extension.base.model.extensionmodel.XmsExtensions;

/**
 * Describes a header parameter.
 */
public class Header {
    private String header;
    private Schema schema;
    private XmsExtensions extensions;

    /**
     * Gets the header name.
     *
     * @return the header name.
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets the header name.
     *
     * @param header the header name to set.
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Gets the header schema.
     *
     * @return the header schema.
     */
    public Schema getSchema() {
        return schema;
    }

    /**
     * Sets the header schema.
     *
     * @param schema the header schema to set.
     */
    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    /**
     * Gets the header extensions.
     *
     * @return the header extensions.
     */
    public XmsExtensions getExtensions() {
        return extensions;
    }

    /**
     * Sets the header extensions.
     *
     * @param extensions the header extensions to set.
     */
    public void setExtensions(XmsExtensions extensions) {
        this.extensions = extensions;
    }
}
