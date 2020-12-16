package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dao.CakeRepository;
import com.waracle.cakemgr.entity.Cake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CakeServiceImpl implements CakeService {
    CakeRepository repository;

    @Autowired
    public CakeServiceImpl(CakeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cake> findAll() {
        return repository.findAll();
    }

    @Override
    public Cake findById(int id) {
        Optional<Cake> entity = repository.findById(id);

        return entity.orElseGet( () -> null);
    }

    @Override
    public Cake save(Cake cake) {
        Cake t = repository.findCakeByTitle(cake.getTitle());

        if (t != null){
            cake.setCakeId(t.getCakeId());
        }
        return repository.save(cake);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public Cake findCakeByTitle(String title) {
        return repository.findCakeByTitle(title);
    }

}
