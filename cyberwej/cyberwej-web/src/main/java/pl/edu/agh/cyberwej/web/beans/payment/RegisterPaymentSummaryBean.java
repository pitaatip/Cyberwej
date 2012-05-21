/**
 * 
 */
package pl.edu.agh.cyberwej.web.beans.payment;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.business.services.api.PaymentService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.data.objects.PaymentParticipation;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;

/**
 * @author Pita
 * 
 */
@ManagedBean
@RequestScoped
public class RegisterPaymentSummaryBean extends BaseBean {
    
    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;

    public String getPaymentName() {
        return (String) getMap4Stuff().get("PaymentName");
    }

    public Group getGroup() {
        return (Group) getMap4Stuff().get("SelectedGroup");
    }

    @SuppressWarnings("unchecked")
    public List<PaymentItem> getItems() {
        return (List<PaymentItem>) getMap4Stuff().get("ItemsList");
    }

    @SuppressWarnings("unchecked")
    public List<PaymentParticipation> getPaymentParticipators() {
        return (List<PaymentParticipation>) getMap4Stuff().get("paymentParticipators");
    }

    public String registerPayment() {
        getPaymentService().registerPayment(getGroup(), new HashSet<PaymentItem>(getItems()),
                new HashSet<PaymentParticipation>(getPaymentParticipators()), getPaymentName());
        getMap4Stuff().put("PaymentName", null);
        getMap4Stuff().put("SelectedGroup", null);
        getMap4Stuff().put("ItemsList", null);
        getMap4Stuff().put("paymentParticipators", null);
        return "main";
    }
    
    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
