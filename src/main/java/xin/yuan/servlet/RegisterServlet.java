package xin.yuan.servlet;

import xin.yuan.service.UserServise;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class RegisterServlet extends HttpServlet {
	UserServise servise = new UserServise();
	/**
	 * 
	 */
	private static final long serialVersionUID = -3728932580440718805L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");	// �޸ı���  �����������
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//  ��ȡע��ҳ���ע����Ϣ
		String name = request.getParameter("regUserName");
		String account = request.getParameter("regAccount");
		String password = request.getParameter("regPassword");
		
		int state = servise.register(name, account, password);
		if(state == 1) {
			out.println("{\"result\":\"ע��ɹ�\"}");
			System.out.println("ע��ɹ�");
		} else {
			out.println("{\"result\":\"ע��ʧ��\"}");
			System.out.println("ע��ʧ��");
		}
		out.flush();
		out.close();
	}
}
