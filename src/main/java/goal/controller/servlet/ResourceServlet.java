package goal.controller.servlet;

import goal.model.dao.FileDAO;
import goal.model.manager.ManageUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/resource")
public class ResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		manageUser.authenticationCookie(request, response);
		String type = request.getParameter("type");
		switch(type) {
			case "text":
				FileDAO fileDAO = new FileDAO();
				request.setAttribute("file", fileDAO.getFile(request.getParameter("name")));
				request.getRequestDispatcher("jsp/file.jsp").forward(request, response);
			break;
			case "web":
				request.setAttribute("title", request.getParameter("name"));
				request.setAttribute("target", request.getParameter("url"));
				request.getRequestDispatcher("jsp/web.jsp").forward(request, response);
			break;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
