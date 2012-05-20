package pl.edu.agh.cyberwej.data.dao.interfaces;

import java.util.List;

import pl.edu.agh.cyberwej.data.objects.Product;

public interface ProductDAO extends IDAO<Product> {

    public boolean saveProduct(Product product);
    
    
    public Product findProductByName(String name);

    public List<Product> findProductsByName(String name);

    public boolean removeProduct(Product product);
}
