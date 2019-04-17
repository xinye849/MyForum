package xin.yuan.servlet;

import xin.yuan.service.PostServise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class PostList extends HttpServlet {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -6420497939349581133L;
	
	public PostServise servise = new PostServise();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");	// �޸ı���  �����������
		
		List<Map<String, Object>> posts = new ArrayList<Map<String, Object>>();
		
		String type = request.getParameter("themeID");		//  ĳ������������
		
		String querytitle = request.getParameter("querytitle");
		
		if(querytitle == null || querytitle.equals("")) {
			querytitle = "û����";	// ��ֹ��������" " ��ʾȫ������
		}
		
		String actionType = request.getParameter("actionType");
		
		if(actionType.equals("themeQuery")) {	//  ĳ������������
			Object types = (Integer) request.getSession().getAttribute("POID");
			String condition = "";
			if(types == null || types.equals("")) {
				condition = "where title like '%" + querytitle + "%'";
			}else {
				condition = "where title like '%" + querytitle + "%' and type = " + Integer.parseInt(types + "");
			}
			String sql = "select * from post " + condition;
			posts = servise.posts(sql);
		}else if(actionType.equals("indexQuery")) {			//  ȫ������
			String condition = "where title like '%" + querytitle + "%'";
			String sql = "select * from post " + condition;
			posts = servise.posts(sql);
		}else if(actionType.equals("themeIdQuery")){		//  ���������������������
			int typeId = Integer.parseInt(type);
			posts = servise.posts(typeId);
			request.getSession().setAttribute("POID", typeId);
		}
		
		request.setAttribute("posts", posts);
		request.getServletContext().getRequestDispatcher("/postList.jsp")
		.forward(request, response);
	}
}
