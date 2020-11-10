import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String email;
        char[] password;
        while (true) {
            System.out.println("Enter your email");
            email = sc.nextLine();
            if(!isValid(email)) {
                System.out.println("Entered email is not valid");
                continue;
            }
            System.out.println("Enter your password");
            password = System.console().readPassword();
            break;
        }

        String checkString = generateString(30);
        sendEmail(email, checkString);

        System.out.println("Please insert received code");
        while (true) {
            String code = sc.nextLine();
            if(code.equals(checkString)) {
                System.out.println("Registration complete, welcome!!!");
                break;
            }
            else {
                System.out.println("Verification code incorrect, please try again");
                continue;
            }
        }
    }

    public static void sendEmail(String to, String checkString) {
        String from = "dvalera225@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("dvalera225@gmail.com", "Destrugatorul22");

            }

        });

        session.setDebug(false);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("App confirmation code");
            message.setText(checkString);
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    public static String generateString(int n) {

        byte[] arr = new byte[256];
        new Random().nextBytes(arr);

        String randString = new String(arr, Charset.forName("UTF-8"));
        StringBuffer stringBuffer = new StringBuffer();

        for(int k = 0; k < randString.length(); k++) {
            char ch = randString.charAt(k);
            if(((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {
                stringBuffer.append(ch);
                n--;
            }
        }

        return stringBuffer.toString();
    }
    public static boolean isValid(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
