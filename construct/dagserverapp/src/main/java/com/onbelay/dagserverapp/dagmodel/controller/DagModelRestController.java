package com.onbelay.dagserverapp.dagmodel.controller;

import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.dagserverapp.dagmodel.adapter.DagModelRestAdapter;
import com.onbelay.dagserverapp.dagmodel.snapshot.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name="GraphModel", description = "APIs to manage the GraphModel")
@RequestMapping("/api/models")public class DagModelRestController {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private DagModelRestAdapter dagModelRestAdapter;

    @Operation(summary = "Create GraphModel", description = "Create a GraphModel to use for subsequent graphing operations.",
            tags = {"model"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<ModelResult> createGraphModel(
            @RequestBody DagModelSnapshot snapshot,
            BindingResult bindingResult)  {

        if (bindingResult.getErrorCount() > 0) {
            logger.error("Errors on create model POST");
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error(error.toString());
            }

            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        ModelResult result;
        try {
            result = dagModelRestAdapter.createModel(snapshot);
        } catch (OBRuntimeException p) {
            result = new ModelResult(p.getErrorCode(), p.getParms());
        } catch (RuntimeException bre) {
            result = new ModelResult(bre.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (result.isSuccessful()) {
            return new ResponseEntity(result, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

    }


    @Operation(summary = "Add a node", description = "Add a non=persistent node to an existing graph model.",
            tags = {"model, node"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(value="/{modelName}/nodes", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<ModelResult> addNodes(
            @PathVariable String modelName,
            @RequestBody List<DagNodeSnapshot> snapshots,
            BindingResult bindingResult)  {

        if (bindingResult.getErrorCount() > 0) {
            logger.error("Errors on adding DagNodes POST");
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error(error.toString());
            }

            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        ModelResult result;
        try {
            result = dagModelRestAdapter.addNodes(
                    modelName,
                    snapshots);
        } catch (OBRuntimeException p) {
            result = new ModelResult(p.getErrorCode(), p.getParms());
        } catch (RuntimeException bre) {
            result = new ModelResult(bre.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (result.isSuccessful()) {
            return new ResponseEntity(result, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

    }



    @Operation(summary = "Add a relationship", description = "Add a non-persistent relationship to an existing graph model.",
            tags = {"model, relationship"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(value="/{modelName}/relationships", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<ModelResult> addRelationships(
            @PathVariable String modelName,
            @RequestBody List<DagRelationshipSnapshot> snapshots,
            BindingResult bindingResult)  {

        if (bindingResult.getErrorCount() > 0) {
            logger.error("Errors on adding DagRelationships POST");
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error(error.toString());
            }

            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        ModelResult result;
        try {
            result = dagModelRestAdapter.addRelationships(
                    modelName,
                    snapshots);
        } catch (OBRuntimeException p) {
            result = new ModelResult(p.getErrorCode(), p.getParms());
        } catch (RuntimeException bre) {
            result = new ModelResult(bre.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (result.isSuccessful()) {
            return new ResponseEntity(result, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

    }




    @Operation(summary = "Detect cycles in an existing model", description = "Detect a cyclic (cycle) relationship between two nodes.",
            tags = {"model, relationship"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(value="/{modelName}/{relationshipName}/cycles", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<DagNodePathCollection> fetchCycles(
            @PathVariable String modelName,
            @PathVariable String relationshipName,
            @RequestParam(value = "startingNode", required = false) String query)  {

        DagNodePathCollection collection;
        try {
            collection = dagModelRestAdapter.fetchCycleReport(
                    modelName,
                    relationshipName);
        } catch (OBRuntimeException p) {
            collection = new DagNodePathCollection(p.getErrorCode(), p.getParms());
        } catch (RuntimeException bre) {
            collection = new DagNodePathCollection(bre.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (collection.isSuccessful()) {
            return new ResponseEntity(collection, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity(collection, HttpStatus.BAD_REQUEST);
        }

    }


    @Operation(summary = "Find zero or more existing GraphModels", description = "Find zero or more models",
            tags = {"model"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<DagModelCollection> findGraphModels(
            @RequestHeader Map<String, String> headersIn,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "limit", defaultValue = "100") int limit,
            @RequestParam(value = "filter", required = false) String filter) {

        DagModelCollection collection;
        try {
            collection = dagModelRestAdapter.findGraphModels(
                    start,
                    limit,
                    filter);
        } catch (OBRuntimeException e) {
            collection = new DagModelCollection(e.getErrorCode(), e.getParms());
        } catch (RuntimeException e) {
            collection = new DagModelCollection(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (collection.isSuccessful()) {
            return new ResponseEntity<>(collection, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(collection, headers, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Find descendants from a starting node", description = "Find descendants from a starting node in a graph model. ",
            tags = {"model"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(value="/{modelName}/{startingNodeName}/{relationshipName}/descendants",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<DagNodeCollection> findDescendants(
            @RequestHeader Map<String, String> headersIn,
            @PathVariable String modelName,
            @PathVariable String startingNodeName,
            @PathVariable String relationshipName,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        DagNodeCollection collection;
        try {
            collection = dagModelRestAdapter.findDescendants(
                    modelName,
                    startingNodeName,
                    relationshipName,
                    start,
                    limit);
        } catch (OBRuntimeException e) {
            collection = new DagNodeCollection(e.getErrorCode(), e.getParms());
        } catch (RuntimeException e) {
            collection = new DagNodeCollection(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (collection.isSuccessful()) {
            return new ResponseEntity<>(collection, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(collection, headers, HttpStatus.BAD_REQUEST);
        }
    }



    @Operation(summary = "Find root nodes.", description = "Find root nodes (no relationships to)",
            tags = {"model"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(value="/{modelName}/rootnodes",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<DagNodeCollection> findRootNodes(
            @RequestHeader Map<String, String> headersIn,
            @PathVariable String modelName,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        DagNodeCollection collection;
        try {
            collection = dagModelRestAdapter.findRootNodes(
                    modelName,
                    start,
                    limit);
        } catch (OBRuntimeException e) {
            collection = new DagNodeCollection(e.getErrorCode(), e.getParms());
        } catch (RuntimeException e) {
            collection = new DagNodeCollection(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (collection.isSuccessful()) {
            return new ResponseEntity<>(collection, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(collection, headers, HttpStatus.BAD_REQUEST);
        }
    }

}
