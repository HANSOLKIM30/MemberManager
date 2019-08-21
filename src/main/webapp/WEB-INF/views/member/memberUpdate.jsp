<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	var chk = ${rCnt};
	if(chk>0){
		alert('수정되었습니다.');
		location.href='/mm/main';
	} else {
		alert('수정 되지 않았습니다. 관리자에 연락바랍니다.');
		location.href='/mm/main';
	}
</script>