package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import entity.CommuteInfo;
import repository.UserRepository;

/**
 * 출퇴근 등록 및 조회
 * 
 * @author zeonghun
 * @since 2023.03.30
 */
public class Commute {

    private UserRepository userRepo;

    /**
     * 출근등록, 퇴근등록, 출퇴근조회 선택
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public void commute(String id, Scanner sc) {
        // Scanner sc = new Scanner(System.in);
        int option = 0;

        while (option != 4) {
            try {
                printOption();
                option = sc.nextInt();

                switch (option) {
                    // 출근 입력
                    case 1:
                        workIn(id);
                        break;
                    // 퇴근 입력
                    case 2:
                        workOut(id);
                        break;
                    // 출퇴근 조회
                    case 3:
                        readCommuteList(id);
                        break;
                    // 로그아웃
                    case 4:
                        System.out.println();
                        System.out.println("[ 로그아웃 완료 ]");
                        break;
                    default:
                        System.out.println();
                        System.out.println("[ 입력 범위 : 1 ~ 4 ]");
                }
                // 예외처리
            } catch (InputMismatchException ime) {
                System.out.println();
                System.err.println("[ 숫자만 입력 가능 ]");
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
    public void printOption() {
        System.out.println();
        System.out.println("1. 출근");
        System.out.println("2. 퇴근");
        System.out.println("3. 조회");
        System.out.println("4. 로그아웃");
        System.out.println();
        System.out.print("입력 >> ");
    }

    /**
     * 출근 등록
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.30
     */
    public void workIn(String id) {
        this.userRepo = new UserRepository();
        int rowCount = 0;

        // 같은 날짜에 출근이 있는지 조회
        ResultSet result = this.userRepo.todayOnTimeRead(id);

        // 행 개수 카운트
        try {
            result.last();
            rowCount = result.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 같은 날짜가 존재할 경우
        if (rowCount == 1) {
            System.out.println();
            System.err.println("[ 이미 출근 등록이 되어있습니다 ]");

        // 같은 날짜가 존재하지 않을 경우
        } else {

            // 출근 등록
            boolean workInResult = this.userRepo.workIn(id);

            if (workInResult) {
                System.out.printf(" / %s ]\n", id);
            } else {
                System.err.println("[ 출근 등록 실패 ]");
            }
        }
    }

    /**
     * 퇴근 등록
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.30
     */
    public void workOut(String id) {
        this.userRepo = new UserRepository();
        int onTimeCount = 0;
        int offTimeCount = 0;

        // 같은 날짜에 출근이 있는지 조회
        ResultSet onTimeResult = this.userRepo.todayOnTimeRead(id);

        // 행 개수 카운트
        try {
            onTimeResult.last();
            onTimeCount = onTimeResult.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 같은 날짜에 출근 등록이 되어있는 경우
        if (onTimeCount == 1) {
            ResultSet offTimeResult = this.userRepo.todayOffTimeRead(id);

            // 행 개수 카운트
            try {
                offTimeResult.last();
                offTimeCount = offTimeResult.getRow();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 같은 날짜에 퇴근 등록이 되어있는 경우
            if (offTimeCount == 1) {
                System.out.println();
                System.err.println("[ 이미 퇴근 등록이 되어있습니다 ]");
            } else {
                // 퇴근 등록
                boolean workOutResult = this.userRepo.workOut(id);

                if (workOutResult) {
                    System.out.printf(" / %s ]\n", id);
                } else {
                    System.err.println("[ 퇴근 등록 실패 ]");
                }
            }
        
        // 같은 날짜에 출근 등록이 없는 경우
        } else {
            System.out.println();
            System.err.println("[ 출근 등록이 되어있지 않습니다 ]");
        }
    }

    /**
     * 출퇴근 조회
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.30
     */
    public void readCommuteList(String id) {
        this.userRepo = new UserRepository();
        boolean isAdmin =  this.userRepo.isAdmin(id);
        ResultSet rs = userRepo.readCommuteList(isAdmin, id);

        // 관리자일 경우
        if (isAdmin){
            System.out.println();
            System.out.println("========================================== Commute List ==========================================");
            try {
                while (rs.next()) {
                    System.out.println("아이디: " + rs.getString("id") + " / 이름: " + rs.getString("name") + " / 출근: "
                            + rs.getString("on_time") + " / 퇴근: " + rs.getString("off_time"));
                }
                System.out.println("==================================================================================================");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // 일반 회원일 경우
        else {
            System.out.println();
            System.out.println("========================================== Commute List ==========================================");
            try {
                while (rs.next()) {
                    System.out.println("아이디: " + rs.getString("id") + " / 이름: " + rs.getString("name") + " / 출근: "
                            + rs.getString("on_time") + " / 퇴근: " + rs.getString("off_time"));
                }
                System.out.println("==================================================================================================");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
