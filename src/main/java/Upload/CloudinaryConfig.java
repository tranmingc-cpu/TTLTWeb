package Upload;
import com.cloudinary.Cloudinary;
import java.util.HashMap;
import java.util.Map;
public class CloudinaryConfig {
    public static Cloudinary cloudinary;

    static {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dsisasunp");
        config.put("api_key", "355795983164731");
        config.put("api_secret", "EF3fOQQwy3sOfmdceVadqjHGycg");

        cloudinary = new Cloudinary(config);
    }
    public static Cloudinary getInstance() {
        return cloudinary;
    }

}
