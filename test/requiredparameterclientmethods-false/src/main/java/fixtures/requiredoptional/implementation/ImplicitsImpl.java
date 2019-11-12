// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.requiredoptional.implementation;

import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.http.rest.VoidResponse;
import com.azure.core.implementation.RestProxy;
import com.azure.core.implementation.annotation.BodyParam;
import com.azure.core.implementation.annotation.ExpectedResponses;
import com.azure.core.implementation.annotation.Get;
import com.azure.core.implementation.annotation.HeaderParam;
import com.azure.core.implementation.annotation.Host;
import com.azure.core.implementation.annotation.PathParam;
import com.azure.core.implementation.annotation.Put;
import com.azure.core.implementation.annotation.QueryParam;
import com.azure.core.implementation.annotation.ReturnType;
import com.azure.core.implementation.annotation.ServiceInterface;
import com.azure.core.implementation.annotation.ServiceMethod;
import com.azure.core.implementation.annotation.UnexpectedResponseExceptionType;
import fixtures.requiredoptional.Implicits;
import fixtures.requiredoptional.models.Error;
import fixtures.requiredoptional.models.ErrorException;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * Implicits.
 */
public final class ImplicitsImpl implements Implicits {
    /**
     * The proxy service used to perform REST calls.
     */
    private ImplicitsService service;

    /**
     * The service client containing this operation class.
     */
    private AutoRestRequiredOptionalTestServiceImpl client;

    /**
     * Initializes an instance of ImplicitsImpl.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public ImplicitsImpl(AutoRestRequiredOptionalTestServiceImpl client) {
        this.service = RestProxy.create(ImplicitsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * AutoRestRequiredOptionalTestServiceImplicits to be used by the proxy
     * service to perform REST calls.
     */
    @Host("http://localhost:3000")
    @ServiceInterface(name = "AutoRestRequiredOptionalTestServiceImplicits")
    private interface ImplicitsService {
        @Get("reqopt/implicit/required/path/{pathParameter}")
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<Error>> getRequiredPath(@PathParam("pathParameter") String pathParameter);

        @Put("reqopt/implicit/optional/query")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> putOptionalQuery(@QueryParam("queryParameter") String queryParameter);

        @Put("reqopt/implicit/optional/header")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> putOptionalHeader(@HeaderParam("queryParameter") String queryParameter);

        @Put("reqopt/implicit/optional/body")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> putOptionalBody(@BodyParam("application/json; charset=utf-8") String bodyParameter);

        @Get("reqopt/global/required/path/{required-global-path}")
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<Error>> getRequiredGlobalPath(@PathParam("required-global-path") String requiredGlobalPath);

        @Get("reqopt/global/required/query")
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<Error>> getRequiredGlobalQuery(@QueryParam("required-global-query") String requiredGlobalQuery);

        @Get("reqopt/global/optional/query")
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<Error>> getOptionalGlobalQuery(@QueryParam("optional-global-query") Integer optionalGlobalQuery);
    }

    /**
     * Test implicitly required path parameter.
     *
     * @param pathParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the Error object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Error getRequiredPath(String pathParameter) {
        return getRequiredPathAsync(pathParameter).block();
    }

    /**
     * Test implicitly required path parameter.
     *
     * @param pathParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Error>> getRequiredPathWithRestResponseAsync(String pathParameter) {
        return service.getRequiredPath(pathParameter);
    }

    /**
     * Test implicitly required path parameter.
     *
     * @param pathParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Error> getRequiredPathAsync(String pathParameter) {
        return getRequiredPathWithRestResponseAsync(pathParameter)
            .flatMap((SimpleResponse<Error> res) -> Mono.just(res.value()));
    }

    /**
     * Test implicitly optional query parameter.
     *
     * @param queryParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void putOptionalQuery(String queryParameter) {
        putOptionalQueryAsync(queryParameter).block();
    }

    /**
     * Test implicitly optional query parameter.
     *
     * @param queryParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> putOptionalQueryWithRestResponseAsync(String queryParameter) {
        return service.putOptionalQuery(queryParameter);
    }

    /**
     * Test implicitly optional query parameter.
     *
     * @param queryParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> putOptionalQueryAsync(String queryParameter) {
        return putOptionalQueryWithRestResponseAsync(queryParameter)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Test implicitly optional header parameter.
     *
     * @param queryParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void putOptionalHeader(String queryParameter) {
        putOptionalHeaderAsync(queryParameter).block();
    }

    /**
     * Test implicitly optional header parameter.
     *
     * @param queryParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> putOptionalHeaderWithRestResponseAsync(String queryParameter) {
        return service.putOptionalHeader(queryParameter);
    }

    /**
     * Test implicitly optional header parameter.
     *
     * @param queryParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> putOptionalHeaderAsync(String queryParameter) {
        return putOptionalHeaderWithRestResponseAsync(queryParameter)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Test implicitly optional body parameter.
     *
     * @param bodyParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void putOptionalBody(String bodyParameter) {
        putOptionalBodyAsync(bodyParameter).block();
    }

    /**
     * Test implicitly optional body parameter.
     *
     * @param bodyParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> putOptionalBodyWithRestResponseAsync(String bodyParameter) {
        return service.putOptionalBody(bodyParameter);
    }

    /**
     * Test implicitly optional body parameter.
     *
     * @param bodyParameter the String value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> putOptionalBodyAsync(String bodyParameter) {
        return putOptionalBodyWithRestResponseAsync(bodyParameter)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Test implicitly required path parameter.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the Error object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Error getRequiredGlobalPath() {
        return getRequiredGlobalPathAsync().block();
    }

    /**
     * Test implicitly required path parameter.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Error>> getRequiredGlobalPathWithRestResponseAsync() {
        return service.getRequiredGlobalPath(this.client.getRequiredGlobalPath());
    }

    /**
     * Test implicitly required path parameter.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Error> getRequiredGlobalPathAsync() {
        return getRequiredGlobalPathWithRestResponseAsync()
            .flatMap((SimpleResponse<Error> res) -> Mono.just(res.value()));
    }

    /**
     * Test implicitly required query parameter.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the Error object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Error getRequiredGlobalQuery() {
        return getRequiredGlobalQueryAsync().block();
    }

    /**
     * Test implicitly required query parameter.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Error>> getRequiredGlobalQueryWithRestResponseAsync() {
        return service.getRequiredGlobalQuery(this.client.getRequiredGlobalQuery());
    }

    /**
     * Test implicitly required query parameter.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Error> getRequiredGlobalQueryAsync() {
        return getRequiredGlobalQueryWithRestResponseAsync()
            .flatMap((SimpleResponse<Error> res) -> Mono.just(res.value()));
    }

    /**
     * Test implicitly optional query parameter.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the Error object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Error getOptionalGlobalQuery() {
        return getOptionalGlobalQueryAsync().block();
    }

    /**
     * Test implicitly optional query parameter.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Error>> getOptionalGlobalQueryWithRestResponseAsync() {
        return service.getOptionalGlobalQuery(this.client.getOptionalGlobalQuery());
    }

    /**
     * Test implicitly optional query parameter.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Error> getOptionalGlobalQueryAsync() {
        return getOptionalGlobalQueryWithRestResponseAsync()
            .flatMap((SimpleResponse<Error> res) -> Mono.just(res.value()));
    }
}