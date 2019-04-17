package xin.yuan.service;

import xin.yuan.dao.BaseDao;
import xin.yuan.util.SysUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class PostServise {
	
	BaseDao dao = new BaseDao();
	
	public PostServise() {}
	
	List<Map<String, Object>> posts = new ArrayList<Map<String, Object>>();
	
	Map<String, Object> post = new HashMap<String,Object>();
	
	/**
	 * ��ȡָ��ҳ��������
	 */
	public List<Map<String, Object>> getAllPosts(int page) {
		String sql = "select * from post ORDER BY publishTime DESC limit ?,?";
		posts = dao.executeQuery(sql, new Object[]{(page-1)*10, 10});
		return posts;
	}
	
	/**
	 * ���е����Ӷ�Ӧ��ҳ��
	 */
	public int getAllPages() {
		String sql = "select count(*) AS postTotal from post";
		posts = dao.executeQuery(sql, new Object[0]);
		int totalPages  = 0;
		int num = Integer.parseInt(posts.get(0).get("postTotal") + "");
		if(num % 10 != 0) {
			totalPages += 1;
		}
		totalPages += (num/8);
		return totalPages;
	}
	
	/**
	 * ���巵�������������͵Ľӿ�
	 */
	public List<Map<String, Object>> allTypes() {
		String sql = "select * from posttype";
		posts = dao.executeQuery(sql, new Object[0]);
		return posts;
	}
	
	/**
	 * ����ָ�����͵����ӽӿ�
	 */
	public List<Map<String, Object>> posts(int type) {
		String sql = "select * from post where type = ?";
		posts = dao.executeQuery(sql, new Object[] {type});
		return posts;
	}
	
	/**
	 * ����ģ��������ȡ���ӵĽӿ�
	 */
	public List<Map<String, Object>> posts(String sql) {
		posts = dao.executeQuery(sql, new Object[0]);
		return posts;
	}
	
	/**
	 * �������µ����ӵĽӿ�
	 */
	public List<Map<String, Object>> newPosts() {
		String sql = "select * from post ORDER BY publishTime DESC limit 0,5";
		posts = dao.executeQuery(sql, new Object[0]);
		return posts;
	}
	
	/**
	 * �����������ӵĽӿ�
	 */
	public List<Map<String, Object>> hotPosts() {
		String sql = "select * from post ORDER BY pageview DESC limit 0,5";
		posts = dao.executeQuery(sql, new Object[0]);
		
		return posts;
	}
	
	/**
	 * ����ĳһ�����ӵ���Ϣ�Ľӿ�
	 */
	public List<Map<String,Object>> getPost(int postID) {
		String sql = "select post.postID, " +
							"post.title, " +
							"post.content, " +
							"post.publishTime, " +
							"post.pageview, " +
							"`user`.userID, " + 
							"`user`.account as account, " +
							"`user`.photo as userPhoto, " +
							"posttype.name as postTypeName " +
							"from post left join `user` on " +
							"post.operator = `user`.userID  " +
							"left JOIN posttype on " +
							"post.type = postType.postTypeID where post.postID=?";
		posts = dao.executeQuery(sql, new Object[]{postID});
		
		//	��Ϊ����ID��ѯ���Ľ��ֻ����һ��
		//post = types.get(0);
		return posts;
	}
	
	/**
	 * ��������1
	 */
	public int addPageView(int postID) {
		String sql = "update post set pageview = pageview + 1 where postID = ?";
		int rs = dao.executeUpdate(sql, new Object[]{postID});
		return rs;
	}
	
	/**
	 * �������ӵ�IDɾ������  �����������  ��������ɾ��
	 */
	public int deletePost(int postID) {
		String sql = "delete from post where postID = ?";
		int rs = dao.executeUpdate(sql, new Object[]{postID});
		return rs;
	}
	
	/**
	 * �û��������ӵķ���
	 */
	public int addPost(int userID, int postType, String title, String content) {
		//	��ȡ��ǰʱ��������ݿ�  ����ʱ����ַ���
		String time = SysUtil.getCurrentTime();
		String sql = "insert into post (title, content, publishTime, operator, type) values (?,?,?,?,?)";
		int rs = dao.executeUpdate(sql, new Object[]{title, content, time, userID, postType});
		
		//  ��ָ�������ӵ���������������һ
		sql = "update posttype set count = count + 1 where postTypeID = ?";
		rs = dao.executeUpdate(sql, new Object[]{postType});
		return rs;
	}
	
	/**
	 * ��ȡָ���û��������б�
	 * @return
	 */
	public List<Map<String,Object>> getPosts(int userID, int page) {
		String sql = "select * from post where operator = ? ORDER BY publishTime DESC limit ?,?";
		posts = dao.executeQuery(sql, new Object[]{userID, (page-1)*10, 10});
		return posts;
	}
	
	// ����ָ���û���������ҳ��
	public int getTotalPostPages(int userID) {
		String sql = "select count(*) AS postTotal from post where operator = ?";
		posts = dao.executeQuery(sql, new Object[]{userID});
		int totalPages  = 0;
		int num = Integer.parseInt(posts.get(0).get("postTotal") + "");
		if(num % 10 != 0) {
			totalPages += 1;
		}
		totalPages += (num/10);
		return totalPages;
	}
}