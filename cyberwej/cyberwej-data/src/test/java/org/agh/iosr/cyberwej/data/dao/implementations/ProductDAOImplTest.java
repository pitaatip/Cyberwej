package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.agh.iosr.cyberwej.data.dao.interfaces.ProductDAO;
import org.agh.iosr.cyberwej.data.objects.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductDAOImplTest {

    @Autowired
    private ProductDAO productDAO;

    private Product product;

    private String name = "Pizza";

    private String nonExistingProduct = "Ziemniaki";

    @Before
    public void setUp() {
        this.product = new Product();
        this.product.setName(this.name);
        this.productDAO.saveProduct(product);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testSaveProduct() {
        List<Product> products = this.productDAO.findProductsByName(this.name);
        assertFalse(products.isEmpty());
        assertTrue(products.contains(product));
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testFindProductsByName() {
        String findName = this.name.substring(0, name.length() - 2);
        List<Product> products = this.productDAO.findProductsByName(findName);
        assertFalse(products.isEmpty());
        assertTrue(products.get(0).getName().startsWith(findName));
        products = this.productDAO.findProductsByName(nonExistingProduct);
        assertTrue(products.isEmpty());
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemoveProduct() {
        this.productDAO.removeProduct(product);
        List<Product> products = this.productDAO.findProductsByName(this.name);
        assertTrue(products.isEmpty());
    }
}
