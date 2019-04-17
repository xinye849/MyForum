package xin.yuan.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); // �޸ı��� �����������

		response.setContentType("application/json; charset=UTF-8");
		 
		// ���ҳ��session������ �ﵽע����Ч��
		request.getSession().invalidate();
		
		request.getServletContext().getRequestDispatcher("/index.jsp")
		.forward(request, response);
	}
}
