package entity;
import java.sql.Timestamp;

/**
 * 회원 정보 저장 클래스
 * 
 * @author zeonghun
 * @since 2023.03.28
 */
public class Member {
    private String id;
    private String password;
    private String name;
    private Timestamp joinDate;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getJoinDate() {
        return this.joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

}
