package com.neura.components;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.neura.model.ApiCallLog;
import com.neura.repo.CallRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiCallInterceptor implements HandlerInterceptor {

	@Autowired
    CallRepo callRepo;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		if (!(request instanceof ContentCachingRequestWrapper)) {
			request = new ContentCachingRequestWrapper(request);
		}
		String requestBody = getRequestBody((ContentCachingRequestWrapper) request);
	
		ApiCallLog logEntry=new ApiCallLog();
		logEntry.setMethod(request.getMethod());
		logEntry.setPath(request.getRequestURI());
		logEntry.setId(request.getRequestId());
		logEntry.setRequestBody(requestBody);
		try {
			callRepo.save(logEntry);
		} catch (Exception dbException) {
		}

		return true;
	}

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        return content.length > 0 ? new String(content, StandardCharsets.UTF_8) : "";
    }
}  