package xin.yuan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHander {
	
	/**
	 * �Զ���Ĵ���ִ�� SQL ��䷵�ؽ�����Ľӿ�
	 */
	public Object doHander(ResultSet rs) throws SQLException;
}
