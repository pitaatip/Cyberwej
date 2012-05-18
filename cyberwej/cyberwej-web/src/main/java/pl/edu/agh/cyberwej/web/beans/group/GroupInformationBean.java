package pl.edu.agh.cyberwej.web.beans.group;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;

/**
 * 
 * @author Krzysztof
 * 
 */
@ManagedBean
@RequestScoped
public class GroupInformationBean extends BaseBean {

    private static final String SELECTEDGROUP = "selectedGroup";

    @ManagedProperty(value = "#{groupService}")
    private GroupService groupService;

    private Group group;

    @PostConstruct
    public void init() {
        int id = Integer.parseInt(super.getParameter(SELECTEDGROUP));
        this.group = this.groupService.getGroupWithMembers(id);
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

    public List<GroupMembership> getGroupMembers() {
        return new LinkedList<GroupMembership>(this.group.getGroupMembers());
    }
}
