package goal.model.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import goal.model.bean.Notification;
import goal.model.bean.User;
import goal.model.dao.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManageUser {
	
	// Regex controle de champ
	private final String REGEX_LOGIN = "[a-zA-Z][a-zA-Z0-9.]+";
	private final String REGEX_PASSWORD = "[a-zA-Z][a-zA-Z0-9.]+";
	// Liste des erreurs
	private List<Notification> notifications = new LinkedList<Notification>();
	// DAO pour les utilisateurs
	private UserDAO userDAO = new UserDAO();
	
	public void authentication (HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		// Recuperation de l'utilisateur
		User user = userDAO.getUser(login, password);
		
		if(user == null) { 
			notifications.add(new Notification("alert", "User doesn't exist."));
			throw new Exception();
		}
		
		// Mise en session de l'utilisateur
		HttpSession session = request.getSession(true);
		session.setAttribute("guest", user);
	}
	
	public void disconnection(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		session.invalidate();
	}
	
	public void create (HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		User user = userDAO.getUser(login, password);
		if(user != null) { 
			notifications.add(new Notification("alert", "User exist already."));
			throw new Exception();
		}
		
		// Ajout de l'utilisateur
		if(!userDAO.addUser(login, password)) {
			notifications.add(new Notification("alert", "User hasn't been created."));
			throw new Exception();
		}
			
	}
	
	public List<Notification> getNotification() {
		return this.notifications;
	}

}
