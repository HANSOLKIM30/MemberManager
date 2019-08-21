<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	var chk = ${rChk};
	if(chk>0){
		alert('탈퇴되었습니다. 감사합니다.');
		location.href='/mm/member/logout';
	} else {
		alert('탈퇴 처리 중 오류가 발생하였습니다. 관리자에 연락바랍니다.');
		location.href='/mm/main';
	}
</script>