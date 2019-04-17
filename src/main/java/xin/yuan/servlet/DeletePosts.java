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


public class DeletePosts extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PostServise servise = new PostServise();

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
		
		PrintWriter out = response.getWriter();
		Map<String, Object> map = new HashMap<String, Object>();
		
		String postID = request.getParameter("postID");
		
		int res = servise.deletePost(Integer.parseInt(postID));
		if(res == 1) {
			map.put("state", "success");
		}else {
			map.put("state", "false");
		}
			
		JSONObject jsonObject = JSONObject.fromObject(map);
		out.println(jsonObject.toString());
		System.out.println("deletePost:" + jsonObject.toString());
		out.flush();
		out.close();
	}
}
