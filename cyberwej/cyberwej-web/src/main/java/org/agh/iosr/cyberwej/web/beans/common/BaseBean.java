package org.agh.iosr.cyberwej.web.beans.common;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * This bean has common methods for all other jsf beans. Each bean should
 * inherit from this bean.
 * 
 * @author pita
 * 
 */
public abstract class BaseBean {

	private final ExternalContext context = FacesContext.getCurrentInstance()
			.getExternalContext();

	public String getParameter(String paramName) {
		Map<String, String> params = context.getRequestParameterMap();
		return params.get(paramName);
	}

}
