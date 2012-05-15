package pl.edu.agh.cyberwej.web.beans.views.user;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;

/**
 * 
 * @author Krzysztof
 * 
 */
@ManagedBean
@RequestScoped
public class UserInformationBean extends BaseBean {

    private User user;

    @PostConstruct
    public void construct() {

    }
}
