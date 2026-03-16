package com.studio.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.studio.constants.URLS;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter extends HttpFilter implements Filter {
	ArrayList<String> URIListBeforeLogin = new ArrayList<String>();
	ArrayList<String> URIListDefaultPswd = new ArrayList<String>();
	/**
	 * 
	 */
	private static final long serialVersionUID = -4746311759037458991L;

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public AuthenticationFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);
		String loggedInUser = (String) session.getAttribute("UID");
		Object isPSWDDefault = session.getAttribute("ISPSWDDEFAULT");
		String contextPath = httpRequest.getContextPath();
		String requestURI = httpRequest.getRequestURI();
		String[] tokens = requestURI.split("/");
		if(tokens.length>2) {
			requestURI = ("/"+tokens[1]+"/"+tokens[2]);
		}
		if (loggedInUser == null || loggedInUser == "") {
			
			if (!(URIListBeforeLogin.contains(requestURI))) {
//				System.out.println(requestURI);
				((HttpServletResponse) response).sendRedirect(contextPath + URLS.LOGIN);
			} else {
				chain.doFilter(request, response);
			}
		} else {
			if (isPSWDDefault != null && (boolean) isPSWDDefault && !URIListDefaultPswd.contains(requestURI)) {

				((HttpServletResponse) response).sendRedirect(contextPath + URLS.DEFAULT_PSWD_CHANGE);
			} else {
				chain.doFilter(request, response);
			}
			// pass the request along the filter chain

		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		String contextPath = fConfig.getServletContext().getContextPath();
			
		URIListBeforeLogin.add(contextPath + URLS.LOGON_SERVLET);
		URIListBeforeLogin.add(contextPath + URLS.LOGIN_PAGE);
		URIListBeforeLogin.add(contextPath + URLS.LOGIN);
		URIListBeforeLogin.add(contextPath + URLS.CREATE_CAPTCHA);
		URIListBeforeLogin.add(contextPath + URLS.TEMP_PSWD_SERV);
		URIListDefaultPswd.add(contextPath + URLS.DEFAULT_PSWD_CHANGE);
		URIListDefaultPswd.add(contextPath + "/Scripts");
		URIListDefaultPswd.add(contextPath + "/Styles");
		URIListDefaultPswd.add(contextPath + "/Images");
		URIListDefaultPswd.add(contextPath + "/Music");
		URIListDefaultPswd.add(contextPath + "/anonymous");
		URIListDefaultPswd.add(contextPath + "/DefaultPswdServ");
		URIListDefaultPswd.add(contextPath + URLS.LOGOUT_SERVLET);

	}

}
