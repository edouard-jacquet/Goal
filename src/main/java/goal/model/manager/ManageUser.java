package goal.model.manager;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import goal.model.bean.Notification;
import goal.model.bean.User;
import goal.model.dao.UserDAO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManageUser {
	
	// Constantes de configuration
	private final String REGEX_LOGIN = "[a-zA-Z][a-zA-Z0-9.]+";
	private final String REGEX_PASSWORD = "[a-zA-Z][a-zA-Z0-9.]+";
	private final String SESSION_NAME = "guest";
	private final String COOKIE_NAME = "goal_auth";
	private final String COOKIE_DELIMITER = "----";
	private final int COOKIE_MAXAGE = 60 * 60 * 24;
	// Liste des notifications (default, success, info, warning, error)
	private List<Notification> notifications = new LinkedList<Notification>();
	// DAO pour les utilisateurs
	private UserDAO userDAO = new UserDAO();
	
	public void create(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		// Controle des champs Login et Password
		if(login.length() == 0) {
			notifications.add(new Notification("alert", "Login is empty."));
		}
		else if(!Pattern.matches(this.REGEX_LOGIN, login)) {
			notifications.add(new Notification("alert", "Login contain invalid characters."));
		}
		if(password.length() == 0) {
			notifications.add(new Notification("alert", "Password is empty."));
		}
		else if(!Pattern.matches(this.REGEX_PASSWORD, password)) {
			notifications.add(new Notification("alert", "Password contain invalid characters."));
		}
		
		if(!notifications.isEmpty()) { throw new Exception(); }
		
		// Controle si l'utilisateur existe déja
		if(!userDAO.notExist(login)) { 
			notifications.add(new Notification("alert", "User exist already."));
			throw new Exception();
		}
		
		// Ajout de l'utilisateur
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setKey(this.generateSecureKey());
		if(!userDAO.add(user)) {
			notifications.add(new Notification("alert", "User hasn't been created."));
			throw new Exception();
		}
			
	}
	
	public void authentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		
		// Controle des champs Login et Password
		if(login.length() == 0) {
			notifications.add(new Notification("alert", "Login is empty."));
		}
		else if(!Pattern.matches(this.REGEX_LOGIN, login)) {
			notifications.add(new Notification("alert", "Login contain invalid characters."));
		}
		if(password.length() == 0) {
			notifications.add(new Notification("alert", "Password is empty."));
		}
		else if(!Pattern.matches(this.REGEX_PASSWORD, password)) {
			notifications.add(new Notification("alert", "Password contain invalid characters."));
		}
		
		if(!notifications.isEmpty()) { throw new Exception(); }
		
		// Recuperation de l'utilisateur
		User user = userDAO.getByConnection(login, password);
		
		if(user == null) { 
			notifications.add(new Notification("alert", "User doesn't exist."));
			throw new Exception();
		}
		
		// Mise en session de l'utilisateur
		this.setSession(user, request);
		// Mise en cookie de l'utilisateur
		if(remember != null) {
			this.setCookie(user.getId() + this.COOKIE_DELIMITER + user.getKey(), this.getTimeStamp() + this.COOKIE_MAXAGE, response);
		}
	}
	
	public void disconnection(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		session.invalidate();
		this.setCookie("", 0, response);
	}
	
	public boolean isLogged(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		if(session.getAttribute(this.SESSION_NAME) != null && session.getAttribute(this.SESSION_NAME) instanceof User) {
			return true;
		}	
		return false;
	}
	
	public void authenticationCookie(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		// Verifie que le user n'est pas deja connecte
		if(session.getAttribute(this.SESSION_NAME) == null || (session.getAttribute(this.SESSION_NAME) instanceof User) == false) {
			if(this.getCookieValue(this.COOKIE_NAME, request) != null) {
				// Coupe la valeur du cookie
				String[] cookie = this.getCookieValue(this.COOKIE_NAME, request).split(this.COOKIE_DELIMITER);
				long id = Long.parseLong(cookie[0]);
				String key = cookie[1];
				
				// Recupere le user correpondant
				User user = userDAO.getById(id);
				if(user != null && user.getKey().equals(key)) {
					this.setSession(user, request);
					this.setCookie(user.getId() + this.COOKIE_DELIMITER + user.getKey(), this.getTimeStamp() + this.COOKIE_MAXAGE, response);
				}
				else {
					this.setCookie("", 0, response);
				}
			}
		}
	}
	
	private void setSession(User user, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.setAttribute(this.SESSION_NAME, user);
	}
	
	public User getUser(HttpSession session){
		return (User) session.getAttribute(this.SESSION_NAME);
	}
	
	private void setCookie(String value, int maxAge, HttpServletResponse response) {
		Cookie cookie = new Cookie(this.COOKIE_NAME, value);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	private String getCookieValue(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie != null && name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	private String generateSecureKey() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}
	
	private int getTimeStamp() {
		return (int)(new Date().getTime()/1000);
	}
	
	public List<Notification> getNotification() {
		return this.notifications;
	}

}
