package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void addItem(Item item) {
        System.out.println(item.getName() + " " + item.getId()); //only for debugging in postman
        entityManager.persist(item);
    }

    public Item getItem(String itemId) {
        String sql = "SELECT i from Item i where i.id = :id";
        return entityManager.createQuery(sql, Item.class)
                .setParameter("id", itemId)
                .getResultList().stream()
                .findFirst()
                .orElse(null);
    }
}
