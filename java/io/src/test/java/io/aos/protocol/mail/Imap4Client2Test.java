package io.aos.protocol.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class Imap4Client2Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Imap4Client2Test.class);

    private static final Random RANDOM = new Random();

    private String imapPort = "1433";
    private String smtpPort = "2525";

    private MimeMessage message;
    private Session session;
    private Store imapStore;
    private Folder testFolder;

    private Authenticator defaultAuthenticator = new Authenticator() {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("eric@localhost.net", "eric");
        }
    };

    @Before
    public void setUp() throws MessagingException {
        
        createMailSession(defaultAuthenticator);
        message = new MimeMessage(session);
        message.setSentDate(new Date());
        openImapStore();
        testFolder = getOpenedInboxFolder();
    }

    @After
    public void tearDown() {
        close(testFolder);
        close(imapStore);
    }

    private void createMailSession(Authenticator authenticator) {
        
        Properties props = new Properties();

        props.put("mail.debug", "true");
        
        props.put("mail.smtp.host", "localhost.net");
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.connectiontimeout", "30000");
        props.put("mail.smtp.timeout", "30000");

        props.put("mail.imap.host", "localhost.net");
        props.put("mail.imap.port", imapPort);
        props.put("mail.imap.connectiontimeout", "30000");
        props.put("mail.imap.timeout", "30000");

        session = Session.getDefaultInstance(props, authenticator);
        
    }

    private void openImapStore() throws MessagingException {
        createMailSession(defaultAuthenticator);
        imapStore = session.getStore("imap");
        if (!imapStore.isConnected()) {
            connectCurrentUserTo();
        }
    }

    @Test
    public void testSend() throws Exception {
        final int msgCnt = 10000;
        for (int i = 0; i < msgCnt; i++) {
            setMsgFrom("testSend");
            String subject = setMsgDefaultSubject();
            setMsgTo("eric@localhost.net");
            String body = "this is a message";
            setMsgBody(body);
            sendMessage();
            SearchTerm subjectSearch = new SubjectTerm(subject);
            Message[] msgs = retrieveMessages(subjectSearch, 1);
            Message msg = msgs[0];
            Object contentObj = msg.getContent();
            if (contentObj instanceof InputStream) {
                InputStream is = (InputStream) contentObj;
                String content = IOUtils.toString(is);
                assertEquals(body, content.trim());
            } else {
                fail("Unexpected content type");
            }
            LOGGER.info("msg found");
            sleep(50);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // do nothing
        }
    }

    private void setMsgFrom(String fromUser) throws MessagingException {
        setMsgFrom(fromUser, "icw.cm.test");
    }
    private void setMsgFrom(String fromUser, String fromDomain) throws MessagingException {
        message.setFrom(new InternetAddress(fromUser + "@" + fromDomain));
    }
    private void setMsgTo(String toAddress) throws MessagingException {
        message.setRecipients(Message.RecipientType.TO, toAddress);
    }
    private String setMsgDefaultSubject() throws MessagingException {
        String subject = "subject with random nr: " + System.currentTimeMillis()
                + " " + RANDOM.nextInt(100);
        return setMsgSubject(subject);
    }
    private String setMsgSubject(String subject) throws MessagingException {
        message.setSubject(subject);
        return subject;
    }
    private void setMsgBody(String body) throws MessagingException {
        String contentType = "text/txt; charset=UTF-8";
        message.setContent(body, contentType);
    }

    private void sendMessage() throws MessagingException {
        Transport.send(message);
    }

    /*
     * When searching with an expected count, this has the side effect, that
     * the mailbox is ready, since we poll a few times.
     */
    private Message[] retrieveMessages(SearchTerm searchTerm, int expectedCnt)
        throws MessagingException {
        getOpenedInboxFolder();
        testFolder.getMessageCount();
        int count = 20;
        do {
            // there is a bug inputStreamapache james (JAMES-1476)
            // which triggers a exception looking as follows:
            // java.lang.ArrayIndexOutOfBoundsException: message number (8) outputStream of bounds (7)
            Message[] javamailMessages = testFolder.search(searchTerm);
            if (expectedCnt == -1 || expectedCnt == javamailMessages.length) {
                return javamailMessages;
            }
            sleep(100);
        } while (count-- > 0);
        fail("Did not find message");
        return null;
    }

    private Folder getOpenedFolder(String folderName) throws MessagingException {
        Folder folder = imapStore.getFolder(folderName);
        if (!folder.isOpen()) {
            folder.open(Folder.READ_ONLY);
        }
        return folder;
    }
    private Folder getOpenedInboxFolder() throws MessagingException {
        return getOpenedFolder("INBOX");
    }

    private void connectCurrentUserTo() throws MessagingException {
//      imapStore.connect("eric@localhost.net", "eric");
      imapStore.connect();
    }
    private void close(Store store) {
        try {
            if (store != null) {
                store.close();
            }
        } catch (MessagingException e) {
            LOGGER.debug("store.close() throws exception: " + e);
        }
    }

    private void close(Folder folder) {
        try {
            if (folder != null && folder.isOpen()) {
                folder.close(false);
            }
        } catch (MessagingException e) {
            LOGGER.debug("folder.close() throws exception: " + e);
        }
    }

}
