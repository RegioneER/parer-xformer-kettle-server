/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.parer.kettle.ws.client;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.HashSet;
import java.util.Set;
import javax.xml.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Cappelli_F
 */
public class SOAPClientLoginHandler implements SOAPHandler<SOAPMessageContext> {

    public final static String WSSE_PREFIX = "wsse";
    public final static String WSU_PREFIX = "wsu";
    public final static String WSSE_LNAME = "Security";
    public final static String WSSE_XSD_URI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    public final static String WSU_SXD_URI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    public final static String AUTHN_STAUTS = "authnStatus";
    public final static QName QNAME_WSSE_HEADER = new QName(WSSE_XSD_URI, WSSE_LNAME, WSSE_PREFIX);

    public final static String USER = "user";
    public final static String PWD = "pass";

    private static Logger log = LoggerFactory.getLogger(SOAPClientLoginHandler.class);

    @Override
    public boolean handleMessage(SOAPMessageContext msgCtx) {
        boolean outbound = (boolean) msgCtx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outbound) {
            try {
                String user = null;
                String pass = null;
                log.debug("SOAPClientLoginHandler attivato. Aggiungo username e password all'header SOAP");
                if (msgCtx.containsKey(USER) && msgCtx.containsKey(PWD)) {
                    user = (String) msgCtx.get(USER);
                    pass = (String) msgCtx.get(PWD);
                } else {
                    throw new IllegalArgumentException(
                            "Please set username and password in message context before using this service");
                }
                SOAPEnvelope envelope = msgCtx.getMessage().getSOAPPart().getEnvelope();
                /*
                 * Vedi
                 * http://stackoverflow.com/questions/17058752/severe-saaj0120-cant-add-a-header-when-one-is-already-
                 * present
                 */
                if (envelope.getHeader() != null) {
                    envelope.getHeader().detachNode();
                }
                SOAPHeader header = envelope.addHeader();

                SOAPElement security = header.addChildElement(QNAME_WSSE_HEADER);
                SOAPElement usernameToken = security.addChildElement("UsernameToken", WSSE_PREFIX);
                usernameToken.addAttribute(new QName("xmlns:wsu"), WSU_SXD_URI);
                usernameToken.addAttribute(new QName("wsu:Id"), "UsernameToken-2");
                SOAPElement username = usernameToken.addChildElement("Username", WSSE_PREFIX);

                username.addTextNode(user);
                SOAPElement password = usernameToken.addChildElement("Password", WSSE_PREFIX);
                password.setAttribute("Type",
                        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
                password.addTextNode(pass);

            } catch (Exception e) {
                log.error("Si è verificato un errore durante l'inserimento di username e password nell'header SOAP", e);
            }
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
        // per usi futuri
    }

    @Override
    public Set<QName> getHeaders() {
        HashSet<QName> headers = new HashSet<>();
        headers.add(QNAME_WSSE_HEADER);
        return headers;
    }
}
