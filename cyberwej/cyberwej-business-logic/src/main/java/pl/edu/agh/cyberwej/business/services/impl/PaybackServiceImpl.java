package pl.edu.agh.cyberwej.business.services.impl;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaybackDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payback;
import org.agh.iosr.cyberwej.data.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.agh.cyberwej.business.services.api.PaybackService;

/**
 * 
 * @author Krzysztof
 * 
 */
@Service
public class PaybackServiceImpl implements PaybackService {

    @Autowired
    private PaybackDAO paybackDAO;

    @Override
    public boolean createPayback(User debtor, User investor, Group group,
            float amount) {
        return this.paybackDAO.addPayback(debtor, investor, group, amount);
    }

    @Override
    public boolean acceptPayback(Payback payback, boolean isAccepted) {
        payback.setAccepted(isAccepted);
        return this.paybackDAO.updatePayback(payback);
    }

    /**
     * @param paybackDAO
     *            the paybackDAO to set
     */
    public void setPaybackDAO(PaybackDAO paybackDAO) {
        this.paybackDAO = paybackDAO;
    }

}
