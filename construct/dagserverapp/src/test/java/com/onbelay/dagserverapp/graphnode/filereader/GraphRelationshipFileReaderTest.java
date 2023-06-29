package com.onbelay.dagserverapp.graphnode.filereader;

import com.onbelay.dagserverlib.common.DagnabitSpringTestCase;
import com.onbelay.dagserverlib.graphnode.snapshot.GraphRelationshipSnapshot;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GraphRelationshipFileReaderTest extends DagnabitSpringTestCase  {

    @Test
    public void readRelationships() throws IOException {

        List<GraphRelationshipSnapshot> relationships;
        try (InputStream stream = GraphRelationshipFileReaderTest.class.getResourceAsStream("/relationship_file_example.csv")) {
            GraphRelationshipFileReader reader = new GraphRelationshipFileReader(stream);
            relationships = reader.readFile();
        }
        assertEquals(5, relationships.size());

        GraphRelationshipSnapshot first = relationships.get(0);
        assertEquals("FatherSmith", first.getFromNodeId().getCode());
        assertEquals("JaneSmith", first.getToNodeId().getCode());
        assertEquals("ParentOf", first.getDetail().getType());


        GraphRelationshipSnapshot last = relationships.get(4);
        assertEquals("MotherSmith", last.getFromNodeId().getCode());
        assertEquals("FatherSmith", last.getToNodeId().getCode());
        assertEquals("spouse", last.getDetail().getType());


    }

}
