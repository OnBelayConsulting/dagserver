package com.onbelay.dagserverlib.graphnode.repository;

import com.onbelay.core.query.enums.ExpressionOperator;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.DefinedWhereExpression;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.dagserverlib.common.DagnabitSpringTestCase;
import com.onbelay.dagserverlib.graphnode.model.GraphNode;
import com.onbelay.dagserverlib.graphnode.model.GraphNodeFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GraphNodeRepositoryTest extends DagnabitSpringTestCase {

    @Autowired
    private GraphNodeRepository graphNodeRepository;

    private GraphNode fromGraphNode;
    private GraphNode toGraphNode;


    public void setUp() {
        super.setUp();
        fromGraphNode = GraphNodeFixture.createSavedGraphNode("from");
        toGraphNode = GraphNodeFixture.createSavedGraphNode("to");
        flush();
    }


    @Test
    public void FindByName() {

        GraphNode graphNode = graphNodeRepository.findByName("from");
        assertNotNull(graphNode);
    }


    @Test
    public void FindByQuery() {

        DefinedQuery definedQuery = new DefinedQuery("GraphNode");
        definedQuery.getWhereClause().addExpression(
                new DefinedWhereExpression("name", ExpressionOperator.EQUALS, "from")
        );

        List<Integer> ids = graphNodeRepository.findGraphNodeIds(definedQuery);
        List<GraphNode> nodes = graphNodeRepository.fetchByIds(new QuerySelectedPage(ids));
        assertEquals(1, nodes.size());
    }


}
