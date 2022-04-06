import com.Scheduled.Scheduled_server.model.GameLibrary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        System.out.println(passwordEncoder.encode("user"));
        System.out.println(passwordEncoder.encode("admin"));
        GameLibrary gameLibrary = new GameLibrary();

    }
}
