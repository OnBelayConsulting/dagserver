package com.onbelay.dagserverapp.graphnode.subscribe.converter;

import com.onbelay.dagserverlib.graphnode.snapshot.GraphNodeSnapshot;
import com.onbelay.dagserverapp.graphnode.subscribe.snapshot.SubGraphNodeSnapshot;

import java.util.List;
import java.util.stream.Collectors;

public class GraphNodeConverter {


    public List<GraphNodeSnapshot> convert(List<SubGraphNodeSnapshot> snapshotsIn) {
        return snapshotsIn
                .stream()
                .map( c-> convert(c))
                .collect(Collectors.toUnmodifiableList());
    }


    public GraphNodeSnapshot convert(SubGraphNodeSnapshot snapshotIn) {
        GraphNodeSnapshot snapshot = new GraphNodeSnapshot();
        snapshot.setEntityState(snapshotIn.getEntityState());
        snapshot.getDetail().setExternalReferenceId(snapshotIn.getDetail().getExternalReferenceId());

        snapshot.getDetail().setName(snapshotIn.getDetail().getName());
        snapshot.getDetail().setCategory(snapshotIn.getDetail().getCategory());
        snapshot.getDetail().setData(snapshotIn.getDetail().getData());
        snapshot.getDetail().setWeight(snapshotIn.getDetail().getWeight());
        return snapshot;
    }

}
