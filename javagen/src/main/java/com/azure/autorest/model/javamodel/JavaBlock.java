// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.model.javamodel;

import java.util.function.Consumer;

/**
 * Represents a Java block.
 */
public class JavaBlock implements JavaContext {
    private final JavaFileContents contents;

    /**
     * Create a new JavaBlock backed by the provided JavaFileContents.
     *
     * @param contents the JavaFileContents that this JavaBlock will be backed by.
     */
    public JavaBlock(JavaFileContents contents) {
        this.contents = contents;
    }

    /**
     * Indent the contents of this JavaBlock.
     *
     * @param indentAction the action to perform while indented.
     */
    public final void indent(Runnable indentAction) {
        contents.indent(indentAction);
    }

    /**
     * Increase the indent level of this JavaBlock.
     */
    public final void increaseIndent() {
        contents.increaseIndent();
    }

    /**
     * Decrease the indent level of this JavaBlock.
     */
    public final void decreaseIndent() {
        contents.decreaseIndent();
    }

    /**
     * Adds text to this JavaBlock.
     *
     * @param text the text to add.
     */
    public final void text(String text) {
        contents.text(text);
    }

    /**
     * Adds a line of text to this JavaBlock.
     *
     * @param text the formattable text to add.
     * @param formattedArguments the arguments to format the text.
     */
    public final void line(String text, Object... formattedArguments) {
        contents.line(text, formattedArguments);
    }

    /**
     * Adds a line to this JavaBlock.
     */
    public final void line() {
        contents.line();
    }

    /**
     * Adds a block starting with the provided text to this JavaBlock.
     *
     * @param text the starting text of the block.
     * @param bodyAction the action that adds the block.
     */
    public final void block(String text, Consumer<JavaBlock> bodyAction) {
        contents.block(text, bodyAction);
    }

    /**
     * Adds a JavaDoc comment to this JavaBlock.
     *
     * @param text the text of the JavaDoc comment.
     */
    public final void javadocComment(String text) {
        contents.javadocComment(text);
    }

    /**
     * Adds a JavaDoc comment to this JavaBlock.
     *
     * @param commentAction the action that adds the JavaDoc comment.
     */
    public final void javadocComment(Consumer<JavaJavadocComment> commentAction) {
        contents.javadocComment(commentAction);
    }

    /**
     * Adds a method return to this JavaBlock.
     *
     * @param text the text of the method return.
     */
    public final void methodReturn(String text) {
        contents.methodReturn(text);
    }

    /**
     * Adds a set of annotations to this JavaBlock.
     *
     * @param annotations the annotations to add.
     */
    public final void annotation(String... annotations) {
        contents.annotation(annotations);
    }

    /**
     * Adds an anonymous class return to this JavaBlock.
     *
     * @param anonymousClassDeclaration the declaration of the anonymous class.
     * @param anonymousClassBlock the action that adds the anonymous class.
     */
    public final void returnAnonymousClass(String anonymousClassDeclaration, Consumer<JavaClass> anonymousClassBlock) {
        contents.returnAnonymousClass(anonymousClassDeclaration, anonymousClassBlock);
    }

    /**
     * Adds an anonymous class return to this JavaBlock.
     *
     * @param anonymousClassDeclaration the declaration of the anonymous class.
     * @param instanceName the name of the instance of the anonymous class.
     * @param anonymousClassBlock the action that adds the anonymous class.
     */
    public final void anonymousClass(String anonymousClassDeclaration, String instanceName,
        Consumer<JavaClass> anonymousClassBlock) {
        contents.anonymousClass(anonymousClassDeclaration, instanceName, anonymousClassBlock);
    }

    /**
     * Adds an if block to this JavaBlock.
     *
     * @param condition the condition of the if block.
     * @param ifAction the action that adds the if block.
     * @return the JavaIfBlock that was added.
     */
    public final JavaIfBlock ifBlock(String condition, Consumer<JavaBlock> ifAction) {
        contents.ifBlock(condition, ifAction);
        return new JavaIfBlock(contents);
    }

    /**
     * Adds a try block to this JavaBlock.
     *
     * @param tryAction the action that adds the try block.
     * @return the JavaTryBlock that was added.
     */
    public final JavaTryBlock tryBlock(Consumer<JavaBlock> tryAction) {
        contents.tryBlock(tryAction);
        return new JavaTryBlock(contents);
    }

    /**
     * Adds a try-with-resources block to this JavaBlock.
     *
     * @param resource the try resource.
     * @param tryAction the action that adds the try block.
     * @return the JavaTryBlock that was added.
     */
    public final JavaTryBlock tryBlock(String resource, Consumer<JavaBlock> tryAction) {
        contents.tryBlock(resource, tryAction);
        return new JavaTryBlock(contents);
    }

    /**
     * Adds a lambda to this JavaBlock.
     *
     * @param parameterType the type of the lambda parameter.
     * @param parameterName the name of the lambda parameter.
     * @param body the action that adds the lambda body.
     */
    public final void lambda(String parameterType, String parameterName, Consumer<JavaLambda> body) {
        contents.lambda(parameterType, parameterName, body);
    }

    /**
     * Adds a lambda to this JavaBlock.
     *
     * @param parameterType the type of the lambda parameter.
     * @param parameterName the name of the lambda parameter.
     * @param returnExpression the return expression of the lambda.
     */
    public final void lambda(String parameterType, String parameterName, String returnExpression) {
        contents.lambda(parameterType, parameterName, returnExpression);
    }
}
