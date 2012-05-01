package org.agh.iosr.cyberwej.data.dao.interfaces;

import java.util.List;

import org.agh.iosr.cyberwej.data.objects.Product;

public interface ProductDAO extends IDAO<Product> {

    public boolean saveProduct(Product product);

    public List<Product> findProductsByName(String name);

    public boolean removeProduct(Product product);
}
