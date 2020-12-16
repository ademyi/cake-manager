package com.waracle.cakemgr;

import com.waracle.cakemgr.dao.CakeRepository;
import com.waracle.cakemgr.entity.Cake;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CakeRepositoryTest {

    @Autowired
    private CakeRepository cakeRepository;

    private Cake cakeEntity;

    private Integer cakeId;

    @Before
    public void setUp() {

        this.cakeEntity = new Cake();

        cakeEntity.setTitle("Strawberry cake");
        cakeEntity.setDesc("Super cake");
        cakeEntity.setImage("https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/");

        cakeRepository.saveAndFlush(cakeEntity);

        this.cakeId = ((Cake) cakeRepository.findAll().get(0)).getCakeId();
    }

    @Test
    public void testFindByAllReturnsCakes() {

        assertEquals(cakeRepository.findAll().size(), 1);

    }

    @Test
    public void testFindByIdReturnsCake() {
        // given
        Cake expectedCakeEntity = new Cake();

        expectedCakeEntity.setTitle("Strawberry cake");
        expectedCakeEntity.setDesc("Super cake");
        expectedCakeEntity.setImage("https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/");

        // when
        Cake cakeEntity = new Cake();
        Optional<Cake> found = cakeRepository.findById(cakeId);

        if (found.isPresent()) {
            cakeEntity = found.get();
        }

        // then
        assertEquals(expectedCakeEntity.getTitle(), cakeEntity.getTitle());
        assertEquals(expectedCakeEntity.getDesc(), cakeEntity.getDesc());
        assertEquals(expectedCakeEntity.getImage(), cakeEntity.getImage());
    }

    @Test
    public void testReturnsUpdatedCake() {
        // given
        String expectedCakeDescription = "Strawberry cakes forever";

        // when
        Cake updatedCakeEntity = new Cake();
        Optional<Cake> found = cakeRepository.findById(cakeId);

        if (found.isPresent()) {
            updatedCakeEntity = found.get();
            updatedCakeEntity.setDesc("Strawberry cakes forever");
            cakeRepository.save(updatedCakeEntity);
        }

        found = cakeRepository.findById(cakeId);
        if (found.isPresent()) {
            updatedCakeEntity = found.get();
        }

        // then
        assertEquals(expectedCakeDescription, updatedCakeEntity.getDesc());
    }

    @Test
    public void testInsertsNewCake() {
        // given
        Cake secondCakeEntity = new Cake();

        secondCakeEntity.setTitle("Cheese cake");
        secondCakeEntity.setDesc("Super cheese cake");
        secondCakeEntity.setImage("https://www.pexels.com/photo/chocolate-cake-with-white-icing-and-strawberry-on-top-with-chocolate-69817/");

        //when
        cakeRepository.saveAndFlush(secondCakeEntity);
        List<Cake> cakes = cakeRepository.findAll();

        // then
        assertEquals(cakes.size(), 2);
    }

    @Test
    public void testDeletesCake() {

        //when
        cakeRepository.deleteAll();
        cakeRepository.flush();

        // then
        assertEquals(cakeRepository.findAll().size(), 0);
    }
}
