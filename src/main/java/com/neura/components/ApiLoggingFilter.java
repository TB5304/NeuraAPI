package com.neura.components;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neura.model.ApiCallLog;
import com.neura.repo.CallRepo;

@Component
public class ApiLoggingFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private final CallRepo callRepo;

    public ApiLoggingFilter(CallRepo callRepo) {
        this.callRepo = callRepo;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);
        long startTime = System.currentTimeMillis();
        chain.doFilter(wrappedRequest, wrappedResponse);
        long duration = System.currentTimeMillis() - startTime;
        logRequestResponse(wrappedRequest, wrappedResponse, duration);
        wrappedResponse.copyBodyToResponse(); // Ensure response is sent correctly
    }

    private void logRequestResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long duration) throws IOException {
        ApiCallLog logEntry = new ApiCallLog();

        logEntry.setMethod(request.getMethod());
        logEntry.setPath(request.getRequestURI());
        logEntry.setTimestamp(LocalDateTime.now().toString());
        logEntry.setHeaders(objectMapper.writeValueAsString(getHeaders(request))); // Headers as JSON
        logEntry.setQueryParams(objectMapper.writeValueAsString(request.getParameterMap())); // Query Params as JSON

        String requestBody = getContentAsString(request.getContentAsByteArray());
        logEntry.setRequestBody(requestBody.isEmpty() ? "{}" : requestBody);

        String responseBody = getContentAsString(response.getContentAsByteArray());
        logEntry.setResponseBody(responseBody.isEmpty() ? "{}" : responseBody);
        logEntry.setStatusCode(response.getStatus());
        logEntry.setResponseHeaders(objectMapper.writeValueAsString(getResponseHeaders(response))); // Response Headers
        logEntry.setTimetaken(duration);

        callRepo.save(logEntry);
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headersMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames != null && headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headersMap.put(headerName, request.getHeader(headerName));
        }
        return headersMap;
    }

    private Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Map<String, String> headersMap = new HashMap<>();
        for (String headerName : response.getHeaderNames()) {
            headersMap.put(headerName, response.getHeader(headerName));
        }
        return headersMap;
    }

    private String getContentAsString(byte[] content) {
        if (content == null || content.length == 0) {
            return "{}";
        }
        return new String(content, StandardCharsets.UTF_8);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
