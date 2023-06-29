package com.onbelay.dagserverapp.graphnode.filereader;

import com.onbelay.dagserverlib.common.DagnabitSpringTestCase;
import com.onbelay.dagserverlib.graphnode.snapshot.GraphNodeSnapshot;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GraphNodeFileReaderTest extends DagnabitSpringTestCase  {

    @Test
    public void readNodes() throws IOException {

        List<GraphNodeSnapshot> nodes;
        try (InputStream stream = GraphNodeFileReaderTest.class.getResourceAsStream("/node_file_example.csv")) {
            GraphNodeFileReader reader = new GraphNodeFileReader(stream);
            nodes = reader.readFile();
        }
        assertEquals(4, nodes.size());
        GraphNodeSnapshot first = nodes.get(0);
        assertEquals("FatherSmith", first.getDetail().getName());
        assertEquals("family", first.getDetail().getCategory());
        assertEquals(1, first.getDetail().getWeight().intValue());
        assertEquals("{firstName=John; lastName=Smith}", first.getDetail().getData());

    }

}
