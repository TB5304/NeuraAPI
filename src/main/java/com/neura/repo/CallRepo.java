package com.neura.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.neura.model.ApiCallLog;

public interface CallRepo extends MongoRepository<ApiCallLog, String> {
}
