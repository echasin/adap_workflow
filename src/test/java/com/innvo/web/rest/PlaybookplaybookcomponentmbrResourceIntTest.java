package com.innvo.web.rest;

import com.innvo.AdapWorkflowApp;
import com.innvo.domain.Playbookplaybookcomponentmbr;
import com.innvo.domain.Playbook;
import com.innvo.domain.Playbookcomponent;
import com.innvo.repository.PlaybookplaybookcomponentmbrRepository;
import com.innvo.repository.search.PlaybookplaybookcomponentmbrSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PlaybookplaybookcomponentmbrResource REST controller.
 *
 * @see PlaybookplaybookcomponentmbrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdapWorkflowApp.class)
public class PlaybookplaybookcomponentmbrResourceIntTest {
    private static final String DEFAULT_COMMENT = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private PlaybookplaybookcomponentmbrRepository playbookplaybookcomponentmbrRepository;

    @Inject
    private PlaybookplaybookcomponentmbrSearchRepository playbookplaybookcomponentmbrSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPlaybookplaybookcomponentmbrMockMvc;

    private Playbookplaybookcomponentmbr playbookplaybookcomponentmbr;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlaybookplaybookcomponentmbrResource playbookplaybookcomponentmbrResource = new PlaybookplaybookcomponentmbrResource();
        ReflectionTestUtils.setField(playbookplaybookcomponentmbrResource, "playbookplaybookcomponentmbrSearchRepository", playbookplaybookcomponentmbrSearchRepository);
        ReflectionTestUtils.setField(playbookplaybookcomponentmbrResource, "playbookplaybookcomponentmbrRepository", playbookplaybookcomponentmbrRepository);
        this.restPlaybookplaybookcomponentmbrMockMvc = MockMvcBuilders.standaloneSetup(playbookplaybookcomponentmbrResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Playbookplaybookcomponentmbr createEntity(EntityManager em) {
        Playbookplaybookcomponentmbr playbookplaybookcomponentmbr = new Playbookplaybookcomponentmbr();
        playbookplaybookcomponentmbr = new Playbookplaybookcomponentmbr()
                .comment(DEFAULT_COMMENT);
        // Add required entity
        Playbook playbook = PlaybookResourceIntTest.createEntity(em);
        em.persist(playbook);
        em.flush();
        playbookplaybookcomponentmbr.setPlaybook(playbook);
        // Add required entity
        Playbookcomponent playbookcomponent = PlaybookcomponentResourceIntTest.createEntity(em);
        em.persist(playbookcomponent);
        em.flush();
        playbookplaybookcomponentmbr.setPlaybookcomponent(playbookcomponent);
        return playbookplaybookcomponentmbr;
    }

    @Before
    public void initTest() {
        playbookplaybookcomponentmbrSearchRepository.deleteAll();
        playbookplaybookcomponentmbr = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaybookplaybookcomponentmbr() throws Exception {
        int databaseSizeBeforeCreate = playbookplaybookcomponentmbrRepository.findAll().size();

        // Create the Playbookplaybookcomponentmbr

        restPlaybookplaybookcomponentmbrMockMvc.perform(post("/api/playbookplaybookcomponentmbrs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(playbookplaybookcomponentmbr)))
                .andExpect(status().isCreated());

        // Validate the Playbookplaybookcomponentmbr in the database
        List<Playbookplaybookcomponentmbr> playbookplaybookcomponentmbrs = playbookplaybookcomponentmbrRepository.findAll();
        assertThat(playbookplaybookcomponentmbrs).hasSize(databaseSizeBeforeCreate + 1);
        Playbookplaybookcomponentmbr testPlaybookplaybookcomponentmbr = playbookplaybookcomponentmbrs.get(playbookplaybookcomponentmbrs.size() - 1);
        assertThat(testPlaybookplaybookcomponentmbr.getComment()).isEqualTo(DEFAULT_COMMENT);

        // Validate the Playbookplaybookcomponentmbr in ElasticSearch
        Playbookplaybookcomponentmbr playbookplaybookcomponentmbrEs = playbookplaybookcomponentmbrSearchRepository.findOne(testPlaybookplaybookcomponentmbr.getId());
        assertThat(playbookplaybookcomponentmbrEs).isEqualToComparingFieldByField(testPlaybookplaybookcomponentmbr);
    }

    @Test
    @Transactional
    public void getAllPlaybookplaybookcomponentmbrs() throws Exception {
        // Initialize the database
        playbookplaybookcomponentmbrRepository.saveAndFlush(playbookplaybookcomponentmbr);

        // Get all the playbookplaybookcomponentmbrs
        restPlaybookplaybookcomponentmbrMockMvc.perform(get("/api/playbookplaybookcomponentmbrs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(playbookplaybookcomponentmbr.getId().intValue())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getPlaybookplaybookcomponentmbr() throws Exception {
        // Initialize the database
        playbookplaybookcomponentmbrRepository.saveAndFlush(playbookplaybookcomponentmbr);

        // Get the playbookplaybookcomponentmbr
        restPlaybookplaybookcomponentmbrMockMvc.perform(get("/api/playbookplaybookcomponentmbrs/{id}", playbookplaybookcomponentmbr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(playbookplaybookcomponentmbr.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlaybookplaybookcomponentmbr() throws Exception {
        // Get the playbookplaybookcomponentmbr
        restPlaybookplaybookcomponentmbrMockMvc.perform(get("/api/playbookplaybookcomponentmbrs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaybookplaybookcomponentmbr() throws Exception {
        // Initialize the database
        playbookplaybookcomponentmbrRepository.saveAndFlush(playbookplaybookcomponentmbr);
        playbookplaybookcomponentmbrSearchRepository.save(playbookplaybookcomponentmbr);
        int databaseSizeBeforeUpdate = playbookplaybookcomponentmbrRepository.findAll().size();

        // Update the playbookplaybookcomponentmbr
        Playbookplaybookcomponentmbr updatedPlaybookplaybookcomponentmbr = playbookplaybookcomponentmbrRepository.findOne(playbookplaybookcomponentmbr.getId());
        updatedPlaybookplaybookcomponentmbr
                .comment(UPDATED_COMMENT);

        restPlaybookplaybookcomponentmbrMockMvc.perform(put("/api/playbookplaybookcomponentmbrs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPlaybookplaybookcomponentmbr)))
                .andExpect(status().isOk());

        // Validate the Playbookplaybookcomponentmbr in the database
        List<Playbookplaybookcomponentmbr> playbookplaybookcomponentmbrs = playbookplaybookcomponentmbrRepository.findAll();
        assertThat(playbookplaybookcomponentmbrs).hasSize(databaseSizeBeforeUpdate);
        Playbookplaybookcomponentmbr testPlaybookplaybookcomponentmbr = playbookplaybookcomponentmbrs.get(playbookplaybookcomponentmbrs.size() - 1);
        assertThat(testPlaybookplaybookcomponentmbr.getComment()).isEqualTo(UPDATED_COMMENT);

        // Validate the Playbookplaybookcomponentmbr in ElasticSearch
        Playbookplaybookcomponentmbr playbookplaybookcomponentmbrEs = playbookplaybookcomponentmbrSearchRepository.findOne(testPlaybookplaybookcomponentmbr.getId());
        assertThat(playbookplaybookcomponentmbrEs).isEqualToComparingFieldByField(testPlaybookplaybookcomponentmbr);
    }

    @Test
    @Transactional
    public void deletePlaybookplaybookcomponentmbr() throws Exception {
        // Initialize the database
        playbookplaybookcomponentmbrRepository.saveAndFlush(playbookplaybookcomponentmbr);
        playbookplaybookcomponentmbrSearchRepository.save(playbookplaybookcomponentmbr);
        int databaseSizeBeforeDelete = playbookplaybookcomponentmbrRepository.findAll().size();

        // Get the playbookplaybookcomponentmbr
        restPlaybookplaybookcomponentmbrMockMvc.perform(delete("/api/playbookplaybookcomponentmbrs/{id}", playbookplaybookcomponentmbr.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean playbookplaybookcomponentmbrExistsInEs = playbookplaybookcomponentmbrSearchRepository.exists(playbookplaybookcomponentmbr.getId());
        assertThat(playbookplaybookcomponentmbrExistsInEs).isFalse();

        // Validate the database is empty
        List<Playbookplaybookcomponentmbr> playbookplaybookcomponentmbrs = playbookplaybookcomponentmbrRepository.findAll();
        assertThat(playbookplaybookcomponentmbrs).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPlaybookplaybookcomponentmbr() throws Exception {
        // Initialize the database
        playbookplaybookcomponentmbrRepository.saveAndFlush(playbookplaybookcomponentmbr);
        playbookplaybookcomponentmbrSearchRepository.save(playbookplaybookcomponentmbr);

        // Search the playbookplaybookcomponentmbr
        restPlaybookplaybookcomponentmbrMockMvc.perform(get("/api/_search/playbookplaybookcomponentmbrs?query=id:" + playbookplaybookcomponentmbr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playbookplaybookcomponentmbr.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }
}
