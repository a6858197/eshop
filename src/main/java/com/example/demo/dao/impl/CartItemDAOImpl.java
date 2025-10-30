package com.example.demo.dao.impl;

import com.example.demo.dao.CartItemDAO;
import com.example.demo.model.CartItem;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemDAOImpl implements CartItemDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public CartItem findByCartIdAndProductId(Long cartId, Long productId) {
        String hql = "FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId";
        return getSession()
                .createQuery(hql, CartItem.class)
                .setParameter("cartId", cartId)
                .setParameter("productId", productId)
                .uniqueResult();
    }

    @Override
    public void save(CartItem cartItem) {
        getSession().save(cartItem);
    }

    @Override
    public void update(CartItem cartItem) {
        getSession().update(cartItem);
    }

    @Override
    public void delete(Long id) {
        CartItem item = findById(id);
        if (item != null) {
            getSession().delete(item);
        }
    }

    @Override
    public CartItem findById(Long id) {
        return getSession().get(CartItem.class, id);
    }

    @Override
    public List<CartItem> findByCart(Cart cart) {
        return getSession()
                .createQuery("FROM CartItem WHERE cart = :cart", CartItem.class)
                .setParameter("cart", cart)
                .list();
    }

    @Override
    public CartItem findByCartAndProduct(Cart cart, Product product) {
        return getSession()
                .createQuery("FROM CartItem WHERE cart = :cart AND product = :product", CartItem.class)
                .setParameter("cart", cart)
                .setParameter("product", product)
                .uniqueResult();
    }
}
