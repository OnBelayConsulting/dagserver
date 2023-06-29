package com.onbelay.dagserverapp.graphnode.filewriter;

import com.onbelay.dagserverlib.common.DagnabitSpringTestCase;
import com.onbelay.dagserverlib.graphnode.snapshot.GraphRelationshipSnapshot;
import com.onbelay.dagserverapp.graphnode.filewriter.GraphRelationshipFileWriter;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GraphRelationshipFileWriterTest extends DagnabitSpringTestCase {

    @Test
    public void writeRelationships() throws IOException {

        List<GraphRelationshipSnapshot> snapshots = new ArrayList<>();

        GraphRelationshipSnapshot snapshot = new GraphRelationshipSnapshot();
        snapshot.getFromNodeId().setCode("fred");
        snapshot.getToNodeId().setCode("janice");
        snapshot.getDetail().setType("parentOf");
        snapshot.getDetail().setWeight(2);
        snapshot.getDetail().setData("data");
        snapshots.add(snapshot);

        snapshot = new GraphRelationshipSnapshot();
        snapshot.getFromNodeId().setCode("ginger");
        snapshot.getToNodeId().setCode("fred");
        snapshot.getDetail().setType("spouse");
        snapshot.getDetail().setWeight(2);
        snapshot.getDetail().setData("data");
        snapshots.add(snapshot);

        GraphRelationshipFileWriter writer = new GraphRelationshipFileWriter(snapshots);
        byte[] contents = writer.getContents();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(contents);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String headerLine = reader.readLine();
        String contentLine = reader.readLine();
        assertEquals("FromNode,ToNode,Relationship,Weight,Data", headerLine);

        assertEquals("fred,janice,parentOf,2,data", contentLine);


    }

}
