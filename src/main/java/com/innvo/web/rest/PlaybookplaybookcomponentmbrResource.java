package com.innvo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.innvo.domain.Playbookplaybookcomponentmbr;

import com.innvo.repository.PlaybookplaybookcomponentmbrRepository;
import com.innvo.repository.search.PlaybookplaybookcomponentmbrSearchRepository;
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
 * REST controller for managing Playbookplaybookcomponentmbr.
 */
@RestController
@RequestMapping("/api")
public class PlaybookplaybookcomponentmbrResource {

    private final Logger log = LoggerFactory.getLogger(PlaybookplaybookcomponentmbrResource.class);
        
    @Inject
    private PlaybookplaybookcomponentmbrRepository playbookplaybookcomponentmbrRepository;

    @Inject
    private PlaybookplaybookcomponentmbrSearchRepository playbookplaybookcomponentmbrSearchRepository;

    /**
     * POST  /playbookplaybookcomponentmbrs : Create a new playbookplaybookcomponentmbr.
     *
     * @param playbookplaybookcomponentmbr the playbookplaybookcomponentmbr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new playbookplaybookcomponentmbr, or with status 400 (Bad Request) if the playbookplaybookcomponentmbr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/playbookplaybookcomponentmbrs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Playbookplaybookcomponentmbr> createPlaybookplaybookcomponentmbr(@Valid @RequestBody Playbookplaybookcomponentmbr playbookplaybookcomponentmbr) throws URISyntaxException {
        log.debug("REST request to save Playbookplaybookcomponentmbr : {}", playbookplaybookcomponentmbr);
        if (playbookplaybookcomponentmbr.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("playbookplaybookcomponentmbr", "idexists", "A new playbookplaybookcomponentmbr cannot already have an ID")).body(null);
        }
        Playbookplaybookcomponentmbr result = playbookplaybookcomponentmbrRepository.save(playbookplaybookcomponentmbr);
        playbookplaybookcomponentmbrSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/playbookplaybookcomponentmbrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("playbookplaybookcomponentmbr", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /playbookplaybookcomponentmbrs : Updates an existing playbookplaybookcomponentmbr.
     *
     * @param playbookplaybookcomponentmbr the playbookplaybookcomponentmbr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated playbookplaybookcomponentmbr,
     * or with status 400 (Bad Request) if the playbookplaybookcomponentmbr is not valid,
     * or with status 500 (Internal Server Error) if the playbookplaybookcomponentmbr couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/playbookplaybookcomponentmbrs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Playbookplaybookcomponentmbr> updatePlaybookplaybookcomponentmbr(@Valid @RequestBody Playbookplaybookcomponentmbr playbookplaybookcomponentmbr) throws URISyntaxException {
        log.debug("REST request to update Playbookplaybookcomponentmbr : {}", playbookplaybookcomponentmbr);
        if (playbookplaybookcomponentmbr.getId() == null) {
            return createPlaybookplaybookcomponentmbr(playbookplaybookcomponentmbr);
        }
        Playbookplaybookcomponentmbr result = playbookplaybookcomponentmbrRepository.save(playbookplaybookcomponentmbr);
        playbookplaybookcomponentmbrSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("playbookplaybookcomponentmbr", playbookplaybookcomponentmbr.getId().toString()))
            .body(result);
    }

    /**
     * GET  /playbookplaybookcomponentmbrs : get all the playbookplaybookcomponentmbrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of playbookplaybookcomponentmbrs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/playbookplaybookcomponentmbrs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Playbookplaybookcomponentmbr>> getAllPlaybookplaybookcomponentmbrs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Playbookplaybookcomponentmbrs");
        Page<Playbookplaybookcomponentmbr> page = playbookplaybookcomponentmbrRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/playbookplaybookcomponentmbrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /playbookplaybookcomponentmbrs/:id : get the "id" playbookplaybookcomponentmbr.
     *
     * @param id the id of the playbookplaybookcomponentmbr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the playbookplaybookcomponentmbr, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/playbookplaybookcomponentmbrs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Playbookplaybookcomponentmbr> getPlaybookplaybookcomponentmbr(@PathVariable Long id) {
        log.debug("REST request to get Playbookplaybookcomponentmbr : {}", id);
        Playbookplaybookcomponentmbr playbookplaybookcomponentmbr = playbookplaybookcomponentmbrRepository.findOne(id);
        return Optional.ofNullable(playbookplaybookcomponentmbr)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /playbookplaybookcomponentmbrs/:id : delete the "id" playbookplaybookcomponentmbr.
     *
     * @param id the id of the playbookplaybookcomponentmbr to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/playbookplaybookcomponentmbrs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePlaybookplaybookcomponentmbr(@PathVariable Long id) {
        log.debug("REST request to delete Playbookplaybookcomponentmbr : {}", id);
        playbookplaybookcomponentmbrRepository.delete(id);
        playbookplaybookcomponentmbrSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("playbookplaybookcomponentmbr", id.toString())).build();
    }

    /**
     * SEARCH  /_search/playbookplaybookcomponentmbrs?query=:query : search for the playbookplaybookcomponentmbr corresponding
     * to the query.
     *
     * @param query the query of the playbookplaybookcomponentmbr search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/playbookplaybookcomponentmbrs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Playbookplaybookcomponentmbr>> searchPlaybookplaybookcomponentmbrs(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Playbookplaybookcomponentmbrs for query {}", query);
        Page<Playbookplaybookcomponentmbr> page = playbookplaybookcomponentmbrSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/playbookplaybookcomponentmbrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
