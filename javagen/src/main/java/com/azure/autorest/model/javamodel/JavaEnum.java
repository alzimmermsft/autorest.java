// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.model.javamodel;

import com.azure.autorest.model.clientmodel.IType;
import com.azure.core.util.CoreUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a Java enum.
 */
public class JavaEnum {
    private final JavaFileContents contents;
    private boolean previouslyAddedValue;
    private boolean addNewLine;

    /**
     * Create a new JavaEnum backed by the provided JavaFileContents.
     *
     * @param contents the JavaFileContents that this JavaEnum will be backed by.
     */
    public JavaEnum(JavaFileContents contents) {
        this.contents = contents;
    }

    private void addExpectedNewLine() {
        if (addNewLine) {
            contents.line();
            addNewLine = false;
        }
    }

    private void addExpectedCommaAndNewLine() {
        if (previouslyAddedValue) {
            contents.line(",");
            previouslyAddedValue = false;
        }

        addExpectedNewLine();
    }

    private void addExpectedSemicolonAndNewLine() {
        if (previouslyAddedValue) {
            contents.line(";");
            previouslyAddedValue = false;
        }

        addExpectedNewLine();
    }

    /**
     * Adds an expected new line after the last value in the enum.
     */
    public final void addExpectedNewLineAfterLastValue() {
        if (previouslyAddedValue) {
            contents.line();
            previouslyAddedValue = false;
            addNewLine = false;
        }
    }

    /**
     * Adds a value to the enum.
     *
     * @param name the name of the value.
     * @param value the value of the value.
     */
    public final void value(String name, String value) {
        addExpectedCommaAndNewLine();
        contents.javadocComment("Enum value " + value + ".");
        contents.text(name + "(\"" + value + "\")");
        previouslyAddedValue = true;
        addNewLine = true;
    }

    /**
     * Adds a value to the enum.
     *
     * @param name the name of the value.
     * @param value the value of the value.
     * @param description the description of the value.
     * @param type the type of the value.
     */
    public final void value(String name, String value, String description, IType type) {
        addExpectedCommaAndNewLine();
        contents.javadocComment(CoreUtils.isNullOrEmpty(description) ? "Enum value " + value + "." : description);
        contents.text(name + "(" + type.defaultValueExpression(value) + ")");
        previouslyAddedValue = true;
        addNewLine = true;
    }

    /**
     * Adds a private final member variable to the enum.
     *
     * @param variableType the type of the variable.
     * @param variableName the name of the variable.
     */
    public final void privateFinalMemberVariable(String variableType, String variableName) {
        addExpectedSemicolonAndNewLine();
        contents.line("private final " + variableType + " " + variableName + ";");
        addNewLine = true;
    }

    /**
     * Adds a constructor to the enum.
     * @param constructorSignature the signature of the constructor.
     * @param constructor the action that adds the constructor.
     */
    public final void constructor(String constructorSignature, Consumer<JavaBlock> constructor) {
        addExpectedSemicolonAndNewLine();
        contents.block(constructorSignature, constructor);
        previouslyAddedValue = false;
        addNewLine = true;
    }

    /**
     * Adds a method to the enum.
     *
     * @param visibility the visibility of the method.
     * @param modifiers the modifiers of the method.
     * @param methodSignature the signature of the method.
     * @param method the action that adds the method.
     */
    public final void method(JavaVisibility visibility, List<JavaModifier> modifiers, String methodSignature,
        Consumer<JavaBlock> method) {
        addExpectedSemicolonAndNewLine();
        contents.method(visibility, modifiers, methodSignature, method);
        previouslyAddedValue = false;
        addNewLine = true;
    }

    /**
     * Adds a public method to the enum.
     *
     * @param methodSignature the signature of the method.
     * @param method the action that adds the method.
     */
    public final void publicMethod(String methodSignature, Consumer<JavaBlock> method) {
        method(JavaVisibility.Public, null, methodSignature, method);
    }

    /**
     * Adds a public static method to the enum.
     *
     * @param methodSignature the signature of the method.
     * @param method the action that adds the method.
     */
    public final void publicStaticMethod(String methodSignature, Consumer<JavaBlock> method) {
        method(JavaVisibility.Public, Collections.singletonList(JavaModifier.Static), methodSignature, method);
    }

    /**
     * Adds a javadoc comment to the enum.
     *
     * @param description the description of the enum.
     */
    public final void javadocComment(String description) {
        addExpectedSemicolonAndNewLine();
        contents.javadocComment(description);
    }

    /**
     * Adds a javadoc comment to the enum.
     *
     * @param commentAction the action that adds the javadoc comment.
     */
    public final void javadocComment(Consumer<JavaJavadocComment> commentAction) {
        addExpectedSemicolonAndNewLine();
        contents.javadocComment(commentAction);
    }

    /**
     * Adds annotations to the enum.
     *
     * @param annotations the annotations to add.
     */
    public final void annotation(String... annotations) {
        addExpectedSemicolonAndNewLine();
        contents.annotation(annotations);
    }
}
