package com.innvo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.innvo.domain.Playbookcomponent;

import com.innvo.repository.PlaybookcomponentRepository;
import com.innvo.repository.search.PlaybookcomponentSearchRepository;
import com.innvo.web.rest.util.HeaderUtil;
import com.innvo.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Playbookcomponent.
 */
@RestController
@RequestMapping("/api")
public class PlaybookcomponentResource {

    private final Logger log = LoggerFactory.getLogger(PlaybookcomponentResource.class);
        
    @Inject
    private PlaybookcomponentRepository playbookcomponentRepository;

    @Inject
    private PlaybookcomponentSearchRepository playbookcomponentSearchRepository;

    /**
     * POST  /playbookcomponents : Create a new playbookcomponent.
     *
     * @param playbookcomponent the playbookcomponent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new playbookcomponent, or with status 400 (Bad Request) if the playbookcomponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/playbookcomponents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Playbookcomponent> createPlaybookcomponent(@Valid @RequestBody Playbookcomponent playbookcomponent) throws URISyntaxException {
        log.debug("REST request to save Playbookcomponent : {}", playbookcomponent);
        if (playbookcomponent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("playbookcomponent", "idexists", "A new playbookcomponent cannot already have an ID")).body(null);
        }
        Playbookcomponent result = playbookcomponentRepository.save(playbookcomponent);
        playbookcomponentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/playbookcomponents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("playbookcomponent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /playbookcomponents : Updates an existing playbookcomponent.
     *
     * @param playbookcomponent the playbookcomponent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated playbookcomponent,
     * or with status 400 (Bad Request) if the playbookcomponent is not valid,
     * or with status 500 (Internal Server Error) if the playbookcomponent couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/playbookcomponents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Playbookcomponent> updatePlaybookcomponent(@Valid @RequestBody Playbookcomponent playbookcomponent) throws URISyntaxException {
        log.debug("REST request to update Playbookcomponent : {}", playbookcomponent);
        if (playbookcomponent.getId() == null) {
            return createPlaybookcomponent(playbookcomponent);
        }
        Playbookcomponent result = playbookcomponentRepository.save(playbookcomponent);
        playbookcomponentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("playbookcomponent", playbookcomponent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /playbookcomponents : get all the playbookcomponents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of playbookcomponents in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/playbookcomponents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Playbookcomponent>> getAllPlaybookcomponents(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Playbookcomponents");
        Page<Playbookcomponent> page = playbookcomponentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/playbookcomponents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /playbookcomponents/:id : get the "id" playbookcomponent.
     *
     * @param id the id of the playbookcomponent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the playbookcomponent, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/playbookcomponents/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Playbookcomponent> getPlaybookcomponent(@PathVariable Long id) {
        log.debug("REST request to get Playbookcomponent : {}", id);
        Playbookcomponent playbookcomponent = playbookcomponentRepository.findOne(id);
        return Optional.ofNullable(playbookcomponent)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /playbookcomponents/:id : delete the "id" playbookcomponent.
     *
     * @param id the id of the playbookcomponent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/playbookcomponents/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePlaybookcomponent(@PathVariable Long id) {
        log.debug("REST request to delete Playbookcomponent : {}", id);
        playbookcomponentRepository.delete(id);
        playbookcomponentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("playbookcomponent", id.toString())).build();
    }

    /**
     * SEARCH  /_search/playbookcomponents?query=:query : search for the playbookcomponent corresponding
     * to the query.
     *
     * @param query the query of the playbookcomponent search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/playbookcomponents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Playbookcomponent>> searchPlaybookcomponents(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Playbookcomponents for query {}", query);
        Page<Playbookcomponent> page = playbookcomponentSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/playbookcomponents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
