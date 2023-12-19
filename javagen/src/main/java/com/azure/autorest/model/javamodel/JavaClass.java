// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.model.javamodel;

import com.azure.core.util.CoreUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Represents a Java class.
 */
public class JavaClass implements JavaType {
    private final JavaFileContents contents;
    private boolean addNewLine;

    /**
     * Create a new JavaClass backed by the provided JavaFileContents.
     *
     * @param contents the JavaFileContents that this JavaClass will be backed by.
     */
    public JavaClass(JavaFileContents contents) {
        this.contents = contents;
    }

    private void addExpectedNewLine() {
        if (addNewLine) {
            contents.line();
            addNewLine = false;
        }
    }

    /**
     * Adds a private member variable.
     *
     * @param variableType The type of the variable.
     * @param variableName The name of the variable.
     */
    public final void privateMemberVariable(String variableType, String variableName) {
        privateMemberVariable(variableType + " " + variableName);
    }

    /**
     * Adds a private member variable.
     *
     * @param variableDeclaration The declaration of the variable.
     */
    public final void privateMemberVariable(String variableDeclaration) {
        addExpectedNewLine();
        contents.line("private " + variableDeclaration + ";");
        addNewLine = true;
    }

    /**
     * Adds a private final member variable.
     *
     * @param variableDeclaration The declaration of the variable.
     */
    public final void privateFinalMemberVariable(String variableDeclaration) {
        addExpectedNewLine();
        contents.line("private final " + variableDeclaration + ";");
        addNewLine = true;
    }

    /**
     * Adds a private final member variable.
     *
     * @param variableType The type of the variable.
     * @param variableName The name of the variable.
     */
    public final void privateFinalMemberVariable(String variableType, String variableName) {
        addExpectedNewLine();
        contents.line("private final " + variableType + " " + variableName + ";");
        addNewLine = true;
    }

    /**
     * Adds a private final member variable.
     *
     * @param variableType The type of the variable.
     * @param variableName The name of the variable.
     * @param finalValue The value of the variable.
     */
    public final void privateFinalMemberVariable(String variableType, String variableName, String finalValue) {
        addExpectedNewLine();
        contents.line("private final " + variableType + " " + variableName + " = " + finalValue + ";");
        addNewLine = true;
    }

    /**
     * Adds a public static final variable.
     *
     * @param variableDeclaration The declaration of the variable.
     */
    public final void publicStaticFinalVariable(String variableDeclaration) {
        addExpectedNewLine();
        contents.line("public static final " + variableDeclaration + ";");
        addNewLine = true;
    }

    /**
     * Adds a private static final variable.
     *
     * @param variableDeclaration The declaration of the variable.
     */
    public final void privateStaticFinalVariable(String variableDeclaration) {
        addExpectedNewLine();
        contents.line("private static final " + variableDeclaration + ";");
        addNewLine = true;
    }

    /**
     * Adds a protected member variable.
     *
     * @param variableType The type of the variable.
     * @param variableName The name of the variable.
     */
    public final void protectedMemberVariable(String variableType, String variableName) {
        addExpectedNewLine();
        contents.line("protected " + variableType + " " + variableName + ";");
        addNewLine = true;
    }

    /**
     * Adds a variable with the given declaration, visibility, and modifiers.
     * <p>
     * Adding a private constant variable would be:
     * {@code variable(declaration, JavaVisibility.Private, JavaModifier.Static, JavaModifier.Final)}
     *
     * @param variableDeclaration The variable declaration.
     * @param visibility The visibility of the variable.
     * @param modifiers The modifiers of the variable.
     */
    public final void variable(String variableDeclaration, JavaVisibility visibility, JavaModifier... modifiers) {
        addExpectedNewLine();
        String modifier = CoreUtils.isNullOrEmpty(modifiers) ? ""
            : Arrays.stream(modifiers).map(JavaModifier::toString).collect(Collectors.joining(" "));
        contents.line(visibility + " " + modifier + " " + variableDeclaration + ";");
        addNewLine = true;
    }

    /**
     * Adds a constructor with the given visibility and signature.
     *
     * @param visibility The visibility of the constructor.
     * @param constructorSignature The signature of the constructor.
     * @param constructor The logic to generate the constructor.
     */
    public final void constructor(JavaVisibility visibility, String constructorSignature,
        Consumer<JavaBlock> constructor) {
        addExpectedNewLine();
        contents.constructor(visibility, constructorSignature, constructor);
        addNewLine = true;
    }

    /**
     * Adds a private constructor with the given signature.
     *
     * @param constructorSignature The signature of the constructor.
     * @param constructor The logic to generate the constructor.
     */
    public final void privateConstructor(String constructorSignature, Consumer<JavaBlock> constructor) {
        constructor(JavaVisibility.Private, constructorSignature, constructor);
    }

    /**
     * Adds a public constructor with the given signature.
     *
     * @param constructorSignature The signature of the constructor.
     * @param constructor The logic to generate the constructor.
     */
    public final void publicConstructor(String constructorSignature, Consumer<JavaBlock> constructor) {
        constructor(JavaVisibility.Public, constructorSignature, constructor);
    }

    /**
     * Adds a package private constructor with the given signature.
     *
     * @param constructorSignature The signature of the constructor.
     * @param constructor The logic to generate the constructor.
     */
    public final void packagePrivateConstructor(String constructorSignature, Consumer<JavaBlock> constructor) {
        constructor(JavaVisibility.PackagePrivate, constructorSignature, constructor);
    }

    /**
     * Adds a method with the given visibility, modifiers, signature, and logic.
     *
     * @param visibility The visibility of the method.
     * @param modifiers The modifiers of the method.
     * @param methodSignature The signature of the method.
     * @param method The logic to generate the method.
     */
    public final void method(JavaVisibility visibility, List<JavaModifier> modifiers, String methodSignature,
        Consumer<JavaBlock> method) {
        addExpectedNewLine();
        contents.method(visibility, modifiers, methodSignature, method);
        addNewLine = true;
    }

