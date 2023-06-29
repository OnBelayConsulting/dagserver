package com.onbelay.dagserverapp.graphnode.adapter;

import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.dagserverlib.common.DagnabitSpringTestCase;
import com.onbelay.dagserverlib.graphnode.model.GraphNode;
import com.onbelay.dagserverlib.graphnode.model.GraphNodeFixture;
import com.onbelay.dagserverlib.graphnode.repository.GraphNodeRepository;
import com.onbelay.dagserverlib.graphnode.snapshot.GraphNodeSnapshot;
import com.onbelay.dagserverapp.graphnode.adapter.GraphNodeRestAdapter;
import com.onbelay.dagserverapp.graphnode.snapshot.GraphNodeCollection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;

@WithMockUser
public class GraphNodeRestAdapterTest extends DagnabitSpringTestCase {

    @Autowired
    private GraphNodeRestAdapter graphNodeRestAdapter;

    @Autowired
    private GraphNodeRepository graphNodeRepository;

    private GraphNode firstNode;

    @Override
    public void setUp() {
        super.setUp();

        firstNode = GraphNodeFixture.createSavedGraphNode("HarryNode");
        flush();
    }

    @Test
    public void createNodes() {
        ArrayList<GraphNodeSnapshot> snapshots = new ArrayList<>();

        GraphNodeSnapshot node = new GraphNodeSnapshot();
        node.getDetail().setName("MyMyNode");
        node.getDetail().setCategory("MyCategory");
        node.getDetail().setData("data");
        snapshots.add(node);

        TransactionResult result = graphNodeRestAdapter.saveGraphNodes(snapshots);
        assertEquals(1, result.getEntityIds().size());
        GraphNode saved = graphNodeRepository.findByName("MyMyNode");
        assertNotNull(saved);
    }

    @Test
    public void findNodes() {
        GraphNodeCollection collection = graphNodeRestAdapter.findGraphNodes(0, 100, "WHERE name like 'H%'");
        assertEquals(1, collection.getSnapshots().size());
    }
}
