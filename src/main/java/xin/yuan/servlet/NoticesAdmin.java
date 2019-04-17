package xin.yuan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import xin.yuan.service.NoticeServise;


public class NoticesAdmin extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoticeServise servise = new NoticeServise();
	
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
		
		List<Map<String, Object>> notices = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		String dealType = request.getParameter("dealType");
		
		System.out.println("dealType:" + dealType);
		if(dealType != null) {	// ɾ������
			if(dealType.equals("delete")) {
				System.out.println("ɾ�����棺");
				String noticeID = request.getParameter("noticeID");
				int result = servise.deleteNoticeById(Integer.parseInt(noticeID));
				if(result == 1) {
					map.put("state", "success");
				}else {
					map.put("state", "false");
				}
			}else if(dealType.equals("add")) {
				String noticeTitle = request.getParameter("noticeTitle");
				String noticeContent = request.getParameter("noticeContent");
				int res = servise.addNotice(noticeTitle, noticeContent);
				if(res == 1) {
					map.put("state", "success"); 
				}else {
					map.put("state", "fail");
				}
			}
		}else {
			String pageNum = request.getParameter("pageNum");
			
			notices = servise.allNotice(Integer.parseInt(pageNum));
			
			int totalPages = servise.getAllNoticesPages();
			
			map.put("notices", notices);
			map.put("pages", totalPages);
			map.put("page", Integer.parseInt(pageNum));
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		out.println(jsonObject.toString());
		System.out.println("��̨����:" + jsonObject.toString());
		out.flush();
		out.close();
	}
}
