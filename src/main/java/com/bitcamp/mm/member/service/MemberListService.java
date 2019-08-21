package com.bitcamp.mm.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitcamp.mm.member.dao.MemberSessionDao;
import com.bitcamp.mm.member.domain.ListViewData;
import com.bitcamp.mm.member.domain.MemberInfo;
import com.bitcamp.mm.member.domain.SearchParam;

@Service("listService")
public class MemberListService {

	/*
	 * 1. memberList: MemberInfo를 저장하는 리스트 2. totalCount: 전체 MemberInfo의 개수 3. no: 각
	 * MemberInfo가 가지고 있는 고유 값 4. currentPageNumber: 현재 페이지 번호 5. pageTotalCount: 전체
	 * 페이지 수
	 */

	// @Autowired private MemberJdbcTemplateDao dao;

	@Autowired
	private SqlSessionTemplate template;

	private MemberSessionDao dao;

	// 상수 처리(final)
	final int MEMBER_CNT_LIST = 3;

	public ListViewData getListData(int currentPageNumber, SearchParam searchParam) {

		dao = template.getMapper(MemberSessionDao.class);

		ListViewData listData = new ListViewData();

		// 현재 페이지 번호
		listData.setCurrentPageNumber(currentPageNumber);

		// 전체 게시물의 개수
		int totalCnt = dao.selectTotalCount(searchParam);

		int totalPageCnt = 0;

		// 전체 페이지 개수
		if (totalCnt > 0) {
			totalPageCnt = totalCnt / MEMBER_CNT_LIST;

			if (totalCnt % MEMBER_CNT_LIST > 0) {
				totalPageCnt++;
			}
		}

		listData.setPageTotalCount(totalPageCnt);

		// 1 -> 0 , 2 -> 3, 3 -> 6, 4 -> 9
		int index = (currentPageNumber - 1) * MEMBER_CNT_LIST;

		// 회원 정보 리스트
		List<MemberInfo> memberList = null;
		// 1. 검색 조건이 없는 경우 selectList → 전체 회원의 리스트
		// 2. id로 검색 : where like uid '%?%'
		// 3. name으로 검색 : where like uname '%?%'
		// 4. id 또는 name : where like uid '%?%' or uname '%?%'

		/*
		 * *****************************동적쿼리 적용 전 :
		 * switch*******************************
		 */
		/*
		 * if(searchParam!=null) { switch (searchParam.getStype()) { case "both":
		 * memberList = dao.selectListByBoth(conn, index, MEMBER_CNT_LIST, searchParam);
		 * break;
		 * 
		 * case "id": memberList = dao.selectListById(conn, index, MEMBER_CNT_LIST,
		 * searchParam); break;
		 * 
		 * case "name": memberList = dao.selectListByName(conn, index, MEMBER_CNT_LIST,
		 * searchParam);
		 * 
		 * break; } } else { memberList = dao.selectList(conn, index, MEMBER_CNT_LIST);
		 * }
		 */

		/*
		 * *****************************동적쿼리 적용 전 :
		 * if-else*******************************
		 */
		/*
		 * if(searchParam == null) { Map<String, Object> params = new HashMap<String,
		 * Object>(); params.put("index", index); params.put("MEMBER_CNT_LIST",
		 * MEMBER_CNT_LIST); params.put("keyword", searchParam.getKeyword());
		 * params.put("stype", searchParam.getStype());
		 * 
		 * memberList = dao.selectList(params);
		 * 
		 * } else if(searchParam.getStype().equals("both")) {
		 * 
		 * Map<String, Object> params = new HashMap<String, Object>();
		 * params.put("index", index); params.put("MEMBER_CNT_LIST", MEMBER_CNT_LIST);
		 * params.put("keyword", searchParam.getKeyword());
		 * 
		 * memberList = dao.selectListByBoth(params);
		 * 
		 * } else if(searchParam.getStype().equals("name")) {
		 * 
		 * Map<String, Object> params = new HashMap<String, Object>();
		 * params.put("index", index); params.put("MEMBER_CNT_LIST", MEMBER_CNT_LIST);
		 * params.put("keyword", searchParam.getKeyword());
		 * 
		 * memberList = dao.selectListByName(params);
		 * 
		 * } else if(searchParam.getStype().equals("id")) {
		 * 
		 * Map<String, Object> params = new HashMap<String, Object>();
		 * params.put("index", index); params.put("MEMBER_CNT_LIST", MEMBER_CNT_LIST);
		 * params.put("keyword", searchParam.getKeyword());
		 * 
		 * memberList = dao.selectListById(params); }
		 */

		// 동적쿼리 적용 후
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("index", index);
		params.put("MEMBER_CNT_LIST", MEMBER_CNT_LIST);
		params.put("searchParam", searchParam);

		memberList = dao.selectList(params);

		listData.setMemberList(memberList);

		// 확인 용 출력구문
		for (MemberInfo m : memberList) {
			System.out.println(m);
		}

		// 현재 페이지 받아오기 위함.
		// 1 -> 9-0 =9,8,7 / 2 -> 9-3=6,5,4
		// no를 통해 idx를 대신하는 번호를 구한다.
		int no = totalCnt - index;

		listData.setNo(no);

		listData.setTotalCount(totalCnt);

		return listData;

	}

	public List<MemberInfo> getAllList() {
		
		List<MemberInfo> list = null;
		
		dao = template.getMapper(MemberSessionDao.class);
		
		list = dao.selectAllList();

		return list;
	}
}
