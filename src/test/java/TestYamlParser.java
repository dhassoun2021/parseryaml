import com.david.parser.Parser;
import com.david.parser.YamlParser;
import com.david.parser.bean.Info;
import org.junit.Test;
import java.io.File;


public class TestYamlParser {

    @Test
    public void test() throws Exception {
        Parser parser = new YamlParser();
        Info info = parser.read(new File("src/test/resources/file_simple.yaml"), Info.class);

    }

}
