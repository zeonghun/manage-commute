package entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * 출퇴근 정보 저장 클래스
 * 
 * @author zeonghun
 * @since 2023.03.29
 */
public class CommuteInfo {
    private int commuteIndex;
    private Timestamp onTime;
    private Timestamp offTime;

    public int getCommuteIndex() {
        return this.commuteIndex;
    }

    public void setCommuteIndex(int commuteIndex) {
        this.commuteIndex = commuteIndex;
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

    public Date getOnTime(LocalDate now) {
        return null;
    }
}
