package repository;

/**
 * (non-javadoc)
 * 
 * @author zeonghun
 * @since 2023.03.28
 */
public class Query {

    // 회원 등록 쿼리
    public static final String MEMBER_INSERT = "INSERT INTO member(id, password, name, join_date) VALUES(?, ?, ?, ?)";

    // 회원 조회 쿼리
    public static final String MEMBER_SELECT = "SELECT * FROM member WHERE id = ? AND password = ?";
}
