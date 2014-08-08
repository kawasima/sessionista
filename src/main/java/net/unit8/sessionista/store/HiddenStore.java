package net.unit8.sessionista.store;

import net.unit8.sessionista.HttpContextUtil;
import net.unit8.sessionista.SessionEntry;
import net.unit8.sessionista.SessionStore;

import javax.xml.bind.DatatypeConverter;
import java.util.List;
import java.util.UUID;

/**
 * @author kawasima
 */
public class HiddenStore extends SessionStore {
    private String hiddenParameterName = "_SESSIONISTA_";

    @Override
    public List<SessionEntry> load(UUID sessionId) {
        String hiddenValue = HttpContextUtil.getRequest().getParameter(hiddenParameterName);
        return decode(DatatypeConverter.parseBase64Binary(hiddenValue));
    }

    @Override
    public void save(UUID sessionId, List<SessionEntry> entries) {
        String hiddenValue = DatatypeConverter.printHexBinary(encode(entries));
        HttpContextUtil.getRequest().setAttribute(
                HiddenStore.class.getName() + "." + getName(),
                hiddenValue);
    }
}
