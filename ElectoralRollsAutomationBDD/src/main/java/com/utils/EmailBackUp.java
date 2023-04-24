package com.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailBackUp {
	public  void sendReport() throws Exception{
		final String fromEmail = "nik.piu@gmail.com";
		String[] toEmail = {"vidisha.qa@gmail.com"}; 
		String host = "smtp.gmail.com";
		//https://www.google.com/settings/security/lesssecureapps (allow to send mail)
		Properties properties = System.getProperties();
		
		properties.put("mail.smtp.host", host);
	    properties.put("mail.smtp.port", "465");
	    properties.put("mail.smtp.ssl.enable", "true");
	    properties.put("mail.smtp.auth", "true");

	    // Get the Session object.// and pass username and password
	    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

	        protected PasswordAuthentication getPasswordAuthentication() {

	            return new PasswordAuthentication(fromEmail, "dpavxrdcjzplszqv");//Passw@rd@123

	        }

	    });

	    // Used to debug SMTP issues
	    session.setDebug(true);

	    try {
	        // Create a default MimeMessage object.
	        MimeMessage message = new MimeMessage(session);

	        // Set From: header field of the header.
	        message.setFrom(new InternetAddress(fromEmail));

	        // Set To: header field of the header.
	        for(int i=0;i<toEmail.length;i++) {
	        	System.out.println(toEmail[i]);
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail[i]));
	        }
	        // Set Subject: header field
	        message.setSubject("QA Assignment Automation Execution");
	        
	        
	        
	        // Fill the message

	        String html = "<div>Good Morning All,</div><br>	"
		        	
	        		+"<div>Automation Execution has been successfully done </div><br>"
    				
	        		+ "<br><br><span>Thankyou !!</span><br><br>"
	        		+"<div>Best Regards,</div>"
	        		+ "<div>Vidisha Shedge</div>.";
	        		
	        System.out.println(html);
	 
	 
	        // Now set the actual message
	        message.setText("This is actual message");
	        // Create the message part 
	        BodyPart messageBodyPart = new MimeBodyPart();
	        
	        // Fill the message
	        messageBodyPart.setContent(html,"text/html; charset=utf-8");

	        // Create a multipart message
	        Multipart multipart = new MimeMultipart();

	        // Set text message part
	        multipart.addBodyPart(messageBodyPart);
	        
	        // Part two is attachment
	        BodyPart messageBodyPart1 = new MimeBodyPart();
	        String currentDir = System.getProperty("user.dir");
	        File dir_currentDir = new File(currentDir);
	       

	        String cucumberReportFile = currentDir+"/target/Cucumber-Reports_QA.zip";
	        String recordingFile = currentDir+"/test-recordings/Execution_QA.avi";

	        MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
	        MimeBodyPart messageBodyPart3 = new MimeBodyPart();  
	        
	       
			
	        String filename_for_recording = "Execution_QA_"+StaticVariables.strTimeStamp+".avi";
	        DataSource fds = new FileDataSource(recordingFile);
	       
	        Path path = Paths.get(recordingFile);
	        //******************* size of a file (in bytes)********************//
	        double bytes = Files.size(path);
	        double megabytes =  bytes/(1024*1024);
	        System.out.println( "megabyte : "+megabytes);

	        messageBodyPart2.setDataHandler(new DataHandler(fds));	
	        messageBodyPart2.setFileName(filename_for_recording);
//	        String filename_for_Report = "Cucumber-Report_QA.zip";

	        DataSource report = new FileDataSource(cucumberReportFile);
//	        messageBodyPart3.setDataHandler(new DataHandler(report));
//	        messageBodyPart3.setFileName(filename_for_Report);

	        multipart.addBodyPart(messageBodyPart);
	        multipart.addBodyPart(messageBodyPart2);
//	        multipart.addBodyPart(messageBodyPart3);

	        // Send the complete message parts
	        message.setContent(multipart );

	        System.out.println("sending...");
	        // Send message
	        Transport.send(message);
	        System.out.println("Sent message successfully....");
	    } catch (MessagingException mex) {
	        mex.printStackTrace();
	    }	
	}
}
