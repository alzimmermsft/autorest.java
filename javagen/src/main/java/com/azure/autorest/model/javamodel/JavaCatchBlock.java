// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.model.javamodel;

import java.util.function.Consumer;

/**
 * Represents a Java catch block.
 */
public class JavaCatchBlock {
    private final JavaFileContents contents;

    /**
     * Create a new JavaCatchBlock backed by the provided JavaFileContents.
     *
     * @param contents the JavaFileContents that this JavaCatchBlock will be backed by.
     */
    public JavaCatchBlock(JavaFileContents contents) {
        this.contents = contents;
    }

    /**
     * Adds a catch block to this JavaCatchBlock.
     *
     * @param exception the exception to catch.
     * @param catchAction the action to perform in the catch block.
     * @return the JavaCatchBlock.
     */
    public final JavaCatchBlock catchBlock(String exception, Consumer<JavaBlock> catchAction) {
        contents.catchBlock(exception, catchAction);
        return new JavaCatchBlock(contents);
    }

    /**
     * Adds a finally block to this JavaCatchBlock.
     *
     * @param finallyAction the action to perform in the finally block.
     */
    public final void finallyBlock(Consumer<JavaBlock> finallyAction) {
        contents.finallyBlock(finallyAction);
    }
}
