const URL = {
    //COMMON
    ROOT: "/", // 루트경로

    LOGIN: "/login", //로그인
    ERROR: "/error", //에러

    HOME: "home", // 메인페이지

    //역할 기반 접미사 URL
    REG: "reg", //등록
    BNDE_REG: "bndeReg", //일괄등록
    DTL: "dtl", //상세
    MDFCN: "mdfcn", //수정

    //일정관리
    SCHDL: "schdl", // 일정
    SCHDL_VCATN: "vcatn", // 휴가
    SCHDL_BSRP: "bsrp", // 출장 등록

    //조직도
    ORGCHT: "orgcht", // 조직도

    //사용자정보
    MYPAGE: "mypage", // 회원 정보 수정
    MYPAGE_VCATN: "vcatn", // 휴가 조회
    MYPAGE_BSRP: "bsrp", // 출장 조회

    //관리자
    ADMIN: "admin", // 관리자

    //직원관리
    ADMIN_MBER_MANAGE: "mberManage", // 직원회원관리
    ADMIN_ORGCHT: "orgcht", // 조직도(조직도 & 사용자맵)
    ADMIN_DEPT: "dept", // 부서관리
    ADMIN_VCATN_CRT_MANAGE: "vcatnCrtManage", // 휴가 생성 관리

    //게시판 관리

    //사이트 설정관리
    ADMIN_BASS_MNG: "bassMng", //기본설정관리
    ADMIN_AUTHRT_MNG: "authrtMng", //권한관리
    ADMIN_CMMN_CD: "cmmnCd", //공통코드
    ADMIN_CMMN_DTL_CD: "cmmnDtlCd", //공통상세코드
    ADMIN_BANNER_MNG: "bannerMng", //배너관리 => 보류
    ADMIN_POPUP_MNG: "popupMng", //팝업관리 => 보류
    ADMIN_PROGRM_LL_MNG: "progrmLlMng", //프로그램리스트관리
    ADMIN_MENU_AUTHRT_MNG: "menuAuthrtMng", //메뉴권한관리
    ADMIN_MENU_USER_AUTHRT_MNG: "menuUserAuthrtMng", //메뉴사용자권한관리
    ADMIN_SKLL_USER_AUTHRT_MNG: "skllUserAuthrtMng", //기능사용자권한관리
};

export default URL;
