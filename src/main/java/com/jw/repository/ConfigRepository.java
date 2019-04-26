package com.jw.repository;

import com.jw.entity.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 姜雯
 * @date 2019/4/25--22:26
 */
@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity,String> {

    @Query("select s from ConfigEntity s")
     List<ConfigEntity> findAll() ;
}
