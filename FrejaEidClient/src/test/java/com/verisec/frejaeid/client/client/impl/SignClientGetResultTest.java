package com.verisec.frejaeid.client.client.impl;

import com.verisec.frejaeid.client.beans.general.BasicUserInfo;
import com.verisec.frejaeid.client.beans.common.RelyingPartyRequest;
import com.verisec.frejaeid.client.beans.general.SslSettings;
import com.verisec.frejaeid.client.beans.general.RequestedAttributes;
import com.verisec.frejaeid.client.beans.general.SsnUserInfo;
import com.verisec.frejaeid.client.beans.sign.get.SignResult;
import com.verisec.frejaeid.client.beans.sign.get.SignResultRequest;
import com.verisec.frejaeid.client.beans.sign.get.SignResultsRequest;
import com.verisec.frejaeid.client.beans.sign.get.SignResults;
import com.verisec.frejaeid.client.client.api.SignClientApi;
import com.verisec.frejaeid.client.client.util.TestUtil;
import com.verisec.frejaeid.client.enums.TransactionStatus;
import com.verisec.frejaeid.client.enums.Country;
import com.verisec.frejaeid.client.enums.FrejaEnvironment;
import com.verisec.frejaeid.client.enums.FrejaEidErrorCode;
import com.verisec.frejaeid.client.enums.TransactionContext;
import com.verisec.frejaeid.client.exceptions.FrejaEidException;
import com.verisec.frejaeid.client.exceptions.FrejaEidClientInternalException;
import com.verisec.frejaeid.client.exceptions.FrejaEidClientPollingException;
import com.verisec.frejaeid.client.http.HttpServiceApi;
import com.verisec.frejaeid.client.util.MethodUrl;
import com.verisec.frejaeid.client.util.RequestTemplate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class SignClientGetResultTest {

    private final HttpServiceApi httpServiceMock = Mockito.mock(HttpServiceApi.class);
    private static final String RELYING_PARTY_ID = "relyingPartyId";
    private static final String SIGN_REFERENCE = "123456789123456789";
    private static final String SIGN_DETAILS = "This is sign transaction";
    private static final String RELYING_PARTY_USER_ID = "relyingPartyUserId";
    private static final String EMAIL_ADDRESS = "test@frejaeid.com";
    private static final String ORGANISATION_ID = "orgId";
    private static final RequestedAttributes REQUESTED_ATTRIBUTES = new RequestedAttributes(new BasicUserInfo("name", "surname"), "customIdentifier", SsnUserInfo.create(Country.SWEDEN, "ssn"), "integratorSpecificId", "1987-10-18", RELYING_PARTY_USER_ID, EMAIL_ADDRESS, ORGANISATION_ID);

    @Test
    public void getSignResult_relyingPartyIdNull_expectSuccess() throws FrejaEidClientInternalException, FrejaEidException {
        SignResultRequest signResultsRequest = SignResultRequest.create(SIGN_REFERENCE);
        SignResult expectedResponse = new SignResult(SIGN_REFERENCE, TransactionStatus.STARTED, SIGN_DETAILS, REQUESTED_ATTRIBUTES);
        SignClientApi signClient = SignClient.create(SslSettings.create(TestUtil.getKeystorePath(TestUtil.KEYSTORE_PATH), TestUtil.KEYSTORE_PASSWORD, TestUtil.getKeystorePath(TestUtil.CERTIFICATE_PATH)), FrejaEnvironment.TEST)
                .setHttpService(httpServiceMock)
                .setTransactionContext(TransactionContext.PERSONAL).build();
        Mockito.when(httpServiceMock.send(Mockito.anyString(), Mockito.any(RequestTemplate.class), Mockito.any(RelyingPartyRequest.class), Mockito.eq(SignResult.class), (String) Mockito.isNull())).thenReturn(expectedResponse);
        SignResult response = signClient.getResult(signResultsRequest);
        Mockito.verify(httpServiceMock).send(FrejaEnvironment.TEST.getUrl() + MethodUrl.SIGN_GET_RESULT, RequestTemplate.SIGN_RESULT_TEMPLATE, signResultsRequest, SignResult.class, null);
        Assert.assertEquals(SIGN_REFERENCE, response.getSignRef());
        Assert.assertEquals(TransactionStatus.STARTED, response.getStatus());
        Assert.assertEquals(SIGN_DETAILS, response.getDetails());
        Assert.assertEquals(REQUESTED_ATTRIBUTES, response.getRequestedAttributes());
    }

    @Test
    public void getSignResultPersonal_expectSuccess() throws FrejaEidClientInternalException, FrejaEidException {
        SignResultRequest signResultRequest = SignResultRequest.create(SIGN_REFERENCE, RELYING_PARTY_ID);
        SignResult expectedResponse = new SignResult(SIGN_REFERENCE, TransactionStatus.STARTED, SIGN_DETAILS, REQUESTED_ATTRIBUTES);
        SignClientApi signClient = SignClient.create(SslSettings.create(TestUtil.getKeystorePath(TestUtil.KEYSTORE_PATH), TestUtil.KEYSTORE_PASSWORD, TestUtil.getKeystorePath(TestUtil.CERTIFICATE_PATH)), FrejaEnvironment.TEST)
                .setHttpService(httpServiceMock)
                .setTransactionContext(TransactionContext.PERSONAL).build();
        Mockito.when(httpServiceMock.send(Mockito.anyString(), Mockito.any(RequestTemplate.class), Mockito.any(RelyingPartyRequest.class), Mockito.eq(SignResult.class), Mockito.anyString())).thenReturn(expectedResponse);
        SignResult response = signClient.getResult(signResultRequest);
        Mockito.verify(httpServiceMock).send(FrejaEnvironment.TEST.getUrl() + MethodUrl.SIGN_GET_RESULT, RequestTemplate.SIGN_RESULT_TEMPLATE, signResultRequest, SignResult.class, RELYING_PARTY_ID);
        Assert.assertEquals(SIGN_REFERENCE, response.getSignRef());
        Assert.assertEquals(TransactionStatus.STARTED, response.getStatus());
        Assert.assertEquals(SIGN_DETAILS, response.getDetails());
        Assert.assertEquals(REQUESTED_ATTRIBUTES, response.getRequestedAttributes());
    }

    @Test
    public void getSignResultOrganisational_expectSuccess() throws FrejaEidClientInternalException, FrejaEidException {
        SignResultRequest signResultRequest = SignResultRequest.create(SIGN_REFERENCE, RELYING_PARTY_ID);
        SignResult expectedResponse = new SignResult(SIGN_REFERENCE, TransactionStatus.STARTED, SIGN_DETAILS, REQUESTED_ATTRIBUTES);
        SignClientApi signClient = SignClient.create(SslSettings.create(TestUtil.getKeystorePath(TestUtil.KEYSTORE_PATH), TestUtil.KEYSTORE_PASSWORD, TestUtil.getKeystorePath(TestUtil.CERTIFICATE_PATH)), FrejaEnvironment.TEST)
                .setHttpService(httpServiceMock)
                .setTransactionContext(TransactionContext.ORGANISATIONAL).build();
        Mockito.when(httpServiceMock.send(Mockito.anyString(), Mockito.any(RequestTemplate.class), Mockito.any(RelyingPartyRequest.class), Mockito.eq(SignResult.class), Mockito.anyString())).thenReturn(expectedResponse);
        SignResult response = signClient.getResult(signResultRequest);
        Mockito.verify(httpServiceMock).send(FrejaEnvironment.TEST.getUrl() + MethodUrl.ORGANISATION_SIGN_GET_ONE_RESULT, RequestTemplate.SIGN_RESULT_TEMPLATE, signResultRequest, SignResult.class, RELYING_PARTY_ID);
        Assert.assertEquals(SIGN_REFERENCE, response.getSignRef());
        Assert.assertEquals(TransactionStatus.STARTED, response.getStatus());
        Assert.assertEquals(SIGN_DETAILS, response.getDetails());
        Assert.assertEquals(REQUESTED_ATTRIBUTES, response.getRequestedAttributes());
    }

    @Test
    public void getSignResult_invalidReference_expectInvalidReferenceError() throws FrejaEidClientInternalException, FrejaEidException {
        SignResultRequest signResultRequest = SignResultRequest.create(SIGN_REFERENCE, RELYING_PARTY_ID);
        try {
            SignClientApi signClient = SignClient.create(SslSettings.create(TestUtil.getKeystorePath(TestUtil.KEYSTORE_PATH), TestUtil.KEYSTORE_PASSWORD, TestUtil.getKeystorePath(TestUtil.CERTIFICATE_PATH)), FrejaEnvironment.TEST)
                    .setHttpService(httpServiceMock)
                    .setTransactionContext(TransactionContext.PERSONAL).build();
            Mockito.when(httpServiceMock.send(Mockito.anyString(), Mockito.any(RequestTemplate.class), Mockito.any(RelyingPartyRequest.class), Mockito.eq(SignResult.class), Mockito.anyString())).thenThrow(new FrejaEidException(FrejaEidErrorCode.INVALID_REFERENCE.getMessage(), FrejaEidErrorCode.INVALID_REFERENCE.getCode()));
            SignResult response = signClient.getResult(signResultRequest);
            Assert.fail("Test should throw exception!");
        } catch (FrejaEidException rpEx) {
            Mockito.verify(httpServiceMock).send(FrejaEnvironment.TEST.getUrl() + MethodUrl.SIGN_GET_RESULT, RequestTemplate.SIGN_RESULT_TEMPLATE, signResultRequest, SignResult.class, RELYING_PARTY_ID);
            Assert.assertEquals(1100, rpEx.getErrorCode());
            Assert.assertEquals("Invalid reference (for example, nonexistent or expired).", rpEx.getLocalizedMessage());
        }
    }

    @Test
    public void pollForResult_finalResponseRejected_success() throws FrejaEidClientInternalException, FrejaEidException, FrejaEidClientPollingException {
        SignResult expectedResponse = new SignResult(SIGN_REFERENCE, TransactionStatus.REJECTED, SIGN_DETAILS, null);
        SignClientApi signClient = SignClient.create(SslSettings.create(TestUtil.getKeystorePath(TestUtil.KEYSTORE_PATH), TestUtil.KEYSTORE_PASSWORD, TestUtil.getKeystorePath(TestUtil.CERTIFICATE_PATH)), FrejaEnvironment.TEST)
                .setHttpService(httpServiceMock)
                .setTransactionContext(TransactionContext.PERSONAL).build();
        Mockito.when(httpServiceMock.send(Mockito.anyString(), Mockito.any(RequestTemplate.class), Mockito.any(RelyingPartyRequest.class), Mockito.eq(SignResult.class), (String) Mockito.isNull())).thenReturn(expectedResponse);
        SignResultRequest signResultRequest = SignResultRequest.create(SIGN_REFERENCE);
        SignResult response = signClient.pollForResult(signResultRequest, 10000);
        Mockito.verify(httpServiceMock).send(FrejaEnvironment.TEST.getUrl() + MethodUrl.SIGN_GET_RESULT, RequestTemplate.SIGN_RESULT_TEMPLATE, signResultRequest, SignResult.class, null);
        Assert.assertEquals(TransactionStatus.REJECTED, response.getStatus());
    }

    @Test
    public void pollForResult_requestTimeout_expectTimeoutError() throws FrejaEidClientInternalException, FrejaEidException {
        try {
            SignClientApi signClient = SignClient.create(SslSettings.create(TestUtil.getKeystorePath(TestUtil.KEYSTORE_PATH), TestUtil.KEYSTORE_PASSWORD, TestUtil.getKeystorePath(TestUtil.CERTIFICATE_PATH)), FrejaEnvironment.TEST)
                    .setHttpService(httpServiceMock)
                    .setTransactionContext(TransactionContext.PERSONAL).build();
            SignResult response = signClient.pollForResult(SignResultRequest.create(SIGN_REFERENCE), 2);
            Assert.fail("Test should throw exception!");
        } catch (FrejaEidClientPollingException ex) {
            Assert.assertEquals("A timeout of 2s was reached while sending request.", ex.getLocalizedMessage());
        }
    }

    @Test
    public void getSignResults_relyingPartyIdNull_expectSuccess() throws FrejaEidClientInternalException, FrejaEidException, FrejaEidClientPollingException {
        SignResults expectedResponse = prepareResponse();
        Mockito.when(httpServiceMock.send(Mockito.anyString(), Mockito.any(RequestTemplate.class), Mockito.any(RelyingPartyRequest.class), Mockito.eq(SignResults.class), (String) Mockito.isNull())).thenReturn(expectedResponse);
        SignResultsRequest getSignResultsRequest = SignResultsRequest.create();
        getSignResults_success(getSignResultsRequest);

    }

    @Test
    public void getSignResults_relyingPartyIdNotNull_expectSuccess() throws FrejaEidClientInternalException, FrejaEidException, FrejaEidClientPollingException {
        SignResults expectedResponse = prepareResponse();
        Mockito.when(httpServiceMock.send(Mockito.anyString(), Mockito.any(RequestTemplate.class), Mockito.any(RelyingPartyRequest.class), Mockito.eq(SignResults.class), Mockito.anyString())).thenReturn(expectedResponse);
        SignResultsRequest signResultsRequest = SignResultsRequest.create(RELYING_PARTY_ID);
        getSignResults_success(signResultsRequest);

    }

    @Test
    public void getSignResults_expectError() throws FrejaEidClientInternalException, FrejaEidException, FrejaEidClientPollingException {
        SignClientApi signClient = SignClient.create(SslSettings.create(TestUtil.getKeystorePath(TestUtil.KEYSTORE_PATH), TestUtil.KEYSTORE_PASSWORD, TestUtil.getKeystorePath(TestUtil.CERTIFICATE_PATH)), FrejaEnvironment.TEST)
                .setHttpService(httpServiceMock)
                .setTransactionContext(TransactionContext.PERSONAL).build();
        Mockito.when(httpServiceMock.send(Mockito.anyString(), Mockito.any(RequestTemplate.class), Mockito.any(RelyingPartyRequest.class), Mockito.eq(SignResults.class), Mockito.anyString())).thenThrow(new FrejaEidException(FrejaEidErrorCode.UNKNOWN_RELYING_PARTY.getMessage(), FrejaEidErrorCode.UNKNOWN_RELYING_PARTY.getCode()));
        SignResultsRequest signResultsRequest = SignResultsRequest.create(RELYING_PARTY_ID);
        try {
            signClient.getResults(signResultsRequest);
            Assert.fail("Test should throw exception!");
        } catch (FrejaEidException rpEx) {
            Mockito.verify(httpServiceMock).send(FrejaEnvironment.TEST.getUrl() + MethodUrl.SIGN_GET_RESULTS, RequestTemplate.SIGN_RESULTS_TEMPLATE, signResultsRequest, SignResults.class, RELYING_PARTY_ID);
            Assert.assertEquals(1008, rpEx.getErrorCode());
            Assert.assertEquals("Unknown Relying party.", rpEx.getLocalizedMessage());
        }

    }

    private void getSignResults_success(SignResultsRequest signResultsRequest) throws FrejaEidClientInternalException, FrejaEidException, FrejaEidClientPollingException {

        SignClientApi signClient = SignClient.create(SslSettings.create(TestUtil.getKeystorePath(TestUtil.KEYSTORE_PATH), TestUtil.KEYSTORE_PASSWORD, TestUtil.getKeystorePath(TestUtil.CERTIFICATE_PATH)), FrejaEnvironment.TEST)
                .setHttpService(httpServiceMock)
                .setTransactionContext(TransactionContext.PERSONAL).build();

        List<SignResult> response = signClient.getResults(signResultsRequest);
        Mockito.verify(httpServiceMock).send(FrejaEnvironment.TEST.getUrl() + MethodUrl.SIGN_GET_RESULTS, RequestTemplate.SIGN_RESULTS_TEMPLATE, signResultsRequest, SignResults.class, signResultsRequest.getRelyingPartyId());

        SignResult firstResponse = response.get(0);
        Assert.assertEquals(SIGN_REFERENCE, firstResponse.getSignRef());
        Assert.assertEquals(TransactionStatus.STARTED, firstResponse.getStatus());
        Assert.assertEquals(SIGN_DETAILS, firstResponse.getDetails());
        Assert.assertEquals(REQUESTED_ATTRIBUTES, firstResponse.getRequestedAttributes());

        SignResult secondResponse = response.get(1);
        Assert.assertEquals(SIGN_REFERENCE, secondResponse.getSignRef());
        Assert.assertEquals(TransactionStatus.DELIVERED_TO_MOBILE, secondResponse.getStatus());
        Assert.assertEquals("test", secondResponse.getDetails());
        Assert.assertNull(secondResponse.getRequestedAttributes());
    }

    private SignResults prepareResponse() {
        SignResult response1 = new SignResult(SIGN_REFERENCE, TransactionStatus.STARTED, SIGN_DETAILS, REQUESTED_ATTRIBUTES);
        SignResult response2 = new SignResult(SIGN_REFERENCE, TransactionStatus.DELIVERED_TO_MOBILE, "test", null);
        List<SignResult> responses = new ArrayList<>();
        responses.add(response1);
        responses.add(response2);
        return new SignResults(responses);
    }
}