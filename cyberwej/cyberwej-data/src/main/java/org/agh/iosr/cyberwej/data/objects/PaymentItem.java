package org.agh.iosr.cyberwej.data.objects;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAYMENTITEM")
public class PaymentItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7467642949671009250L;

	private int id;

	private Product product;

	private int count;

	private float price;

	private Set<User> consumers;

	@Id
	@Column(name = "ITEMID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "PRODUCTID")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "COUNT", nullable=false)
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Column(name = "PRICE", nullable=false)
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@ManyToMany
	public Set<User> getConsumers() {
		return consumers;
	}

	public void setConsumers(Set<User> consumers) {
		this.consumers = consumers;
	}

}
