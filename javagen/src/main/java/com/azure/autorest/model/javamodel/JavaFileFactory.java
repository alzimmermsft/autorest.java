// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.model.javamodel;

import com.azure.autorest.extension.base.plugin.JavaSettings;
import com.azure.autorest.util.CodeNamer;

import java.io.File;
import java.nio.file.Paths;

public class JavaFileFactory {
    private final JavaSettings settings;

    public JavaFileFactory(JavaSettings settings) {
        this.settings = settings;
    }

    public final JavaFile createEmptySourceFile(String packageKeyword, String fileNameWithoutExtension) {
        String filePath = getFilePath("main", packageKeyword, fileNameWithoutExtension);
        return new JavaFile(filePath);
    }

    public final JavaFile createSourceFile(String packageKeyword, String fileNameWithoutExtension) {
        JavaFile javaFile = createEmptySourceFile(packageKeyword, fileNameWithoutExtension);

        addCommentAndPackage(javaFile, packageKeyword);

        return javaFile;
    }

    public final JavaFile createSampleFile(String packageKeyword, String fileNameWithoutExtension) {
        String filePath = getFilePath("samples", packageKeyword, fileNameWithoutExtension);
        JavaFile javaFile = new JavaFile(filePath);

        addCommentAndPackage(javaFile, packageKeyword);

        return javaFile;
    }

    public final JavaFile createTestFile(String packageKeyword, String fileNameWithoutExtension) {
        String filePath = getFilePath("test", packageKeyword, fileNameWithoutExtension);
        JavaFile javaFile = new JavaFile(filePath);

        addCommentAndPackage(javaFile, packageKeyword);

        return javaFile;
    }

    private static String getFilePath(String sourceDirectory, String packageKeyword, String fileNameWithoutExtension) {
        packageKeyword = CodeNamer.linearReplace(packageKeyword, ".", File.separator);
        String folderPath = Paths.get("src", sourceDirectory, "java", packageKeyword).toString();
        String filePath = Paths.get(folderPath).resolve(fileNameWithoutExtension + ".java").toString();
        filePath = CodeNamer.linearReplace(filePath, "\\", "/");
        return CodeNamer.linearReplace(filePath, "//", "/");
    }

    private void addCommentAndPackage(JavaFile javaFile, String packageName) {
        String headerComment = settings.getFileHeaderText();
        if (headerComment != null && !headerComment.isEmpty()) {
            javaFile.lineComment(settings.getMaximumJavadocCommentWidth(), comment -> comment.line(headerComment));
            javaFile.line();
        }

        javaFile.declarePackage(packageName);
        javaFile.line();
    }
}
