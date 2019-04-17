package xin.yuan.service;

import xin.yuan.dao.BaseDao;
import xin.yuan.util.SysUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class NoticeServise {
	
	BaseDao dao = new BaseDao();
	
	List<Map<String, Object>> notices = new ArrayList<Map<String, Object>>();
	
	public NoticeServise() {
		
	}
	
	/**
	 * �������¹���ķ���
	 */
	public List<Map<String, Object>> newNotice() {
		
		String sql = "select * from notice ORDER BY publishTime DESC limit 0,5";
		notices = dao.executeQuery(sql, new Object[0]);
		
		return notices; 
	}
	
	/**
	 * ����ָ��ҳ���Ĺ���ķ���
	 */
	public List<Map<String,Object>> allNotice(int page){
		String sql = "select * from notice ORDER BY publishTime DESC limit ?,?";
		notices = dao.executeQuery(sql, new Object[]{(page-1)*5, 5});
		return notices;
	}
	
	/**
	 * ����ȫ������ķ���
	 */
	public List<Map<String,Object>> allNotice(){
		String sql = "select * from notice ORDER BY publishTime DESC";
		notices = dao.executeQuery(sql, new Object[0]);
		return notices;
	}

	/**
	 * �������й����ҳ��
	 * @return
	 */
	public int getAllNoticesPages() {
		String sql = "select count(*) AS noticesTotal from notice";
		notices = dao.executeQuery(sql, new Object[0]);
		int totalPages  = 0;
		int num = Integer.parseInt(notices.get(0).get("noticesTotal") + "");
		if(num % 5 != 0) {
			totalPages += 1;
		}
		totalPages += (num/5);
		return totalPages;
	}
	
	/**
	 * ����ĳһ���������Ϣ�Ľӿ�
	 */
	public List<Map<String,Object>> getPost(int noticeID) {
		String sql = "select notice.id, " +
							"notice.title, " +
							"notice.content, " +
							"notice.publishTime from notice where notice.id=?";
		notices = dao.executeQuery(sql, new Object[]{noticeID});
		return notices;
	}
	
	/**
	 * ����idɾ������
	 */
	public int deleteNoticeById(int noticeID) {
		String sql = "delete from notice where id = ?";
		int res = dao.executeUpdate(sql, new Object[]{noticeID});
		return res;
	}
	
	/**
	 * ��ӹ���
	 */
	public int addNotice(String title, String content) {
		String sql = "insert into notice (title, content, publishTime) values (?,?,?)";
		String time = SysUtil.getCurrentTime();
		int rs = dao.executeUpdate(sql, new Object[]{title, content, time});
		return rs;
	}
}
