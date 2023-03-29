import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256 암호화 클래스
 * 
 * @author zeonghun
 * @since 2023.03.29
 */
public class SHA256 {

    /**
     * SHA256으로 해싱하는 메소드
     * 
     * @param password 암호화할 패스워드
     * 
     * @throws NoSuchAlgorithmException
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public static String encrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        return bytesToHex(md.digest());
    }

    /**
     * 바이트를 헥사값으로 변환하는 메소드
     * 
     * @param bytes 변환할 바이트
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
