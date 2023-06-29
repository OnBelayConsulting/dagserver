package com.onbelay.dagserverapp.graphnode.adapter;

import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.dagserverlib.common.DagnabitSpringTestCase;
import com.onbelay.dagserverlib.graphnode.model.GraphNode;
import com.onbelay.dagserverlib.graphnode.model.GraphNodeFixture;
import com.onbelay.dagserverlib.graphnode.model.GraphRelationship;
import com.onbelay.dagserverlib.graphnode.model.GraphRelationshipFixture;
import com.onbelay.dagserverlib.graphnode.repository.GraphRelationshipRepository;
import com.onbelay.dagserverlib.graphnode.snapshot.GraphRelationshipSnapshot;
import com.onbelay.dagserverapp.graphnode.adapter.GraphRelationshipRestAdapter;
import com.onbelay.dagserverapp.graphnode.snapshot.GraphRelationshipCollection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GraphRelationshipRestAdapterTest extends DagnabitSpringTestCase {

    @Autowired
    private GraphRelationshipRestAdapter graphRelationshipRestAdapter;

    @Autowired
    private GraphRelationshipRepository graphRelationshipRepository;

    private GraphNode firstNode;
    private GraphNode secondNode;


    public void setUp() {

        super.setUp();

        firstNode = GraphNodeFixture.createSavedGraphNode("firstNode");
        secondNode = GraphNodeFixture.createSavedGraphNode("secondNode");
        flush();

        GraphRelationshipFixture.createSavedRelationship(
                firstNode,
                secondNode,
                "specialRelationship");

        flush();
    }

    @Test
    public void fetchRelationshipsUsingFirstNode() {
        GraphRelationshipCollection collection = graphRelationshipRestAdapter.findGraphRelationships(
                0,
                10,
                "WHERE fromNodeName = 'firstNode'");

        assertEquals(1, collection.getSnapshots().size());
        GraphRelationshipSnapshot snapshot = collection.getSnapshots().get(0);
        assertEquals("firstNode", snapshot.getFromNodeId().getCode());
        assertEquals("secondNode", snapshot.getToNodeId().getCode());
        assertEquals("specialRelationship", snapshot.getDetail().getType());
    }

    @Test
    public void fetchRelationshipsUsingRelationshipType() {
        GraphRelationshipCollection collection = graphRelationshipRestAdapter.findGraphRelationships(
                0,
                10,
                "WHERE type = 'specialRelationship'");

        assertEquals(1, collection.getSnapshots().size());
        GraphRelationshipSnapshot snapshot = collection.getSnapshots().get(0);
        assertEquals("firstNode", snapshot.getFromNodeId().getCode());
        assertEquals("secondNode", snapshot.getToNodeId().getCode());
        assertEquals("specialRelationship", snapshot.getDetail().getType());
    }


    @Test
    public void createRelationship() {
        GraphRelationshipSnapshot snapshot = new GraphRelationshipSnapshot();
        snapshot.getDetail().setType("childOf");
        snapshot.setFromNodeId(firstNode.generateEntityId());
        snapshot.setToNodeId(secondNode.generateEntityId());
        snapshot.getDetail().setData("mydata");

        TransactionResult result = graphRelationshipRestAdapter.saveGraphRelationship(snapshot);
        assertTrue(result.isSuccessful());
        GraphRelationship graphRelationship = graphRelationshipRepository.load(result.getEntityId());
        assertEquals("childOf", graphRelationship.getDetail().getType());
        assertEquals("mydata", graphRelationship.getDetail().getData());
        assertEquals("firstNode", graphRelationship.getFromGraphNode().getDetail().getName());
        assertEquals("secondNode", graphRelationship.getToGraphNode().getDetail().getName());
    }

}
