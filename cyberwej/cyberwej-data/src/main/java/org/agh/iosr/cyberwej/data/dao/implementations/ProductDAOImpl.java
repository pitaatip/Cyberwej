package org.agh.iosr.cyberwej.data.dao.implementations;

import java.util.List;

import org.agh.iosr.cyberwej.data.dao.interfaces.ProductDAO;
import org.agh.iosr.cyberwej.data.objects.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAOImpl extends DAOBase implements ProductDAO {

	@Override
	public boolean saveProduct(Product product) {
		return super.save(product);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> findProductsByName(String name) {
		return super.hibernateTemplate.find(
				"from Product product where product.name like ?", name + "%");
	}

	@Override
	public boolean removeProduct(Product product) {
		super.hibernateTemplate.delete(product);
		return true;
	}
}
