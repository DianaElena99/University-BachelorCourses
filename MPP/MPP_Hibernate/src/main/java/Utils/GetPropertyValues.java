package Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetPropertyValues {
    public Properties getPropValues() throws IOException {
        String res = "";
        Properties prop = new Properties();
        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("config.properties");
        if (is!=null){
            prop.load(is);
        }else{
            throw new FileNotFoundException("Somethin went wrong :/ ");
        }
        return prop;
    }
}
