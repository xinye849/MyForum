package xin.yuan.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BaseDao {
	// ����URL
	private static final String url = "jdbc:mysql://localhost:3306/forum?Unicode=true&characterEncoding=UTF-8";

	// �û���
	private static final String username = "root";

	// ����
	private static final String password = "xin123456";

	// ���ݿ�����
	private static final String jdbcDriver = "com.mysql.jdbc.Driver";

	// ���� ParameterMetaData.getParameterType(i + 1) �Ƿ���׳��쳣
	protected boolean pmdKnownBroken = false;

	public BaseDao() {

	}

	public BaseDao(boolean pmdKnownBroken) {
		this.pmdKnownBroken = pmdKnownBroken;
	}

	public Connection getConnetion() {

		Connection conn = null;
		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/** ���ݸ����Ĳ��� ִ��Sql ��ѯ��䣬�ѽ�����Ϸ���һ�� List<Map<String,Object>> ���� */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> executeQuery(String sql, Object[] params) {
		return (List<Map<String, Object>>) this.excuteQuery(sql, params,
				new ListMapHander());
	}

	/** ��崫������sql���, ��������Ҫ�Ľӿڵķ�����������,��������Ҫ�Ľ�����ĸ�ʽ */
	
	public Object excuteQuery(String sql, Object[] params, ResultSetHander rsh) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = this.getConnetion();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			stmt = con.prepareStatement(sql);

			System.out.println("SQL:" + sql + "; Parameters:"
					+ Arrays.deepToString(params));

			// ���Statement�Ĳ���
			fillStatement(stmt, params);
			// ִ�в�ѯ
			rs = stmt.executeQuery();
			Object obj = rsh.doHander(rs);
			return obj;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// �ر����ݿ�����
			close(con, stmt, rs);
		}
		return resultList;
	}

	/**
	 * ���²���  ����ֵ1���ɹ�
	 */
	public int executeUpdate(String sql, Object[] params) {
		PreparedStatement stmt = null;
		Connection con = this.getConnetion();

		int rs = 0;
		try {
			con.setAutoCommit(false);
			// ����PreparedStatement����
			stmt = con.prepareStatement(sql);
			// ���Statement�Ĳ���
			fillStatement(stmt, params);
			System.out.println("SQL:" + sql + "; Parameters:"
					+ Arrays.deepToString(params));
			// ִ�в�ѯ
			rs = stmt.executeUpdate();
			// �ύ����
			con.commit();
			// ����������Ϊԭ����״̬
			con.setAutoCommit(true);
		} catch (SQLException e) {
			// �ڲ����쳣��ʱ������ع�
			try {
				con.rollback();
				if (!con.getAutoCommit()) {
					con.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				e.printStackTrace();
				System.out.println("update database error");
			}
			System.out.println("update database error");
		} finally {
			// �ر����ݿ�����
			close(con, stmt, null);
		}
		return rs;
	}

	/**
	 * @Title: fillStatement
	 * @Description: ���SQL����
	 * @param stmt
	 * @param params
	 * @throws SQLException
	 * @return void
	 */
	private void fillStatement(PreparedStatement stmt, Object[] params)
			throws SQLException {

		/**
		 * �������ĸ����Ƿ�Ϸ��������е����ݿ�������֧�� stmt.getParameterMetaData()���������
		 * ���������һ��һ��pmdKnownBroken ��������ʶ��ǰ���������Ƿ�֧�ָ÷����ĵ��á�
		 */
		ParameterMetaData pmd = null;
		if (!pmdKnownBroken) {
			pmd = stmt.getParameterMetaData();
			int stmtCount = pmd.getParameterCount();
			int paramsCount = params == null ? 0 : params.length;

			if (stmtCount != paramsCount) {
				System.out.println("stmtCount:" + stmtCount + ",paramsCount:"
						+ paramsCount);
				throw new SQLException("Wrong number of parameters: expected "
						+ stmtCount + ", was given " + paramsCount);
			}
		}

		// ��� ���� Ϊ null ֱ�ӷ���
		if (params == null) {
			return;
		}

		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				stmt.setObject(i + 1, params[i]);
			} else {
				int sqlType = Types.VARCHAR;
				if (!pmdKnownBroken) {
					try {
						sqlType = pmd.getParameterType(i + 1);
					} catch (SQLException e) {
						pmdKnownBroken = true;
					}
				}
				stmt.setNull(i + 1, sqlType);
			}
		}
	}

	/**
	 * �ر����ݿ�����
	 */
	private void close(Connection con, Statement stmt, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						if (con != null) {
							try {
								con.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}