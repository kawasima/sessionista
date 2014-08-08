package net.unit8.sessionista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kawasima
 */
public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private List<Address> addressList;
    private Integer age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static Person createPersonForTest() {
        Person person = new Person();
        person.setFirstName("Yoshitaka");
        person.setLastName("kawasima");

        List<Address> addressList = new ArrayList<Address>();
        Address address1 = new Address();
        address1.setPostalCd("123-3333");
        address1.setPrefecture("東京都");
        address1.setCity("杉並区");
        addressList.add(address1);

        Address address2 = new Address();
        address2.setPostalCd("888-3333");
        address2.setPrefecture("長崎県");
        address2.setCity("長崎市");
        addressList.add(address2);

        person.setAddressList(addressList);
        return person;
    }
}
