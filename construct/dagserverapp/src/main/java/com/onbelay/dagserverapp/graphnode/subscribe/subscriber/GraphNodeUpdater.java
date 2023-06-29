package com.onbelay.dagserverapp.graphnode.subscribe.subscriber;

import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.dagserverapp.graphnode.subscribe.snapshot.SubGraphNodeSnapshot;

import java.util.List;

public interface GraphNodeUpdater {

    TransactionResult updateGraphNodes(List<SubGraphNodeSnapshot> nodesIn);



}
