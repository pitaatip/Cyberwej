/**
 * 
 */
package pl.edu.agh.cyberwej.web.beans.group;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;

/**
 * @author Pita
 * 
 */

@ManagedBean
@RequestScoped
public class AddGroupBean extends BaseBean {
    private static final String GROUP2ADD = "group2add";
    private Group group = new Group();

    public String next() {
        getMap4Stuff().put(GROUP2ADD, getGroup());
        return "addGroupSummary";
    }

    public Group getGroup() {
        if (group == null) {
            group = (Group) getMap4Stuff().get(GROUP2ADD);
        }
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
