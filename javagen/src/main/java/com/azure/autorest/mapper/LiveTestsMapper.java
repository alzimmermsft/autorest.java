// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.mapper;

import com.azure.autorest.extension.base.model.codemodel.ScenarioStep;
import com.azure.autorest.extension.base.model.codemodel.ScenarioTest;
import com.azure.autorest.extension.base.model.codemodel.TestModel;
import com.azure.autorest.extension.base.model.codemodel.TestScenario;
import com.azure.autorest.extension.base.model.codemodel.TestScenarioStepType;
import com.azure.autorest.model.clientmodel.ExampleLiveTestStep;
import com.azure.autorest.model.clientmodel.LiveTestCase;
import com.azure.autorest.model.clientmodel.LiveTestStep;
import com.azure.autorest.model.clientmodel.LiveTests;
import com.azure.autorest.model.clientmodel.ProxyMethodExample;
import com.azure.autorest.util.CodeNamer;
import com.azure.autorest.util.XmsExampleWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A mapper that maps a {@link TestModel} to a {@link List} of {@link LiveTests}.
 */
public class LiveTestsMapper implements IMapper<TestModel, List<LiveTests>>{

    private static final LiveTestsMapper INSTANCE = new LiveTestsMapper();

    /**
     * Gets the global {@link LiveTestsMapper} instance.
     *
     * @return The global {@link LiveTestsMapper} instance.
     */
    public static LiveTestsMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public List<LiveTests> map(TestModel testModel) {
        if (testModel.getScenarioTests() == null) {
            return new ArrayList<>();
        }

        List<LiveTests> liveTestsList = new ArrayList<>();

        for (ScenarioTest scenarioTest : testModel.getScenarioTests()) {
            LiveTests liveTests = new LiveTests(getFilename(scenarioTest.getFilePath()));

            List<LiveTestCase> liveTestCases = new ArrayList<>();
            for (TestScenario testScenario : scenarioTest.getScenarios()) {
                LiveTestCase liveTestCase = new LiveTestCase(CodeNamer.toCamelCase(testScenario.getScenario()),
                    testScenario.getDescription());
                List<LiveTestStep> liveTestSteps = new ArrayList<>();
                for (ScenarioStep scenarioStep : testScenario.getResolvedSteps()) {
                    // future work: support other step types, for now only support example file
                    if (scenarioStep.getType() != TestScenarioStepType.REST_CALL
                        || scenarioStep.getExampleFile() == null) {
                        continue;
                    }

                    Map<String, Object> example = new HashMap<>();
                    example.put("parameters", scenarioStep.getRequestParameters());
                    XmsExampleWrapper exampleWrapper = new XmsExampleWrapper(example, scenarioStep.getOperationId(),
                        scenarioStep.getExampleName());
                    ProxyMethodExample proxyMethodExample = Mappers.getProxyMethodExampleMapper().map(exampleWrapper);
                    liveTestSteps.add(ExampleLiveTestStep.newBuilder()
                        .operationId(scenarioStep.getOperationId())
                        .description(scenarioStep.getDescription())
                        .example(proxyMethodExample)
                        .build());
                }

                liveTestCase.addTestSteps(liveTestSteps);
            }

            liveTests.addTestCases(liveTestCases);
        }

        return liveTestsList;
    }

    private static String getFilename(String filePath) {
        String[] split = CodeNamer.linearReplace(filePath, "\\", "/").split("/");
        String filename = split[split.length - 1];
        filename = filename.split("\\.")[0];
        return CodeNamer.toPascalCase(filename);
    }
}
