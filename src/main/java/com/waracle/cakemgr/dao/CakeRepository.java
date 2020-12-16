package com.waracle.cakemgr.dao;

import com.waracle.cakemgr.entity.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeRepository extends JpaRepository<Cake, Integer> {
    Cake findCakeByTitle(String title);
}
