/**
 * 
 */
package pl.edu.agh.cyberwej.web.beans.payment;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author Pita
 * 
 */
@FacesConverter("pl.cyberwej.setConverter")
public class CustomSetConverter implements Converter {

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext
     * , javax.faces.component.UIComponent, java.lang.String)
     */
    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext
     * , javax.faces.component.UIComponent, java.lang.Object)
     */
    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        @SuppressWarnings("unchecked")
        Set<User> set = (Set<User>) arg2;
        StringBuilder builder = new StringBuilder();
        int nrOfUsers = set.size();
        int i = 0;
        for (User user : set) {
            i += 1;
            builder.append(user.getLogin());
            if (i < nrOfUsers) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

}
