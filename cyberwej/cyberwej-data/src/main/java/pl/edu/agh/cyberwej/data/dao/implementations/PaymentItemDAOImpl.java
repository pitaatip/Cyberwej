package pl.edu.agh.cyberwej.data.dao.implementations;

import org.springframework.stereotype.Repository;

import pl.edu.agh.cyberwej.data.dao.interfaces.PaymentItemDAO;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;

@Repository
public class PaymentItemDAOImpl extends DAOBase<PaymentItem> implements PaymentItemDAO {

}
