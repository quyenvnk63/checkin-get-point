package org.attendancegetpointapi.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BaseRepository<T, ID> extends PagingAndSortingRepository<T, ID>, CrudRepository<T, ID>, JpaSpecificationExecutor<T> {}