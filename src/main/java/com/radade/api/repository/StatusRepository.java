package com.radade.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.radade.api.model.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
