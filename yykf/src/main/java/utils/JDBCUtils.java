package utils;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JDBCUtils {
    private  static DruidDataSource ds=new DruidDataSource();

    static {

        try {
            Properties prop = new Properties();
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties");
            prop.load(is);

            String driver = prop.getProperty("jdbc.driverClass");
            String url = prop.getProperty("jdbc.Url");
            String username = prop.getProperty("jdbc.user");
            String password = prop.getProperty("jdbc.password");

            ds.setDriverClassName(driver);
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);

            ds.setInitialSize(10);
            ds.setMaxActive(20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static DataSource getDS() {
        return ds;
    }


    public String length(int t2) {
        String a="";

        for (int i=0;i<t2;i++){
            a=a+"*" ;

        }
        return a;
    }
}

