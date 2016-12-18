package com.innvo.web.rest;

import com.innvo.AdapWorkflowApp;
import com.innvo.domain.Playbookcomponent;
import com.innvo.repository.PlaybookcomponentRepository;
import com.innvo.repository.search.PlaybookcomponentSearchRepository;

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
 * Test class for the PlaybookcomponentResource REST controller.
 *
 * @see PlaybookcomponentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdapWorkflowApp.class)
public class PlaybookcomponentResourceIntTest {
    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private PlaybookcomponentRepository playbookcomponentRepository;

    @Inject
    private PlaybookcomponentSearchRepository playbookcomponentSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPlaybookcomponentMockMvc;

    private Playbookcomponent playbookcomponent;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlaybookcomponentResource playbookcomponentResource = new PlaybookcomponentResource();
        ReflectionTestUtils.setField(playbookcomponentResource, "playbookcomponentSearchRepository", playbookcomponentSearchRepository);
        ReflectionTestUtils.setField(playbookcomponentResource, "playbookcomponentRepository", playbookcomponentRepository);
        this.restPlaybookcomponentMockMvc = MockMvcBuilders.standaloneSetup(playbookcomponentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Playbookcomponent createEntity(EntityManager em) {
        Playbookcomponent playbookcomponent = new Playbookcomponent();
        playbookcomponent = new Playbookcomponent()
                .name(DEFAULT_NAME);
        return playbookcomponent;
    }

    @Before
    public void initTest() {
        playbookcomponentSearchRepository.deleteAll();
        playbookcomponent = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaybookcomponent() throws Exception {
        int databaseSizeBeforeCreate = playbookcomponentRepository.findAll().size();

        // Create the Playbookcomponent

        restPlaybookcomponentMockMvc.perform(post("/api/playbookcomponents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(playbookcomponent)))
                .andExpect(status().isCreated());

        // Validate the Playbookcomponent in the database
        List<Playbookcomponent> playbookcomponents = playbookcomponentRepository.findAll();
        assertThat(playbookcomponents).hasSize(databaseSizeBeforeCreate + 1);
        Playbookcomponent testPlaybookcomponent = playbookcomponents.get(playbookcomponents.size() - 1);
        assertThat(testPlaybookcomponent.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Playbookcomponent in ElasticSearch
        Playbookcomponent playbookcomponentEs = playbookcomponentSearchRepository.findOne(testPlaybookcomponent.getId());
        assertThat(playbookcomponentEs).isEqualToComparingFieldByField(testPlaybookcomponent);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = playbookcomponentRepository.findAll().size();
        // set the field null
        playbookcomponent.setName(null);

        // Create the Playbookcomponent, which fails.

        restPlaybookcomponentMockMvc.perform(post("/api/playbookcomponents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(playbookcomponent)))
                .andExpect(status().isBadRequest());

        List<Playbookcomponent> playbookcomponents = playbookcomponentRepository.findAll();
        assertThat(playbookcomponents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlaybookcomponents() throws Exception {
        // Initialize the database
        playbookcomponentRepository.saveAndFlush(playbookcomponent);

        // Get all the playbookcomponents
        restPlaybookcomponentMockMvc.perform(get("/api/playbookcomponents?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(playbookcomponent.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPlaybookcomponent() throws Exception {
        // Initialize the database
        playbookcomponentRepository.saveAndFlush(playbookcomponent);

        // Get the playbookcomponent
        restPlaybookcomponentMockMvc.perform(get("/api/playbookcomponents/{id}", playbookcomponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(playbookcomponent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlaybookcomponent() throws Exception {
        // Get the playbookcomponent
        restPlaybookcomponentMockMvc.perform(get("/api/playbookcomponents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaybookcomponent() throws Exception {
        // Initialize the database
        playbookcomponentRepository.saveAndFlush(playbookcomponent);
        playbookcomponentSearchRepository.save(playbookcomponent);
        int databaseSizeBeforeUpdate = playbookcomponentRepository.findAll().size();

        // Update the playbookcomponent
        Playbookcomponent updatedPlaybookcomponent = playbookcomponentRepository.findOne(playbookcomponent.getId());
        updatedPlaybookcomponent
                .name(UPDATED_NAME);

        restPlaybookcomponentMockMvc.perform(put("/api/playbookcomponents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPlaybookcomponent)))
                .andExpect(status().isOk());

        // Validate the Playbookcomponent in the database
        List<Playbookcomponent> playbookcomponents = playbookcomponentRepository.findAll();
        assertThat(playbookcomponents).hasSize(databaseSizeBeforeUpdate);
        Playbookcomponent testPlaybookcomponent = playbookcomponents.get(playbookcomponents.size() - 1);
        assertThat(testPlaybookcomponent.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Playbookcomponent in ElasticSearch
        Playbookcomponent playbookcomponentEs = playbookcomponentSearchRepository.findOne(testPlaybookcomponent.getId());
        assertThat(playbookcomponentEs).isEqualToComparingFieldByField(testPlaybookcomponent);
    }

    @Test
    @Transactional
    public void deletePlaybookcomponent() throws Exception {
        // Initialize the database
        playbookcomponentRepository.saveAndFlush(playbookcomponent);
        playbookcomponentSearchRepository.save(playbookcomponent);
        int databaseSizeBeforeDelete = playbookcomponentRepository.findAll().size();

        // Get the playbookcomponent
        restPlaybookcomponentMockMvc.perform(delete("/api/playbookcomponents/{id}", playbookcomponent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean playbookcomponentExistsInEs = playbookcomponentSearchRepository.exists(playbookcomponent.getId());
        assertThat(playbookcomponentExistsInEs).isFalse();

        // Validate the database is empty
        List<Playbookcomponent> playbookcomponents = playbookcomponentRepository.findAll();
        assertThat(playbookcomponents).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPlaybookcomponent() throws Exception {
        // Initialize the database
        playbookcomponentRepository.saveAndFlush(playbookcomponent);
        playbookcomponentSearchRepository.save(playbookcomponent);

        // Search the playbookcomponent
        restPlaybookcomponentMockMvc.perform(get("/api/_search/playbookcomponents?query=id:" + playbookcomponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playbookcomponent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
