package query;

/**
 * (non-javadoc)
 * 
 * @author zeonghun
 * @since 2023.03.30
 */
public class Query {

    // 회원 등록 쿼리
    public static final String MEMBER_INSERT = "INSERT INTO member(id, password, name, join_date) VALUES(?, ?, ?, ?)";

    // 회원 조회 쿼리
    public static final String MEMBER_SELECT = "SELECT * FROM member WHERE id = ? AND password = ?";

    // 출근 등록 쿼리
    public static final String ON_TIME_INSERT = "INSERT INTO commute(commute_index, member_id, on_time) VALUES(?, ?, ?)";

    // 퇴근 등록 쿼리
    public static final String OFF_TIME_UPDATE = "UPDATE commute SET off_time = ? WHERE member_id = ? AND off_time IS NULL";

    // 오늘 날짜 출근 조회
    public static final String TODAY_ON_TIME_SELCET = "SELECT * FROM commute WHERE member_id = ? AND DATE(on_time) = DATE(NOW())";

    // 오늘 날짜 퇴근 조회
    public static final String TODAY_OFF_TIME_SELCET = "SELECT * FROM commute WHERE member_id = ? AND DATE(off_time) = DATE(NOW())";

    // 개인 출퇴근 조회 쿼리
    public static final String COMMUTE_READ = "SELECT member.id, member.name, commute.on_time, commute.off_time FROM member LEFT OUTER JOIN commute ON member.id = commute.member_id WHERE member.id = ?";

    // 전체 회원 출퇴근 조회 쿼리
    public static final String COMMUTE_READ_ALL = "SELECT member.id, member.name, commute.on_time, commute.off_time FROM member LEFT OUTER JOIN commute ON member.id = commute.member_id";
}
