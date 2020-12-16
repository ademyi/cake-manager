package com.waracle.cakemgr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entity.Cake;
import com.waracle.cakemgr.service.CakeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@Component
public class DataLoadRunner implements CommandLineRunner {

    private static final String CAKES_FILE_URI = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
    private static final Logger logger = LoggerFactory.getLogger(DataLoadRunner.class);

    @Autowired
    CakeService service;

    public DataLoadRunner(CakeService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("data loaded from the remote filesystm");

        final String json = readFile();

        if ( json != null && json.length() > 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Cake> cakes = objectMapper.readValue(json.toString(), new TypeReference<List<Cake>>() {});

            cakes.stream().forEach(r -> insertCake(r) ) ;   // create the Cake entities in Db

            logger.info("Total records read : {}", cakes.size());
        }
    }

    private void insertCake(Cake cake) {

        // check DB. The title field has a unique constraint
        Cake c = service.findCakeByTitle(cake.getTitle());

        //Do not insert if there is a record with the same title
        if ( c != null){
            cake.setCakeId(c.getCakeId()); // update
        }

        Cake save = service.save(cake);

        logger.info("{} --> Title : {} - Id : {}", (c != null) ? "UPDATED" : "INSERTED", save.getTitle(), save.getCakeId());
    }

    private String readFile() {
        final StringBuffer sb = new StringBuffer();

        try (InputStream inputStream = new URL(CAKES_FILE_URI).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }
}
