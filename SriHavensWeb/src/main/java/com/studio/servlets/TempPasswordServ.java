package com.studio.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.studio.bean.MessageBean;
import com.studio.bean.TransactionResponse;
import com.studio.bean.UserDetails;
import com.studio.dao.UserAdminDAO;
import com.studio.util.RandomStringGenerator;
import com.studio.validator.Validator;

/**
 * Servlet implementation class TempPasswordServ
 */
public class TempPasswordServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TempPasswordServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		String sessionID = (String) session.getAttribute("SID");
		String userID = (String) request.getParameter("USERID");
		MessageBean mBean = new MessageBean();
		boolean captchaStatus = (boolean) session.getAttribute("CVSTATUS");
		if(session.getId().equals(sessionID) && captchaStatus) {
			mBean = generateTemporaryPassword(userID, getServletConfig().getServletContext());
			if(mBean.getStatus().equals("Y")) {
				session.removeAttribute("CVSTATUS");
			}
		}
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.print(new Gson().toJson(mBean));
		out.flush();
	}
	private MessageBean generateTemporaryPassword(String userID, ServletContext servletContext) {
		
		String status = "N";
		String message = "Reset password failed";
		String tempPswd = new RandomStringGenerator(10,true,true).next();
		UserDetails uDetails = UserAdminDAO.getUserDetail(userID);
		if(uDetails.getEMAIL()!=null && Validator.isEmailValid(uDetails.getEMAIL())) {
			TransactionResponse tResponse =  UserAdminDAO.storeTemporaryPassword(userID, tempPswd);
			if(tResponse.isTransactionStatus()) {
				boolean isMailSent = sendTemporaryPasswordEmail(uDetails.getEMAIL(), uDetails.getNAME(), tempPswd, servletContext);
				if(isMailSent) {
					message = "Temporary password mail has been sent to "+uDetails.getEMAIL()+". For security reasons,"+
							"change your password after logging into the portal with temporary password.";
					status = "Y";
				}
			}
		}
		
		return new MessageBean(message, status, null);
		
	}
	public static boolean sendTemporaryPasswordEmail(String email,String name, String tmpPswd, ServletContext servletContext) {
		boolean isMailSent = false;
		Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.class",
	            "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "465");

	    Session session = Session.getDefaultInstance(props,
	        new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication("automailer.rkstudio@gmail.com","dwcz vfna qwik wdgu");
	            }
	        });

	    try {

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("automailer.rkstudio@gmail.com"));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(email));
	        message.setSubject("Your temporary password to login into RK Studio portal.");
	        String mailBody="Dear "+name+"," +
	                "\n\n Please find the below attached temporary password to login into RK Studio portal. For security reason, Please change"
	                + "the temporary password once you login into the portal.\n\n Temporary Password: <b>"+tmpPswd+"</b>";
	        URL fpURL=null;
			try {
				fpURL = servletContext.getResource("/WEB-INF/Views/forgot_password_email.html");
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			try {
				mailBody = Files.readString(Path.of(fpURL.toURI()));
				mailBody = mailBody.replace("{0}", name);
				mailBody = mailBody.replace("{1}", tmpPswd);
				
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			message.setContent(mailBody, "text/html; charset=utf-8");
	        Transport.send(message);
//Mail password - Raghu@1512
//Mail - automailer.rkstudio@gmail.com
//App name - RKStudioWebPortal
	        isMailSent=true;

	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
	    return isMailSent;
	}
	

}
