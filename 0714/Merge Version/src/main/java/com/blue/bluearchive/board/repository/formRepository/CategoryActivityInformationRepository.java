package com.blue.bluearchive.board.repository.formRepository;

import com.blue.bluearchive.board.entity.CategoryActivityInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface CategoryActivityInformationRepository extends JpaRepository<CategoryActivityInformation, CriteriaBuilder.In> {
}
