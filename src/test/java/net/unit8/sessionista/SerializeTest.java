package net.unit8.sessionista;

import com.thoughtworks.xstream.XStream;
import net.arnx.jsonic.JSON;
import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kawasima
 */
public class SerializeTest {
    @Test
    public void test() throws Exception {
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

        long t1 = System.currentTimeMillis();
        for (int i=0; i<50000; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            JAXB.marshal(person, baos);
        }
        System.err.println("JAXB:" + (System.currentTimeMillis() - t1));

        long t2 = System.currentTimeMillis();
        for (int i=0; i<50000; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(person);
        }
        System.err.println("Serialize:" + (System.currentTimeMillis() - t2));

        long t3 = System.currentTimeMillis();
        for (int i=0; i<50000; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            JSON.encode(person, baos);
        }
        System.err.println("JSONIC:" + (System.currentTimeMillis() - t3));

        long t4 = System.currentTimeMillis();
        XStream xstream = new XStream();
        for (int i=0; i<50000; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            xstream.toXML(person, baos);
        }
        System.err.println("XStream:" + (System.currentTimeMillis() - t4));
    }
}
