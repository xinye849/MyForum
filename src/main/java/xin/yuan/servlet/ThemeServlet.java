package xin.yuan.servlet;

import xin.yuan.service.NoticeServise;
import xin.yuan.service.PostServise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ThemeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6706113100924995296L;

	public PostServise post = new PostServise();
	
	public NoticeServise notice = new NoticeServise();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	// �޸ı���  �����������
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = post.allTypes();
		request.setAttribute("types", list);
		
		//  ������
		List<Map<String, Object>> notices = new ArrayList<Map<String, Object>>();
		notices = notice.newNotice();
		request.setAttribute("noticeList", notices);
		
		//  ��������
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		newList = post.newPosts();
		request.setAttribute("newList", newList);
		
		//  �������
		List<Map<String, Object>> hotList = new ArrayList<Map<String, Object>>() ;
		hotList = post.hotPosts();
		request.setAttribute("hotList", hotList);
		
		request.getServletContext().getRequestDispatcher("/themes.jsp")
				.forward(request, response);
	}
}
