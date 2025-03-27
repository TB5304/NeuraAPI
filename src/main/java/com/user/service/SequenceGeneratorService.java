package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.user.model.Sequence;

@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public long getNextSequence(String seqName) {
        Query query = new Query(Criteria.where("_id").is(seqName));
        Update update = new Update().inc("seq", 1);
        
        Sequence sequence = mongoOperations.findAndModify(
                query, update, 
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                Sequence.class
        );

        return sequence != null ? sequence.getSeq() : 1;  // Return updated sequence or default 1
    }
}
