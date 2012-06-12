/**
 * 
 */
package pl.edu.agh.cyberwej.business.integrationTests;

import java.util.Date;
import java.util.Random;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author Pita
 * 
 */
public class TestObjectsFactory {
    public static User getMockUser() {
        User user = new User();
        Random random = new Random();
        user.setName("User" + random.nextInt());
        user.setSurname("UserSurname" + random.nextInt());
        user.setBirthday(new Date());
        user.setLogin("Login" + random.nextDouble());
        user.setLocation("Street " + random.nextInt() + " City: Krakow");
        user.setMail("user" + random.nextDouble() + "@gmail.com");
        user.setPassword("pass" + random.nextInt());
        user.setPhoneNumber(String.valueOf(getPhoneNumber()));
        return user;
    }

    public static Group getMockGroup() {
        Random random = new Random();
        Group group = new Group();
        group.setName("GroupName" + random.nextDouble());
        group.setCreationDate(new Date());
        return group;
    }

    private static Integer getPhoneNumber() {
        Random random = new Random();
        return random.nextInt() * 100000 + random.nextInt() * 10000 + random.nextInt() * 1000
                + random.nextInt() * 100 + random.nextInt() * 10 + random.nextInt() * 10;
    }

    public static Payment getMockPayment() {
        Payment payment = new Payment();
        payment.setDate(new Date());
        payment.setDescription("NewPymentDescription");
        return payment;
    }

}
