package com.cafe24.guestbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cafe24.guestbook.vo.GuestBookVo;
import com.cafe24.mvc.util.AutoClose;
import com.cafe24.mvc.util.ConnectionFactroy;

public class GuestBookDao {

	public List<GuestBookVo> getList() {
		List<GuestBookVo> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();

		try {
			conn = ConnectionFactroy.getInstance().createConnection();

			sql.append("select no,name,password,content, date_format(reg_date,'%Y-%m-%d') as reg_date ");
			sql.append("from guestbook ");
			sql.append("order by no desc ");
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				GuestBookVo vo = new GuestBookVo();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setPassword(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setDateTime(rs.getString(5));

				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			AutoClose.closeResource(rs, pstmt, conn);
		}

		return list;
	}

	public boolean insert(GuestBookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();

		try {
			conn = ConnectionFactroy.getInstance().createConnection();
			sql.append("insert into guestbook ");
			sql.append("values(null,?,?,?,now()) ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());

			int count = pstmt.executeUpdate();

			result = (count == 1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			AutoClose.closeResource(pstmt, conn);
		}

		return result;
	}

	public boolean delete(int no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();

		try {
			conn = ConnectionFactroy.getInstance().createConnection();
			sql.append("delete from guestbook ");
			sql.append("where no = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, no);

			int count = pstmt.executeUpdate();

			result = (count == 1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			AutoClose.closeResource(pstmt, conn);
		}

		return result;

	}

	public GuestBookVo getGusetBook(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		GuestBookVo vo = new GuestBookVo();
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();

		try {
			conn = ConnectionFactroy.getInstance().createConnection();

			sql.append("select no,name,password,content, date_format(reg_date,'%Y-%m-%d') as reg_date ");
			sql.append("from guestbook ");
			sql.append("where no = ? ");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setPassword(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setDateTime(rs.getString(5));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			AutoClose.closeResource(rs, pstmt, conn);
		}

		return vo;
	}

}
