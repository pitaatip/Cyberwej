package pl.edu.agh.cyberwej.data.dao.implementations;

import java.util.List;

import org.springframework.stereotype.Repository;

import pl.edu.agh.cyberwej.data.dao.interfaces.ProductDAO;
import pl.edu.agh.cyberwej.data.objects.Product;

@Repository
public class ProductDAOImpl extends DAOBase<Product> implements ProductDAO {

    @Override
    public boolean saveProduct(Product product) {
        return super.save(product);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> findProductsByName(String name) {
        return super.hibernateTemplate.find("from Product product where product.name like ?", name
                + "%");
    }

    @Override
    public boolean removeProduct(Product product) {
        super.hibernateTemplate.delete(product);
        return true;
    }

    @Override
    public Product findProductByName(String name) {
        @SuppressWarnings("unchecked")
        List<Product> queryResult = this.hibernateTemplate.find(
                "from Product product where product.name=?", name);
        if (queryResult.size() > 0)
            return queryResult.get(0);
        return null;
    }
}
