package com.onbelay.dagserverapp.dagmodel.adapter;

import com.onbelay.dagserverapp.dagmodel.snapshot.*;

import java.util.List;

public interface DagModelRestAdapter {

    ModelResult createModel(DagModelSnapshot snapshot);


    DagModelCollection findGraphModels(
            int start,
            int limit,
            String filter);

    ModelResult addNodes(
            String modelName,
            List<DagNodeSnapshot> snapshots);

    ModelResult addRelationships(
            String modelName,
            List<DagRelationshipSnapshot> relationships);

    /**
     * Return a report that documents any cycles in the model.
     * @param modelName = identifies an existing model.
     * @return
     */
    DagNodePathCollection fetchCycleReport(
            String modelName,
            String relationshipName);

    DagNodeCollection findDescendants(
            String modelName,
            String nodeRootName,
            String relationship,
            int start,
            int limit);

    DagNodeCollection findRootNodes(
            String modelName,
            int start,
            int limit);
}
