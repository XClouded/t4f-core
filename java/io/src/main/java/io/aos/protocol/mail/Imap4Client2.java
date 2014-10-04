package io.aos.protocol.mail;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This class tests the stability of James server by performing following actions:
 * </p>
 * <ul>
 * <li>Login</li>
 * <li>fetching mail</li>
 * <li>logout</li>
 * </ul>
 */
public class Imap4Client2 {
    private static Logger LOGGER = LoggerFactory.getLogger(Imap4Client2.class);

    private static String FROM_ADDRESS = "eric@localhost.net";
    private static String TO_ADDRESS = "eric@localhost.net";
    private static String SUBJECT = "Stress Testing James";
    private static String USERNAME = "eric@localhost.net";
    private static String PASSWORD = "eric";
    private static String HOSTNAME = "localhost";
    private static String STORE = "imap";
    private static String FOLDER = "INBOX";
    private static int PORT_IMAP = 1433;
    private static int PORT_SMTP = 2525;

    public static void main(String[] args) throws NoSuchProviderException, MessagingException, InterruptedException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.starttls.enable", bundle.getString("password"));
        props.put("mail.smtp.host", HOSTNAME);
        props.put("mail.smtp.port", PORT_SMTP);

        final Session session = Session.getDefaultInstance(new Properties(), null);
        /*
         * new javax.mail.Authenticator() {
         * 
         * @Override protected PasswordAuthentication
         * getPasswordAuthentication() { return new
         * PasswordAuthentication(userName, password); } });
         */
        
        runTest(session, USERNAME);


    }

    private static void runTest(Session session, String userName) {
        
        Store store = null;
        Folder folder = null;
    
        while (true) {
        
            try {
    
                // get session
                store = session.getStore(STORE);
    
                // login
                if (!store.isConnected()) {
                    store.connect(HOSTNAME, PORT_IMAP, userName, PASSWORD);
                }
    
                LOGGER.info("logged in");
    
                // get folder
                folder = store.getFolder(FOLDER);
                
                // open folder
                if (!folder.isOpen()) {
                    folder.open(com.sun.mail.imap.IMAPFolder.READ_WRITE);
                }
    
                LOGGER.info("open inbox");
    
                int i = 0;
    
                LOGGER.info("Messgae count  in inbox :" + folder.getMessageCount());
    
                // Fetch mails
                while (i < 10) {
                    Thread.sleep(20);
                    folder.getMessages();
                    i++;
                }
                LOGGER.info("refreshed inbox");
    
                Thread.sleep(1000);
                
                // close folder
                // folder.close(false);
    
                // send mail
                sendMail(session, userName);
                LOGGER.info("mail sent");
                
                // logout
                store.close();
                LOGGER.info("logged out");
    
                LOGGER.info("ended");
    
            } catch (Exception e) {
                e.printStackTrace();
            }
    
        }
    
    }

    private static void sendMail(Session session, String userName) {

        try {
            
            Transport transport = session.getTransport("smtp");
            transport.connect(HOSTNAME, PORT_SMTP, userName, PASSWORD);
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM_ADDRESS));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(TO_ADDRESS));

            message.setSubject(SUBJECT + " " + new Date().toString());
            Multipart mp = new MimeMultipart();
            message.setSentDate(new Date());

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent("Test James", "text/html");
            mp.addBodyPart(htmlPart);

            message.setContent(mp);
            message.saveChanges();

            Address[] recips = message.getAllRecipients();
            StringBuffer sb = new StringBuffer();
            Set<Address> receiptSet = new HashSet<Address>();
            for (Address recip : recips) {
                receiptSet.add(recip);
            }

            Iterator<Address> addressIter = receiptSet.iterator();
            int i = 0;
            Address[] receiptAddress = new Address[receiptSet.size()];
            while (addressIter.hasNext()) {
                Address receipts = addressIter.next();
                receiptAddress[i] = receipts;
                sb.append(receipts);
                sb.append(",");
                i++;
            }
            sb.deleteCharAt(sb.length() - 1);

            transport.sendMessage(message, receiptAddress);
            transport.close();

            // LOGGER.info(" mail sent ");

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
