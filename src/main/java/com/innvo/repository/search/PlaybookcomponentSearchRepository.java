package com.innvo.repository.search;

import com.innvo.domain.Playbookcomponent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Playbookcomponent entity.
 */
public interface PlaybookcomponentSearchRepository extends ElasticsearchRepository<Playbookcomponent, Long> {
}
