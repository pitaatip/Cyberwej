/**
 * 
 */
package pl.edu.agh.cyberwej.web.beans.payment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.data.objects.Product;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

/**
 * @author Pita
 * 
 */
@ManagedBean
@RequestScoped
public class RegisterPaymentStep2Bean extends BaseBean {
    private User user;
    private Group group;
    private String[] selectedGroupMemberships;
    private String amount;
    private String product;
    private String price;
    private Group selectedGroup;
    private Map<String, Object> userGroup;

    @ManagedProperty("#{sessionContextBean}")
    private SessionContextBean sessionContextBean;

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    @ManagedProperty(value = "#{groupService}")
    private GroupService groupService;

    @PostConstruct
    public void init() {
        setUser(getSessionContextBean().getLoggedUser());
        setGroup((Group) getMap4Stuff().get("SelectedGroup"));
    }

    public Map<String, Integer> getGroupMemberships() {
        Group groupWithMembers = getGroupService().getGroupWithMembersAndPayments(getGroup().getId());
        Map<String, Integer> map = new HashMap<String, Integer>();
        Set<GroupMembership> groupMembers = groupWithMembers.getGroupMembers();
        for (GroupMembership groupMembership : groupMembers) {
            map.put(groupMembership.getUser().getLogin(), groupMembership.getUser().getId());
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public List<PaymentItem> getItemsList() {
        if (getMap4Stuff().get("ItemsList") == null) {
            getMap4Stuff().put("ItemsList", new LinkedList<PaymentItem>());
        }
        return (List<PaymentItem>) getMap4Stuff().get("ItemsList");
    }

    public String addItem() {
        PaymentItem item = new PaymentItem();
        item.setCount(Integer.parseInt(amount));
        item.setPrice(Float.parseFloat(price));
        Product product2 = new Product();
        product2.setName(product);
        item.setProduct(product2);
        Set<User> consumers = new HashSet<User>();
        for (String userId : selectedGroupMemberships) {
            User userById = userService.getUserById(Integer.parseInt(userId));
            consumers.add(userById);
        }
        item.setConsumers(consumers);
        getItemsList().add(item);
        return null;
    }

    public String next() {
        return "registerPaymentPageStep3";
    }

    public String removeItem() {
        int itemId = Integer.parseInt(getParameter("itemToDelete"));
        getItemsList().remove(itemId);
        return null;
    }


    public SessionContextBean getSessionContextBean() {
        return sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Group selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public Map<String, Object> getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(Map<String, Object> userGroup) {
        this.userGroup = userGroup;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String[] getSelectedGroupMemberships() {
        return selectedGroupMemberships;
    }

    public void setSelectedGroupMemberships(String[] selectedGroupMemberships) {
        this.selectedGroupMemberships = selectedGroupMemberships;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
