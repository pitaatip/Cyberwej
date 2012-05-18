package pl.edu.agh.cyberwej.web.beans.group;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.data.objects.Group;

/**
 * 
 * @author Krzysztof
 * 
 */
@ManagedBean
@RequestScoped
public class GroupInformationBean {

    @ManagedProperty(value = "#{groupService}")
    private GroupService groupService;

    private Group group;

    @PostConstruct
    public void init() {
        this.group = new Group();
        this.group.setName("Proteza");
    }

    /**
     * @return the groupService
     */
    public GroupService getGroupService() {
        return this.groupService;
    }

    /**
     * @param groupService
     *            the groupService to set
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * @return the group
     */
    public Group getGroup() {
        return this.group;
    }

}
