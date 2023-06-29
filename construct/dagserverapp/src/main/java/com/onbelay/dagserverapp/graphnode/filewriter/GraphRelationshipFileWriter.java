package com.onbelay.dagserverapp.graphnode.filewriter;

import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.dagserverlib.enums.TransactionErrorCode;
import com.onbelay.dagserverlib.graphnode.snapshot.GraphRelationshipSnapshot;
import com.onbelay.dagserverapp.graphnode.filereader.GraphRelationshipFileHeader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class GraphRelationshipFileWriter extends GraphRelationshipFileHeader {
    private static final Logger logger = LogManager.getLogger();

    private List<GraphRelationshipSnapshot> relationships = new ArrayList<>();

    public GraphRelationshipFileWriter(List<GraphRelationshipSnapshot> relationships) {
        this.relationships = relationships;
    }

    public List<GraphRelationshipSnapshot> getRelationships() {
        return relationships;
    }

    public byte[] getContents() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out)) {

            try (CSVPrinter printer = new CSVPrinter(
                    outputStreamWriter,
                    CSVFormat.EXCEL.builder().setHeader(header).build()) ) {

                for (GraphRelationshipSnapshot relationship : relationships) {
                    printer.printRecord(
                            relationship.getFromNodeId().getCode(),
                            relationship.getToNodeId().getCode(),
                            relationship.getDetail().getType(),
                            relationship.getDetail().getWeight(),
                            relationship.getDetail().getData());

                }
            } catch (IOException e) {
                logger.error("Writing relationship csv file failed with: ", e);
                throw new OBRuntimeException(TransactionErrorCode.RELATIONSHIP_FILE_WRITE_FAILED.getCode());
            }
        } catch (IOException e) {
            logger.error("Writing relationship csv file failed with: ", e);
            throw new OBRuntimeException(TransactionErrorCode.RELATIONSHIP_FILE_WRITE_FAILED.getCode());
        }

        return out.toByteArray();
    }
}
