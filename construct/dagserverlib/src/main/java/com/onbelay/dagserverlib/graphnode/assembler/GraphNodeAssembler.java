package com.onbelay.dagserverlib.graphnode.assembler;

import com.onbelay.core.entity.assembler.EntityAssembler;
import com.onbelay.dagserverlib.graphnode.model.GraphNode;
import com.onbelay.dagserverlib.graphnode.snapshot.GraphNodeSnapshot;

import java.util.List;
import java.util.stream.Collectors;

public class GraphNodeAssembler extends EntityAssembler {

    public GraphNodeSnapshot assemble(GraphNode node) {
        GraphNodeSnapshot snapshot = new GraphNodeSnapshot();
        setEntityAttributes(node, snapshot);

        snapshot.getDetail().shallowCopyFrom(node.getDetail());
        return snapshot;
    }

    public List<GraphNodeSnapshot> assemble(List<GraphNode> nodes) {
        return nodes
                .stream()
                .map(c-> assemble(c))
                .collect(Collectors.toUnmodifiableList());
    }

}
