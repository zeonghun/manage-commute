import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Scanner;

import entity.CommuteInfo;
import repository.Query;
import repository.UserRepository;

/**
 * 출퇴근 입력 및 조회
 * 
 * @author zeonghun
 * @since 2023.03.29
 */
public class Commute {

    /**
     * 출근입력, 퇴근입력, 출퇴근조회 선택
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public void commute(String id) {
        Scanner sc = new Scanner(System.in);
        int option = 0;

        while (option != 4) {
            try {
                printOption();
                option = sc.nextInt();

                switch (option) {
                    case 1:
                        workIn(id);
                        break;
                    case 2:
                        workOut(id);
                        break;
                    case 3:
                        readCommuteList(id);
                        break;
                    case 4:
                        System.out.println();
                        System.out.println("로그아웃합니다.");
                        System.out.println();
                        break;
                    default:
                        System.out.println();
                        System.out.println("1~4중에서 입력하세요.");
                }
            } catch (InputMismatchException ime) {
                System.out.println();
                System.err.println("숫자만 입력하세요.");
                sc.nextLine();
                option = 1;
            }
        }
    }

    /**
     * 옵션 출력
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public static void printOption() {
        System.out.println();
        System.out.println("1. 출근");
        System.out.println("2. 퇴근");
        System.out.println("3. 조회");
        System.out.println("4. 로그아웃");
        System.out.println();
        System.out.print("항목 선택: ");
    }

    /**
     * 출근 입력
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public static void workIn(String id) {
        CommuteInfo commute = new CommuteInfo();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        try {
            Class.forName(UserRepository.DB_DRIVE_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID,
                        UserRepository.DB_USER_PASSWORD);
                PreparedStatement stmt = con.prepareStatement(Query.ON_TIME_INSERT);) {
            // parameter 설정
            stmt.setInt(1, commute.getCno());
            stmt.setString(2, id);
            commute.setOnTime(currentTimestamp);
            stmt.setTimestamp(3, commute.getOnTime());
            stmt.executeUpdate();
            System.out.println();
            System.out.println("출근 완료했습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 퇴근 입력
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public static void workOut(String id) {
        CommuteInfo commute = new CommuteInfo();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        try {
            Class.forName(UserRepository.DB_DRIVE_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID,
                        UserRepository.DB_USER_PASSWORD);
                PreparedStatement stmt = con.prepareStatement(Query.OFF_TIME_INSERT);) {
            // parameter 설정
            stmt.setInt(1, commute.getCno());
            stmt.setString(2, id);
            commute.setOnTime(currentTimestamp);
            stmt.setTimestamp(3, commute.getOffTime());
            stmt.executeUpdate();
            System.out.println();
            System.out.println("퇴근 완료했습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 출퇴근 조회
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public static void readCommuteList(String id) {
        try {
            Class.forName(UserRepository.DB_DRIVE_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // if (id == "admin") {

        //     try (
        //             Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID,
        //                     UserRepository.DB_USER_PASSWORD);
        //             PreparedStatement stmt = con.prepareStatement(Query.COMMUTE_READ_ALL);) {
        //         // stmt.setString(1, id);
        //         ResultSet rs = stmt.executeQuery();
        //         System.out.println();
        //         System.out.println(
        //                 "========================================== Commute List ==========================================");
        //         while (rs.next()) {
        //             System.out.println("아이디: " + rs.getString("id") + " / 이름: " + rs.getString("name") + " / 출근: "
        //                     + rs.getString("on_time") + " / 퇴근: " + rs.getString("off_time"));
        //         }
        //         System.out.println(
        //                 "==================================================================================================");
        //     } catch (SQLException e) {
        //         e.printStackTrace();
        //     }

        // } else {

            try (
                    Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID,
                            UserRepository.DB_USER_PASSWORD);
                    PreparedStatement stmt = con.prepareStatement(Query.COMMUTE_READ);) {
                stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();
                System.out.println();
                System.out.println(
                        "========================================== Commute List ==========================================");
                while (rs.next()) {
                    System.out.println("아이디: " + rs.getString("id") + " / 이름: " + rs.getString("name") + " / 출근: "
                            + rs.getString("on_time") + " / 퇴근: " + rs.getString("off_time"));
                }
                System.out.println(
                        "==================================================================================================");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        // }
    }
}
