package com.our_nacos.client.loadRestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.our_nacos.client.discovery.LoadbalanceURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Set;

public class MineRestTemplate extends RestTemplate {

    @Autowired(required = false)
    private LoadbalanceURL loadbalanceURL;

    @Override
    public <T> T getForObject(URI url, Class<T> responseType) throws RestClientException {
        return super.getForObject(getOurUri(url), responseType);
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.getForObject(getOurUri(url), responseType, uriVariables);
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.getForObject(getOurUri(url), responseType, uriVariables);
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.getForEntity(getOurUri(url), responseType, uriVariables);
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.getForEntity(getOurUri(url), responseType, uriVariables);
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) throws RestClientException {
        return super.getForEntity(getOurUri(url), responseType);
    }

    @Override
    public HttpHeaders headForHeaders(String url, Object... uriVariables) throws RestClientException {
        return super.headForHeaders(getOurUri(url), uriVariables);
    }

    @Override
    public HttpHeaders headForHeaders(String url, Map<String, ?> uriVariables) throws RestClientException {
        return super.headForHeaders(getOurUri(url), uriVariables);
    }

    @Override
    public HttpHeaders headForHeaders(URI url) throws RestClientException {
        return super.headForHeaders(getOurUri(url));
    }

    @Override
    public URI postForLocation(String url, Object request, Object... uriVariables) throws RestClientException {
        return super.postForLocation(getOurUri(url), request, uriVariables);
    }

    @Override
    public URI postForLocation(String url, Object request, Map<String, ?> uriVariables) throws RestClientException {
        return super.postForLocation(getOurUri(url), request, uriVariables);
    }

    @Override
    public URI postForLocation(URI url, Object request) throws RestClientException {
        return super.postForLocation(getOurUri(url), request);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.postForObject(getOurUri(url), request, responseType, uriVariables);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.postForObject(getOurUri(url), request, responseType, uriVariables);
    }

    @Override
    public <T> T postForObject(URI url, Object request, Class<T> responseType) throws RestClientException {
        return super.postForObject(getOurUri(url), request, responseType);
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.postForEntity(getOurUri(url), request, responseType, uriVariables);
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.postForEntity(getOurUri(url), request, responseType, uriVariables);
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(URI url, Object request, Class<T> responseType) throws RestClientException {
        return super.postForEntity(getOurUri(url), request, responseType);
    }

    @Override
    public <T> T patchForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return super.patchForObject(getOurUri(url), request, responseType, uriVariables);
    }

    @Override
    public <T> T patchForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.patchForObject(getOurUri(url), request, responseType, uriVariables);
    }

    @Override
    public <T> T patchForObject(URI url, Object request, Class<T> responseType) throws RestClientException {
        return super.patchForObject(getOurUri(url), request, responseType);
    }


    private URI getOurUri(URI url){
        //获取ServiceName
        String serviceName = url.getHost();
        //获取请求地址
        String rawPath = url.getRawPath();
        try {
            url = URI.create(loadbalanceURL.GetURl(serviceName,rawPath));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String getOurUri(String url){
        URI uri = URI.create(url);
        URI ourUri = getOurUri(uri);
        return ourUri.toString();
    }
}
