package xin.yuan.servlet;

import xin.yuan.service.NoticeServise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class NoticesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6420497939349581133L;

	public NoticeServise notice = new NoticeServise();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");	// �޸ı���  �����������
		
		List<Map<String, Object>> notices = new ArrayList<Map<String, Object>>();
		String str = request.getParameter("noticeID");
		
		System.out.println("����1");
		if(str == null || str.equals("")) {
			// ��ʾȫ����
			notices = notice.allNotice();
			request.setAttribute("notices", notices);
			
			request.getServletContext().getRequestDispatcher("/noticesList.jsp")
			.forward(request, response);
		}else {
			// ��ʾ������
			int noticeID = Integer.parseInt(str);
			notices = notice.getPost(noticeID);
			
			request.setAttribute("notice", notices);
			request.getServletContext().getRequestDispatcher("/noticeDetail.jsp")
			.forward(request, response);
		}
	}
}
