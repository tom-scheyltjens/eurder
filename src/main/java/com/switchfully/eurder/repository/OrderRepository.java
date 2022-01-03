package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.ItemGroup;
import com.switchfully.eurder.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void addOrder(Order order){
        entityManager.persist(order);
        for (ItemGroup itemGroup : order.getItemGroups()){
            entityManager.persist(itemGroup);
        }
    }
}
