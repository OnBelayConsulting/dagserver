package com.onbelay.dagserverapp.graphnode.adapter;

import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.dagserverlib.graphnode.snapshot.GraphRelationshipSnapshot;
import com.onbelay.dagserverapp.graphnode.snapshot.FileResult;
import com.onbelay.dagserverapp.graphnode.snapshot.GraphRelationshipCollection;

import java.util.List;

public interface GraphRelationshipRestAdapter {

    TransactionResult saveGraphRelationship(GraphRelationshipSnapshot snapshot);

    TransactionResult saveGraphRelationships(List<GraphRelationshipSnapshot> snapshots);


    TransactionResult uploadFile(
            String name,
            byte[] bytes);


    GraphRelationshipCollection findGraphRelationships(
            int start,
            int limit,
            String query);

    FileResult generateCSVFile(String query);
}
