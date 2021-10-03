import com.david.parser.NodeRoot;
import com.david.parser.Parser;
import com.david.parser.YamlParser;
import com.david.parser.bean.Info;
import org.junit.Test;
import java.io.File;


public class TestYamlParser {

    @Test
    public void test() throws Exception {
        YamlParser parser = new YamlParser();
        NodeRoot nodeRoot = parser.readFile(new File("src/test/resources/file_simple.yaml"));
    }

}
