package boomrabbit.logintest.mvc.domain;

/**
 * 사용자(level 1) - 로그인한 사람
 * 팀원(level 2) - 사용자 중에 특정 팀에 가입한 사람
 * 임원(level 3) - 특정 팀의 팀원으로서 팀원 강퇴, 초대권한이 있는 사람
 * 팀장(level 4) - 특정 팀의 팀원으로서 임원의 권한 및 팀삭제 권한을 가진사람
 */
public enum MemberStatus {
    USER, TEAM_MEMBER, MANAGER, LEADER
}
