package com.onbelay.dagserverapp.graphnode.adapter;

import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.dagserverlib.graphnode.snapshot.GraphNodeSnapshot;
import com.onbelay.dagserverapp.graphnode.snapshot.FileResult;
import com.onbelay.dagserverapp.graphnode.snapshot.GraphNodeCollection;

import java.util.List;

public interface GraphNodeRestAdapter {

    TransactionResult saveGraphNode(GraphNodeSnapshot snapshot);

    TransactionResult saveGraphNodes(List<GraphNodeSnapshot> snapshots);

    GraphNodeCollection findGraphNodes(
            int start,
            int limit,
            String query);

    TransactionResult uploadFile(
            String name,
            byte[] bytes);

    FileResult generateCSVFile(String query);
}
