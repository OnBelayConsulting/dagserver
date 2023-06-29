package com.onbelay.dagserverapp.graphnode.filereader;

import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.dagserverlib.enums.TransactionErrorCode;
import com.onbelay.dagserverlib.graphnode.snapshot.GraphNodeSnapshot;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GraphNodeFileReader extends GraphNodeFileHeader{
    private static final Logger logger = LogManager.getLogger();
    private InputStream streamIn;

    public GraphNodeFileReader(InputStream streamIn) {
        this.streamIn = streamIn;
    }

    public List<GraphNodeSnapshot> readFile() {

        ArrayList<GraphNodeSnapshot> snapshots = new ArrayList<>();

        try (InputStreamReader reader = new InputStreamReader(streamIn)) {

            CSVParser parser = new CSVParser(
                    reader,
                    CSVFormat.EXCEL.builder()
                            .setHeader(header)
                            .setSkipHeaderRecord(true)
                            .build());

            Iterable<CSVRecord> records = parser;

            for (CSVRecord record : records) {
                GraphNodeSnapshot snapshot = new GraphNodeSnapshot();
                snapshot.getDetail().setName(record.get("Name"));
                snapshot.getDetail().setCategory(record.get("Category"));

                String weightStr = record.get("Weight");
                weightStr = weightStr.trim();
                if (weightStr != null && weightStr.isBlank() == false) {
                    int weight = Integer.parseInt(weightStr);
                    snapshot.getDetail().setWeight(weight);
                }
                snapshot.getDetail().setData(record.get("Data"));
                snapshots.add(snapshot);
            }

            parser.close();

        } catch (IOException e) {
            logger.error("CSV file parsing read failed. ", e);
            throw new OBRuntimeException(TransactionErrorCode.INVALID_FILE.getCode());
        }

        return snapshots;
    }
}
