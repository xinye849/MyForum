package xin.yuan.service;

import xin.yuan.dao.BaseDao;
import xin.yuan.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class UserServise {

	BaseDao dao = new BaseDao();
	
	List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();

	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @return
	 */
	public List<Object> login(String account, String password) {
		String sql = "select * from user where account=? and password=?";
		List<Map<String, Object>> lm = dao.executeQuery(sql, new Object[] {
				account, password });

		List<Object> rl = new ArrayList<Object>();

		// ��¼�ɹ�
		if (lm.size() > 0) {
			System.out.println("lim.size > 0");
			Map<String, Object> map = lm.get(0);
			System.out.println("luoluo:" + map.toString());
			User user = new User();
			user.setAccount(map.get("account").toString());
			user.setPassword(map.get("password").toString());
			user.setName(map.get("name").toString());
			user.setEmail(map.get("email").toString());
			user.setGender(map.get("gender").toString());
			user.setType(Integer.parseInt(map.get("type").toString()));
			user.setUserID(Integer.parseInt(map.get("userID").toString()));
			user.setPhoto_address(map.get("photo").toString());
			rl.add(1);
			rl.add(user);
		}
		// ��¼���ɹ� ����������Ը�⣬1.�ʺŲ����� 2.�������
		else {
			sql = "select * from user where account=?";
			lm = dao.executeQuery(sql, new Object[] { account });
			if (lm.size() == 0) {// �û��������ڴ���
				System.out.println("this");
				rl.add(3);
			} else {// �������
				rl.add(2);
			}
			rl.add(null);
		}
		return rl;
	}

	/**
	 * ��ȡ���е��û���Ϣ����̨��
	 * 
	 * @return
	 */
	public List<Object> login(String account, String password, String actionType) {
		String sql = "select type from user where account=? and password=?";
		List<Map<String, Object>> lm = dao.executeQuery(sql, new Object[] {
				account, password });
		List<Object> rl = new ArrayList<Object>();

		// ��¼�ɹ�
		if (lm.size() > 0) {
			if(lm.get(0).get("type").equals(1)){
				rl.add(1);
			}
		}else {		// ��¼���ɹ� ����������ԭ��1.�ʺŲ����� 2.�������3.û��Ȩ��
			sql = "select * from user where account=?";
			lm = dao.executeQuery(sql, new Object[] { account });
			
			String sql2 = "select password from user where account=?";
			List<Map<String, Object>> lm2 = dao.executeQuery(sql2,
					new Object[] { account });
			
			if (lm.size() == 0) {// �û��������ڴ���
				rl.add(3);
			} else if(!lm2.get(0).get("password").equals(password)){// �������
				rl.add(2);
			}else {		// �û�û��Ȩ��
				rl.add(4);
			}
		}
		return rl;
	}

	/**
	 * �û�ע��ķ��� 1:ע��ɹ�
	 */

	public int register(String name, String account, String password) {

		String sql = "insert into user (account,name,password,type,photo,email,gender,state) values (?,?,?,?,?,?,?,?)";

		int result = dao.executeUpdate(sql, new Object[] { account, name,
				password, 2, "/MyForum/images/head/default.jpg", "none", "none", 0});

		return result;
	}
	
	/**
	 * ����ĳһ��ID���ظ��û�����Ϣ
	 */
	public List<Map<String, Object>> getUserById(int userID) {
		String sql = "select * from user where userID = ?";
		users = dao.executeQuery(sql,new Object[]{userID});
		return users;
	}
	
	/**
	 * �����û���Ϣ
	 */
	public int updateUser(int userID, String account, String name, String gender, String email) {
		String sql = "update user set account = ?, name = ?, gender = ?, email = ? where userID = ?";
		int res = dao.executeUpdate(sql, new Object[]{account, name, gender, email, userID});
		return res;
	}
	
	/**
	 * ��������
	 */
	public int updatePwd(int userID, String oldPwd, String newPwd) {
		String sql = "select password from user where userID = ?";
		users = dao.executeQuery(sql, new Object[]{userID});
		if(!users.get(0).get("password").equals(oldPwd)) {	// ԭ�����������
			return 2;
		}else {
			sql = "update user set password = ? where userID = ?";
			int res = dao.executeUpdate(sql, new Object[]{newPwd, userID});
			return res;
		}
	}
	
	/**
	 * ��ȡ���е��û���Ϣ
	 */
	public List<Map<String, Object>> getAllUser() {
		String sql = "select * from user";
		users = dao.executeQuery(sql, new Object[0]);
		return users;
	}
	
	/**
	 * ���������û���ҳ��
	 * @return
	 */
	public int getAllPages() {
		String sql = "select count(*) AS userTotal from user";
		users = dao.executeQuery(sql, new Object[0]);
		int totalPages  = 0;
		int num = Integer.parseInt(users.get(0).get("userTotal") + "");
		if(num % 10 != 0) {
			totalPages += 1;
		}
		totalPages += (num/10);
		return totalPages;
	}
	
	/**
	 * �����û�
	 */
	public int freezeUserById(int userID) {
		String sql = "update user set state = 1 where userID = ?";
		int res = dao.executeUpdate(sql, new Object[]{userID});
		return res;
	}
	
	/**
	 * �ⶳ�û�
	 */
	public int unfreezeUserById(int userID) {
		String sql = "update user set state = 0 where userID = ?";
		int res = dao.executeUpdate(sql, new Object[]{userID});
		return res;
	}
	
	/**
	 * ��ѯĳ���û��Ƿ񱻶���  1:������  0������
	 */
	public int checkState(int userID) {
		String sql = "select state from user where userID = ?";
		users = dao.executeQuery(sql, new Object[]{userID});
		int state = Integer.parseInt("" + users.get(0).get("state"));
		return state;
	}
	
	/**
	 * �����û��ϴ�ͷ��
	 */
	public int updatePhoto(int userID, String photoPath) {
		String sql = "update user set photo = ? where userID = ?";
		int res = dao.executeUpdate(sql, new Object[]{photoPath, userID});
		return res;
	}
}
