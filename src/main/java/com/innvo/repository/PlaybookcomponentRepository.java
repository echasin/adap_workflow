package com.innvo.repository;

import com.innvo.domain.Playbookcomponent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Playbookcomponent entity.
 */
@SuppressWarnings("unused")
public interface PlaybookcomponentRepository extends JpaRepository<Playbookcomponent,Long> {

}
