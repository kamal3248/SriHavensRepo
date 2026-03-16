package com.studio.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.studio.Captcha;
import com.studio.Config;
import com.studio.GeneratedCaptcha;
import com.studio.bean.CaptchaResponseBean;

/**
 * Servlet implementation class CreateCaptcha
 */
public class CaptchaHandlerServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CaptchaHandlerServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String mode = (String) request.getParameter("MODE");
		HttpSession session = request.getSession(false);
		CaptchaResponseBean crBean = new CaptchaResponseBean();
		if(mode!=null && mode.equals("GENERATE")) {
			Config customConfig = new Config();
			// Customize the configuration properties as needed
			customConfig.setDark(true);
			Captcha captcha = new Captcha(customConfig);
	        GeneratedCaptcha generatedCaptcha = captcha.generate();
	        BufferedImage captchaImage = generatedCaptcha.getImage();
	        
	        //crBean.setCAPTCHAIMAGESTRING(ImageToJSON.convertImageToJSON(captchaImage));
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	        ImageIO.write(captchaImage,"png",outStream);
	        byte[] imageBytes = outStream.toByteArray();
	        Base64.Encoder encoder = Base64.getEncoder();
	        String encoding = "data:image/png;base64," + encoder.encodeToString(imageBytes);
	        crBean.setCAPTCHAIMAGESTRING(encoding);
	        
	        String captchaCode = generatedCaptcha.getCode();
//	        response.setContentType("image/png");
//	        ImageIO.write(captchaImage, "png", response.getOutputStream());
	        session.setAttribute("SID", session.getId());
	        session.setAttribute("CCODE", captchaCode);
	        
		}
		else if(mode.equals("VALIDATE")) {
			String storedCaptchaCode = (String) session.getAttribute("CCODE");//Captcha code which was stored previously
			
			String sessionID = (String) session.getAttribute("SID");//Session ID stored previously
			String userCaptchCode = (String) request.getParameter("CAPTCHACODE");
			//System.out.println(userCaptchCode+" "+storedCaptchaCode);
			if(session.getId().equals(sessionID)) {
				boolean status = userCaptchCode.equals(storedCaptchaCode);
				crBean.setCAPTCHAMATCHING(status);
				session.setAttribute("CVSTATUS", status);
				session.removeAttribute("CCODE");
			}
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.print(new Gson().toJson(crBean));
		out.flush();
	}

}
