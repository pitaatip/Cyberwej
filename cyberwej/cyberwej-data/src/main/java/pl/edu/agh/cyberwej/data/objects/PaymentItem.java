package pl.edu.agh.cyberwej.data.objects;

import java.util.HashSet;
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
@Table(name = "PAYMENTITEMS")
public class PaymentItem {

    private Integer id;

    private Product product;

    private int count;

    private float price;

    private Set<User> consumers = new HashSet<User>();

    @Id
    @Column(name = "ITEMID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "PRODUCTID")
    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "ITEMSCOUNT", nullable = false)
    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Column(name = "PRICE", nullable = false)
    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @ManyToMany
    public Set<User> getConsumers() {
        return this.consumers;
    }

    public void setConsumers(Set<User> consumers) {
        this.consumers = consumers;
    }

}
