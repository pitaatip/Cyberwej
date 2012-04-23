package org.agh.iosr.cyberwej.data.dao.interfaces;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payback;
import org.agh.iosr.cyberwej.data.objects.User;

public interface PaybackDAO {

	public boolean addPayback(User debtor, User investor, Group group,
			float amount);

	public void removePayback(Payback payback);

	public boolean updatePayback(Payback payback);
	
}
