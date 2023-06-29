package com.onbelay.dagserverlib.graphnode.model;

import com.onbelay.dagserverlib.graphnode.snapshot.GraphRelationshipSnapshot;

public class GraphRelationshipFixture {

    public static GraphRelationship createSavedRelationship(
            GraphNode fromNode,
            GraphNode toNode,
            String relationship) {

        GraphRelationshipSnapshot snapshot = new GraphRelationshipSnapshot();
        snapshot.setFromNodeId(fromNode.generateEntityId());
        snapshot.setToNodeId(toNode.generateEntityId());
        snapshot.getDetail().setType(relationship);

        GraphRelationship graphRelationship = new GraphRelationship();
        graphRelationship.createWith(snapshot);
        return graphRelationship;
    }
}
