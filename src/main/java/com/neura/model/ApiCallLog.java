package com.neura.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Api_Logs") 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiCallLog {
    @Id
    private String id;  

    private String method;
    private String path;
    private String requestBody;
    private String responseBody;
    private int statusCode;
    
    private LocalDateTime timestamp;
    private Map<String, String> headers ;
    private Map<String, String[]> parameters ;

}
