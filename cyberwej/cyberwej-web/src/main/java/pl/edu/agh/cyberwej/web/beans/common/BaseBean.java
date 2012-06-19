package pl.edu.agh.cyberwej.web.beans.common;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * This bean has common methods for all other jsf beans. Each bean should
 * inherit from this bean.
 * 
 * @author pita
 */
public abstract class BaseBean {

    private final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

    /**
     * @param paramName
     * @return returns parametr from faces context
     */
    public String getParameter(String paramName) {
        Map<String, String> params = context.getRequestParameterMap();
        return params.get(paramName);
    }

    public Map<String, Object> getMap4Stuff() {
        SessionContextBean sessionBean = (SessionContextBean) context.getSessionMap().get(
                "sessionContextBean");
        return sessionBean.getMap4Stuff();
    }

}
