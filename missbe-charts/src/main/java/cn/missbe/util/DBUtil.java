package cn.missbe.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 工具类，获取数据库的连接
 * @author Administrator
 *
 */
public final class DBUtil {

	private static String url = "jdbc:mysql://119.29.251.86:3306/missbe";
	private static String user = "root";
	private static String psw = "missbe@cn";
	private static Connection conn;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private DBUtil() {  
    }

	/**
	 * 获取数据库的连接
	 * 
	 * @return conn
	 */
	public static Connection getConnection() {
		if (null == conn) {
			try {
				conn = DriverManager.getConnection(url, user, psw);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return conn;
	}

	/**
	 * 释放资源
	 * 
	 * @param conn
	 * @param pstmt
	 * @param rs
	 */
	public static void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				if (null != pstmt) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} finally {
						if (null != conn) {
							try {
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
								throw new RuntimeException(e);
							}
						}
					}
				}
			}
		}
	}

}
