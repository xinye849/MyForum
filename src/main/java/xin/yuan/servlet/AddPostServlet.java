package xin.yuan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import net.sf.json.JSONObject;
import xin.yuan.service.PostServise;

public class AddPostServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	PostServise servise = new PostServise();

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
		
		Map<String, Object> map = new HashMap<String, Object>();
		PrintWriter out = response.getWriter();
		
		String userID = request.getParameter("userID");
		String postTitle = request.getParameter("postTitle");
		String postContent = request.getParameter("postContent");
		String postType = request.getParameter("postType");
		
		System.out.println("AddPostServlet:" + userID + postContent + postTitle + postType);
		
		int result = servise.addPost(Integer.parseInt(userID), Integer.parseInt(postType), postTitle, postContent);
		//	�жϲ�����
		if(result == 1) {
			map.put("result", "success");
		}else {
			map.put("result", "false");
		}
		JSONObject jsonMap = JSONObject.fromObject(map);
		out.println(jsonMap.toString());
		out.flush();
		out.close();
	}
}
