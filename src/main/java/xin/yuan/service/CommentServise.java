package xin.yuan.service;

import xin.yuan.dao.BaseDao;
import xin.yuan.util.SysUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class CommentServise {
	/**
	 * �������Ӻ��û�������
	 */
	
	BaseDao dao = new BaseDao();
	
	List<Map<String, Object>> comments = new ArrayList<Map<String, Object>>();
	
	// ����ָ�����ӵ�ĳҳ��������
	public List<Map<String, Object>> getCommentByPostID(int postID, int page) {
		String sql = "select `comment`.id, " +
				"`comment`.content, " +
				"`comment`.commentTime, " +
				"`comment`.operator as operatorID, " +
				"`comment`.post as postID, " +
				"`user`.account as account, " +
				"`user`.photo as userPhoto " + 
				"from `comment` LEFT JOIN post on " +
				"`comment`.post = post.postID " +
				"LEFT JOIN `user` on " +
				"`comment`.operator = `user`.userID " +
				"where `comment`.post = ? " +
				"ORDER BY commentTime ASC limit ?,?" ;
		comments = dao.executeQuery(sql, new Object[]{postID, (page-1)*5, 5});
		return comments;
	}
	
	// ����ָ���˺ŵ�ĳҳ��������
	public List<Map<String, Object>> getCommentByUserID(int userID, int page) {
		String sql = "select `comment`.id, " +
				"`comment`.content, " +
				"`comment`.commentTime, " +
				"`comment`.operator as operatorID, " +
				"`comment`.post as postID, " +
				"post.title as postTitle, " +
				"`user`.photo as userPhoto " + 
				"from `comment` LEFT JOIN post on " +
				"`comment`.post = post.postID " +
				"LEFT JOIN `user` on " +
				"`user`.userID = `comment`.operator " +
				"where `comment`.operator = ? " +
				"ORDER BY commentTime DESC limit ?,?" ;
		comments = dao.executeQuery(sql, new Object[]{userID, (page-1)*5, 5});
		return comments;
	}
	
/*	public static void main(String args[]) {
		comments = (new CommentServise()).getCommentByUserID(7, 1);
	}*/
	
	//	�������ӵ�ID���ظ����ӵ���������
	public List<Map<String, Object>> getCommentByPostID(int postID) {
		String sql = "select `comment`.id, " +
				"`comment`.content, " +
				"`comment`.commentTime, " +
				"`comment`.operator as operatorID, " +
				"`comment`.post as postID, " +
				"`user`.account as account, " +
				"`user`.photo as userPhoto " + 
				"from `comment` LEFT JOIN post on " +
				"`comment`.post = post.postID " +
				"LEFT JOIN `user` on " +
				"`comment`.operator = `user`.userID " +
				"where `comment`.post = ? " +
				"ORDER BY commentTime DESC" ;
		comments = dao.executeQuery(sql, new Object[]{postID});
		return comments;
	}
	
	// ����ָ�����ӵ�������ҳ��
	public int getTotalCommentPagesByPostId(int postID) {
		String sql = "select count(*) AS commentTotal from `comment` where post = ?";
		comments = dao.executeQuery(sql, new Object[]{postID});
		int totalPages  = 0;
		int num = Integer.parseInt(comments.get(0).get("commentTotal") + "");
		if(num % 5 != 0) {
			totalPages += 1;
		}
		totalPages += (num/5);
		return totalPages;
	}
	
		// ����ָ���˺ŵ�������ҳ��
		public int getTotalCommentPagesByUserId(int postID) {
			String sql = "select count(*) AS commentTotal from `comment` where operator = ?";
			comments = dao.executeQuery(sql, new Object[]{postID});
			int totalPages  = 0;
			int num = Integer.parseInt(comments.get(0).get("commentTotal") + "");
			if(num % 5 != 0) {
				totalPages += 1;
			}
			totalPages += (num/5);
			return totalPages;
		}
	
	// Ϊĳ�������������
	public int addCommentByPostID(int userID, int postID, String text) {
		//   ��ȡ��ǰʱ��������ݿ�  ����ʱ����ַ���
		String time = SysUtil.getCurrentTime();
		
		String sql = "insert into comment (content, commentTime, post, operator) values (?,?,?,?)";
		int rs = dao.executeUpdate(sql, new Object[]{text, time, postID, userID});
		return rs;
	}
	
	/**
	 * �������۵�IDɾ������
	 */
	public int deleteCommentByID(int commendID) {
		String sql = "delete from comment where id = ?";
		int rs = dao.executeUpdate(sql, new Object[]{commendID});
		return rs;
	}
}
