package net.unit8.sessionista;

import net.unit8.sessionista.encoder.MsgpackStateEncoder;
import org.junit.Test;

/**
 * @author kawasima
 */
public class SessionTest {
    @Test
    public void test() {
        Session session = new Session();
        session.put("person", Person.createPersonForTest());
        session.write("integer", 1);
        session.write("null", null);
        String encoded = session.encode();
        System.out.println(encoded);
        session.deleteAll();

        session.decode(encoded);
        System.out.println(session.read("person"));
    }

    @Test
    public void testMsgpackEncoder() {
        MsgpackStateEncoder encoder = new MsgpackStateEncoder();
        encoder.register(Address.class, Person.class);
        Session session = new Session();
        session.write("person", Person.createPersonForTest());
        session.write("integer", 1);
        session.write("null", null);
        String encoded = session.encode();
        System.out.println(encoded);
        session.deleteAll();

        session.decode(encoded);
        System.out.println(session.read("person"));

    }
}
