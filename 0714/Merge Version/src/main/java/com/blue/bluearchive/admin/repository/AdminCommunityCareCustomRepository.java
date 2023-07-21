package com.blue.bluearchive.admin.repository;

import com.blue.bluearchive.admin.entity.AdminCommunityCare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminCommunityCareCustomRepository extends JpaRepository<AdminCommunityCare,Long> {
    List<AdminCommunityCare> findAll();
}
