package goal.controller.servlet;

import goal.model.bean.User;
import goal.model.manager.ManageUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if(session.getAttribute("guest") != null && session.getAttribute("guest") instanceof User) {
			response.sendRedirect(request.getContextPath() + "/home");
		}
		else {
			request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		try {
			manageUser.authentication(request, response);
			response.sendRedirect(request.getContextPath() + "/home");
		}
		catch(Exception e) {
			request.setAttribute("notifications", manageUser.getNotification());
			request.getRequestDispatcher("jsp/login.jsp").forward(request, response);	
		}
	}

}