    /**
     * Adds a public method with the given signature and logic.
     *
     * @param methodSignature The signature of the method.
     * @param method The logic to generate the method.
     */
    public final void publicMethod(String methodSignature, Consumer<JavaBlock> method) {
        method(JavaVisibility.Public, null, methodSignature, method);
    }

    /**
     * Adds a package private method with the given signature and logic.
     *
     * @param methodSignature The signature of the method.
     * @param method The logic to generate the method.
     */
    public final void packagePrivateMethod(String methodSignature, Consumer<JavaBlock> method) {
        method(JavaVisibility.PackagePrivate, null, methodSignature, method);
    }

    /**
     * Adds a private method with the given signature and logic.
     *
     * @param methodSignature The signature of the method.
     * @param method The logic to generate the method.
     */
    public final void privateMethod(String methodSignature, Consumer<JavaBlock> method) {
        method(JavaVisibility.Private, null, methodSignature, method);
    }

    /**
     * Adds a public static method with the given signature and logic.
     *
     * @param methodSignature The signature of the method.
     * @param method The logic to generate the method.
     */
    public final void publicStaticMethod(String methodSignature, Consumer<JavaBlock> method) {
        staticMethod(JavaVisibility.Public, methodSignature, method);
    }

    /**
     * Adds a static method with a declared visibility to the class.
     *
     * @param visibility The visibility of the method.
     * @param methodSignature The method signature.
     * @param method The logic to generate the method.
     */
    public final void staticMethod(JavaVisibility visibility, String methodSignature, Consumer<JavaBlock> method) {
        Objects.requireNonNull(visibility, "'visibility' cannot be null.");
        method(visibility, Collections.singletonList(JavaModifier.Static), methodSignature, method);
    }

    /**
     * Adds an interface to the class.
     *
     * @param visibility The visibility of the interface.
     * @param interfaceSignature The signature of the interface.
     * @param interfaceBlock The logic to generate the interface.
     */
    public final void interfaceBlock(JavaVisibility visibility, String interfaceSignature,
        Consumer<JavaInterface> interfaceBlock) {
        addExpectedNewLine();
        contents.interfaceBlock(visibility, interfaceSignature, interfaceBlock);
        addNewLine = true;
    }

    /**
     * Adds a public interface to the class.
     *
     * @param interfaceSignature The signature of the interface.
     * @param interfaceBlock The logic to generate the interface.
     */
    public final void publicInterface(String interfaceSignature, Consumer<JavaInterface> interfaceBlock) {
        interfaceBlock(JavaVisibility.Public, interfaceSignature, interfaceBlock);
    }

    /**
     * Adds a private static final class to the class.
     *
     * @param classSignature The signature of the class.
     * @param classBlock The logic to generate the class.
     */
    public final void privateStaticFinalClass(String classSignature, Consumer<JavaClass> classBlock) {
        staticFinalClass(JavaVisibility.Private, classSignature, classBlock);
    }

    /**
     * Adds a static final class to the class.
     *
     * @param visibility The visibility of the class.
     * @param classSignature The signature of the class.
     * @param classBlock The logic to generate the class.
     */
    public final void staticFinalClass(JavaVisibility visibility, String classSignature,
        Consumer<JavaClass> classBlock) {
        addExpectedNewLine();
        contents.classBlock(visibility, Arrays.asList(JavaModifier.Static, JavaModifier.Final), classSignature,
            classBlock);
        addNewLine = true;
    }

    /**
     * Adds a block comment to the class.
     *
     * @param description The description of the comment.
     */
    public final void blockComment(String description) {
        addExpectedNewLine();
        contents.blockComment(description);
    }

    /**
     * Adds a line comment to the class.
     *
     * @param description The description of the comment.
     */
    public final void lineComment(String description) {
        addExpectedNewLine();
        contents.lineComment(description);
    }

    /**
     * Adds a block comment to the class.
     *
     * @param commentAction The logic to generate the comment.
     */
    public final void blockComment(Consumer<JavaLineComment> commentAction) {
        addExpectedNewLine();
        contents.blockComment(commentAction);
    }

    /**
     * Adds a block comment to the class.
     *
     * @param wordWrapWidth The width to wrap the comment at.
     * @param commentAction The logic to generate the comment.
     */
    public final void blockComment(int wordWrapWidth, Consumer<JavaLineComment> commentAction) {
        addExpectedNewLine();
        contents.blockComment(wordWrapWidth, commentAction);
    }

    /**
     * Adds a javadoc comment to the class.
     *
     * @param description The description of the comment.
     */
    public final void javadocComment(String description) {
        addExpectedNewLine();
        contents.javadocComment(description);
    }

    /**
     * Adds a javadoc comment to the class.
     *
     * @param commentAction The logic to generate the comment.
     */
    public final void javadocComment(Consumer<JavaJavadocComment> commentAction) {
        addExpectedNewLine();
        contents.javadocComment(commentAction);
    }

    /**
     * Adds a javadoc comment to the class.
     *
     * @param wordWrapWidth The width to wrap the comment at.
     * @param commentAction The logic to generate the comment.
     */
    public final void javadocComment(int wordWrapWidth, Consumer<JavaJavadocComment> commentAction) {
        addExpectedNewLine();
        contents.javadocComment(wordWrapWidth, commentAction);
    }

    /**
     * Adds annotations to the class.
     *
     * @param annotations The annotations to add.
     */
    public final void annotation(String... annotations) {
        addExpectedNewLine();
        contents.annotation(annotations);
    }
}
