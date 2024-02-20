// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.template;

import com.azure.autorest.extension.base.plugin.JavaSettings;
import com.azure.autorest.model.clientmodel.ModuleInfo;
import com.azure.autorest.model.javamodel.JavaFile;
import com.azure.core.util.CoreUtils;

public class ModuleInfoTemplate implements IJavaTemplate<ModuleInfo, JavaFile> {

    private static final ModuleInfoTemplate INSTANCE = new ModuleInfoTemplate();

    private ModuleInfoTemplate() {
    }

    public static ModuleInfoTemplate getInstance() {
        return INSTANCE;
    }

    @Override
    public void write(ModuleInfo model, JavaFile javaFile) {
        JavaSettings settings = JavaSettings.getInstance();
        if (settings.getFileHeaderText() != null && !settings.getFileHeaderText().isEmpty()) {
            javaFile.lineComment(settings.getMaximumJavadocCommentWidth(),
                comment -> comment.line(settings.getFileHeaderText()));
            javaFile.line();
        }

        javaFile.line("module " + model.getModuleName() + " {");
        javaFile.indent(() -> {
            model.getRequireModules().forEach(module -> javaFile.line(
                "requires " + (module.isTransitive() ? "transitive " : "") + module.getModuleName() + ";"));

            model.getExportModules().forEach(module -> javaFile.line("exports " + module.getModuleName() + ";"));

            model.getOpenModules().forEach(module -> {
                String opensTo = module.isOpenTo()
                    ? " to " + CoreUtils.stringJoin(", ", module.getOpenToModules()) : "";
                javaFile.line("opens " + module.getModuleName() + opensTo + ";");
            });
        });
        javaFile.line("}");
    }
}
