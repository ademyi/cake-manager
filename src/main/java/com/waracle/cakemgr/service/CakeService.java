package com.waracle.cakemgr.service;

import com.waracle.cakemgr.entity.Cake;

import java.util.List;

public interface CakeService {
    public List<Cake> findAll();

    public Cake findById(int id);

    public Cake save(Cake cake);

    public void delete(int id);

    public Cake findCakeByTitle(String title);
}
