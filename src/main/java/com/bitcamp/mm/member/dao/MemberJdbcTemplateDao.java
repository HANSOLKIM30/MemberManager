package com.bitcamp.mm.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bitcamp.mm.jdbc.JdbcUtil;
import com.bitcamp.mm.member.domain.MemberInfo;
import com.bitcamp.mm.member.domain.SearchParam;

@Repository("templateDao")
public class MemberJdbcTemplateDao { // repository 이름 등록 생략하면? → memberDao 이름으로 처리됨

	@Autowired
	JdbcTemplate template;
	
	public MemberInfo selectMemberById(String userId) {
		
		String sql = "select * from member where uid=?";
		
		//query → return 배열 / queryForObject → return 객체
		//query 사용
		// 중간에 parameter가 들어가는 경우 RowMapper와의 구분이 어려우므로, new object[]{} 객체로 받는다. 
		//아니면 아예 마지막에 parameter 기입 template.query(sql, rse, userId)	
		List<MemberInfo> list = template.query(sql, new Object[] {userId}, new MemberInfoRowMapper());
		
		
		return list.isEmpty()?null:list.get(0);
	}
	
	public MemberInfo selectMemberById2(String userId) {
		
		String sql = "select * from member where uid=?";
		
		MemberInfo memberInfo = null;
		
		try {
		memberInfo = template.query(sql, new Object[] {userId}, new MemberInfoRowMapper()).get(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return memberInfo;
	}
	
	public MemberInfo selectMemberById(Connection conn, String userId) {

		MemberInfo memberInfo = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from member where uid=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				memberInfo = new MemberInfo(rs.getInt("idx"), 
						rs.getString("uid"), rs.getString("upw"),
						rs.getString("uname"), 
						rs.getString("uphoto"), 
						new Date(rs.getTimestamp("regdate").getTime()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}

		return memberInfo;
	}

	public int insertMember(MemberInfo memberInfo) {
		
		String sql = "insert into member" + " (uid, uname, upw, uphoto) " + " values (?,?,?,?) ";
		
		return template.update(sql, memberInfo.getuId(), memberInfo.getuName(), memberInfo.getuPW(), memberInfo.getuPhoto());
	}
	
	public int insertMember(Connection conn, MemberInfo memberInfo) {

		PreparedStatement pstmt = null;

		int rCnt = 0;

		String sql = "insert into member (uid, uname, upw, uphoto) values (?,?,?,?) ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberInfo.getuId());
			pstmt.setString(2, memberInfo.getuName());
			pstmt.setString(3, memberInfo.getuPW());
			pstmt.setString(4, memberInfo.getuPhoto());
			rCnt = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rCnt;

	}

	public int selectTotalCount(SearchParam searchParam) {
		
		String sql = "select count(*) from member";
		
		if (searchParam != null) {
			sql = "select count(*) from member where ";

			if (searchParam.getStype().equals("both")) {
				sql += "uid like '% " + searchParam.getKeyword() + "%' or uname like '%" + searchParam.getKeyword()
						+ "%'";
			}
			if (searchParam.getStype().equals("id")) {
				sql += "uid like '%" + searchParam.getKeyword() + "%' ";
			}
			if (searchParam.getStype().equals("name")) {
				sql += "uname like '%" + searchParam.getKeyword() + "%' ";
			}
		}
		
		return template.queryForObject(sql, Integer.class);
	}
	
	public int selectTotalCount(Connection conn, SearchParam searchParam) {

		int totalCnt = 0;

		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select count(*) from member";

		if (searchParam != null) {
			sql = "select count(*) from member where ";

			if (searchParam.getStype().equals("both")) {
				sql += "uid like '% " + searchParam.getKeyword() + "%' or uname like '%" + searchParam.getKeyword()
						+ "%'";
			}
			if (searchParam.getStype().equals("id")) {
				sql += "uid like '%" + searchParam.getKeyword() + "%' ";
			}
			if (searchParam.getStype().equals("name")) {
				sql += "uname like '%" + searchParam.getKeyword() + "%' ";
			}
		}

		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				totalCnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return totalCnt;
	}
	
	public List<MemberInfo> selectList(int index, int count) {
		
		String sql = "SELECT * FROM member limit ?, ?";
		
		return template.query(sql, new MemberInfoRowMapper(), index, count);		
	}
	
	public List<MemberInfo> selectList(Connection conn, int index, int count) {

		List<MemberInfo> memberList = new ArrayList<MemberInfo>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// limit X, Y → X번부터 Y개까지 행 찾기
		String sql = "SELECT * FROM member limit ?, ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			pstmt.setInt(2, count);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				memberList.add(new MemberInfo(rs.getInt("idx"), 
						rs.getString("uid"), 
						rs.getString("upw"),
						rs.getString("uname"), 
						rs.getString("uphoto"), 
						new Date(rs.getDate("regdate").getTime())));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return memberList;
	}

	public List<MemberInfo> selectListById(int index, int count, SearchParam searchParam) {
		
		String sql = "select * from member where uid like ? limit ?, ?";
		
		return template.query(sql, new MemberInfoRowMapper(), "%" + searchParam.getKeyword() + "%", index, count);
	}
	
	public List<MemberInfo> selectListById(Connection conn, int index, int count, SearchParam searchParam) {

		List<MemberInfo> memberList = new ArrayList<MemberInfo>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from member where uid like ? limit ?, ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + searchParam.getKeyword() + "%");
			pstmt.setInt(2, index);
			pstmt.setInt(3, count);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberList.add(new MemberInfo(
								rs.getInt("idx"), 
								rs.getString("uid"), 
								rs.getString("upw"),
								rs.getString("uname"), 
								rs.getString("uphoto"), 
								new Date(rs.getDate("regdate").getTime())));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return memberList;
	}

