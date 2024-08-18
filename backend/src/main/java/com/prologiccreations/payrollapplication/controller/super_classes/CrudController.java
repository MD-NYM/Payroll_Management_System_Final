package com.prologiccreations.payrollapplication.controller.super_classes;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.prologiccreations.payrollapplication.dto.Response;
import com.prologiccreations.payrollapplication.model.super_classes.BaseEntity;

public interface CrudController<E extends BaseEntity, I> {

    @PostMapping(value = "save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Response> storeData(@RequestBody E data);

    @GetMapping(
            value = {"list", "list/{pageNumber}/{pageSize}", "list/{pageNumber}/{pageSize}/{sortDirection}/{sortColumns}"},
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Response<Page<E>>> getAll(@PathVariable(value = "pageNumber", required = false) Integer pageNumber,
                                             @PathVariable(value = "pageSize", required = false) Integer pageSize,
                                             @PathVariable(value = "sortDirection", required = false) String sortDirection,
                                             @PathVariable(value = "sortColumns", required = false) String sortColumns
    );

    @GetMapping(value = "find/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Response<E>> getOne(@PathVariable("id") I id);

    @DeleteMapping(value = "delete/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Response> delete(@PathVariable("id") I id);

    // @GetMapping(value = "list", produces = APPLICATION_JSON_VALUE)
    // ResponseEntity<Response<Page<E>>> getByCriteria(@RequestParam Map<String, String> criteria);
}