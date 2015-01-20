package goal.controller.servlet;

import goal.model.manager.ManageSearch;
import goal.model.manager.ManageUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		manageUser.authenticationCookie(request, response);
		String query = request.getParameter("search");
		if(query.length() > 0) {
			ManageSearch manageSearch = new ManageSearch();
			request.setAttribute("query", query);
			if(manageSearch.search(query)) {
				request.setAttribute("pageCurrent", request.getParameter("page"));
				request.setAttribute("pageMax", (int)Math.ceil(((float)manageSearch.getResults().size()) / 10));
				request.setAttribute("results", manageSearch.getResults());
			}
		}
		request.getRequestDispatcher("jsp/search.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		manageUser.authenticationCookie(request, response);
		String query = request.getParameter("search");
		if(query.length() > 0) {
			ManageSearch manageSearch = new ManageSearch();
			request.setAttribute("query", query);
			if(manageSearch.search(query)) {
				request.setAttribute("pageCurrent", "1");
				request.setAttribute("pageMax", (int)Math.ceil(((float)manageSearch.getResults().size()) / 10));
				request.setAttribute("results", manageSearch.getResults());
			}
		}
		request.getRequestDispatcher("jsp/search.jsp").forward(request, response);
	}

}