	public List<MemberInfo> selectListByName(int index, int count, SearchParam searchParam) {
		
		String sql = "select * from member where uname like ? limit ?, ?";
		
		return template.query(sql, new MemberInfoRowMapper(), "%" + searchParam.getKeyword() + "%", index, count);
		
	}
	
	public List<MemberInfo> selectListByName(Connection conn, int index, int count, SearchParam searchParam) {

		List<MemberInfo> memberList = new ArrayList<MemberInfo>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from member where uname like ? limit ?, ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + searchParam.getKeyword() + "%");
			pstmt.setInt(2, index);
			pstmt.setInt(3, count);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberList.add(new MemberInfo(rs.getInt("idx"), rs.getString("uid"), rs.getString("upw"),
						rs.getString("uname"), rs.getString("uphoto"), new Date(rs.getDate("regdate").getTime())));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return memberList;
	}
	
	public List<MemberInfo> selectListByBoth(int index, int count, SearchParam searchParam){
		
		String sql = "select * from member where uid like ?  or uname like ? limit ?, ?";
		
		return template.query(sql, new MemberInfoRowMapper(), "%"+searchParam.getKeyword()+"%", "%"+searchParam.getKeyword()+"%", index, count);
	}
	
	public List<MemberInfo> selectListByBoth(Connection conn, int index, int count, SearchParam searchParam){
			
			List<MemberInfo> memberList = new ArrayList<MemberInfo>();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = "select * from member where uid like ?  or uname like ? limit ?, ?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+searchParam.getKeyword()+"%");
				pstmt.setString(2, "%"+searchParam.getKeyword()+"%");
				pstmt.setInt(3, index);
				pstmt.setInt(4, count);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					memberList.add(new MemberInfo(
									rs.getInt("idx"),
									rs.getString("uid"),
									rs.getString("upw"),
									rs.getString("uname"),
									rs.getString("uphoto"),
									new Date(rs.getDate("regdate").getTime())
									));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return memberList;
		}

	public MemberInfo selectMemberByIdx(int idx) {
		
		String sql = "select * from member where idx=?";
		
		MemberInfo memberInfo = null;

		//idx의 값이 없을 때 반환하지 못하는 것이 문제. → 예외처리
		try {
			memberInfo =  template.queryForObject(sql, new MemberInfoRowMapper(), idx);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return memberInfo;		
	}
	
	public MemberInfo selectMemberByIdx(Connection conn, int idx) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		MemberInfo memberInfo = null;
		String sql = "select * from member where idx=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			if(rs!=null && rs.next()) {
				memberInfo = new MemberInfo(
											rs.getInt("idx"),
											rs.getString("uid"),
											rs.getString("upw"),
											rs.getString("uname"),
											rs.getString("uphoto"),
											new Date(rs.getTimestamp("regdate").getTime()));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return memberInfo;				
	}
	
	public int updateMember(MemberInfo memberInfo) {
		
		String sql = "update member set uname=?, upw=?, uphoto=? where uid=?";
		
		return template.update(sql, memberInfo.getuName(), memberInfo.getuPW(), memberInfo.getuPhoto(), memberInfo.getuId());
	}
	
	public int updateMember(Connection conn, MemberInfo memberInfo) {

		PreparedStatement pstmt = null;

		int rCnt = 0;

		String sql = "update member set uname=?, upw=?, uphoto=? where uid=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberInfo.getuName());
			pstmt.setString(2, memberInfo.getuPW());
			pstmt.setString(3, memberInfo.getuPhoto());
			pstmt.setString(4, memberInfo.getuId());
			
			rCnt = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rCnt;

	}
	
	public int deleteMember(String uId) {
		
		String sql = "DELETE FROM member WHERE uid = ?";
		
		return template.update(sql, uId);
	}
	
	public int deleteMember(Connection conn, String uId) throws SQLException {

		PreparedStatement pstmt = null;
		
		int rCnt = 0;
		
		String sql = "DELETE FROM member WHERE uid = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uId);
			
			rCnt = pstmt.executeUpdate();	
			
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		return rCnt;
	}
		
}
