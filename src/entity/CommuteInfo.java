package entity;

import java.sql.Timestamp;

/**
 * 출퇴근 정보 저장 클래스
 * 
 * @author zeonghun
 * @since 2023.03.28
 */
public class CommuteInfo {
    private int cno;
    private Timestamp onTime;
    private Timestamp offTime;

    public int getCno() {
        return this.cno;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public Timestamp getOnTime() {
        return this.onTime;
    }

    public void setOnTime(Timestamp onTime) {
        this.onTime = onTime;
    }

    public Timestamp getOffTime() {
        return this.offTime;
    }

    public void setOffTime(Timestamp offTime) {
        this.offTime = offTime;
    }
}
