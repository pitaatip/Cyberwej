package pl.edu.agh.cyberwej.data.dao.interfaces;

import java.util.List;

import pl.edu.agh.cyberwej.data.objects.Product;

public interface ProductDAO extends IDAO<Product> {

    boolean saveProduct(Product product);

    Product findProductByName(String name);

    List<Product> findProductsByName(String name);

    boolean removeProduct(Product product);
}
