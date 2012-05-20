package pl.edu.agh.cyberwej.web.beans.payment;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.business.services.api.PaymentService;
import pl.edu.agh.cyberwej.common.objects.service.ParticipantInformation;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;

/**
 * 
 * @author Krzysztof
 * 
 */
@ManagedBean
@RequestScoped
public class PaymentInformationBean extends BaseBean {

    private static String SELECTEDPAYMENT = "selectedPayment";

    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;

    private Payment payment;

    private List<ParticipantInformation> participantInformations;

    @PostConstruct
    public void init() {
        String idString = super.getParameter(SELECTEDPAYMENT);
        if (idString != null) {
            int id = Integer.parseInt(idString);
            this.payment = this.paymentService.getPaymentWithDependencies(id);
            this.participantInformations = this.paymentService
                    .getParticipants(this.payment);
        }
    }

    /**
     * @return the payment
     */
    public Payment getPayment() {
        return this.payment;
    }

    /**
     * @return the paymentService
     */
    public PaymentService getPaymentService() {
        return this.paymentService;
    }

    /**
     * @param paymentService
     *            the paymentService to set
     */
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public float getPaymentCost() {
        return this.paymentService.getPaymentCost(this.payment);
    }

    public int getInvolvedUsersCount() {
        return this.paymentService.getInvolvedUsersCount(this.payment);
    }

    public List<PaymentItemWrapper> getPaymentItems() {
        List<PaymentItemWrapper> paymentItems = new LinkedList<PaymentItemWrapper>();
        for (PaymentItem paymentItem : this.payment.getPaymentItems())
            paymentItems.add(new PaymentItemWrapper(paymentItem));
        return paymentItems;
    }

    /**
     * @return the participantInformations
     */
    public List<ParticipantInformation> getParticipantInformations() {
        return this.participantInformations;
    }

}
