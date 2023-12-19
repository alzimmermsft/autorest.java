// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.model.javamodel;

import java.util.function.Consumer;

/**
 * Represents a Java context.
 */
public interface JavaContext {
    /**
     * Adds a JavaDoc comment to this JavaContext.
     *
     * @param commentAction the action to perform while in the JavaDoc comment.
     */
    void javadocComment(Consumer<JavaJavadocComment> commentAction);

    /**
     * Adds annotations to this JavaContext.
     *
     * @param annotations the annotation to add.
     */
    void annotation(String... annotations);
}
