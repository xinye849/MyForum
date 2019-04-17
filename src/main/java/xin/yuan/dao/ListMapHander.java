package xin.yuan.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ListMapHander implements ResultSetHander {

	/**
	 * ʵ���Զ���ķ���ָ����ʽ�����������
	 */
	@Override
	public Object doHander(ResultSet rs) throws SQLException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(); 
		ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();
        
        HashMap<String,Object> m = null;
        
        while(rs.next()){
        	m = new HashMap<String,Object>();
        	System.out.println("====>");
        	for (int i = 1; i <= cols; i++) {
        		/* ��������Լ�ֵ�Եķ�ʽ����map */
				m.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
        	resultList.add(m);
        }
		return resultList;
	}
}
