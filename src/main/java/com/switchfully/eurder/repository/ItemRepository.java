package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ItemRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void addItem(Item item) {
        System.out.println(item.getName() + " " + item.getId()); //only for debugging in postman
        entityManager.persist(item);
    }

    public Item getItem(String id) {
        String sql = "SELECT i FROM Item i WHERE i.id = :id";
        return entityManager.createQuery(sql, Item.class)
                .setParameter("id", id)
                .getResultList().stream()
                .findFirst()
                .orElse(null);
    }
}
