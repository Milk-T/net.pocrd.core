package net.pocrd.util;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement
public class CommonConfig {
    public static final CommonConfig Instance;

    public static final boolean      isDebug = true;

    private CommonConfig() {}

    static {
        CommonConfig tmp = ConfigUtil.load("Common.config", CommonConfig.class);
        // 默认值设置
        if (tmp == null) {
            tmp = new CommonConfig();
            tmp.autogenPath = "E:\\software\\jd\\";
            tmp.tokenPwd = "KaKS8hro1Ljf7YXIK+iiag5ofiPmaucUqqfBTu7eVVI=";
            tmp.accessLoggerName = "net.pocrd.api.access";
            tmp.staticSignPwd = "pocrd@gmail.com";
            tmp.cacheVersion = "v1.0";
            tmp.cacheType = CacheDBType.Redis;
            tmp.connectString = "jdbc:mysql://112.124.17.212:3306/test?useUnicode=true;characterset=utf-8&user=gkq&password=gkq1990";
            if(isDebug){
                tmp.c3p0config = new C3P0Config("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/test?useUnicode=true;characterset=utf-8",
                        "gkq", "gkq1990", 5, 10, 10, 20, 5, 5);
            }else tmp.c3p0config = new C3P0Config("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gougoudai?useUnicode=true;characterset=utf-8",
                    "root", "123", 5, 10, 10, 20, 5, 5);
        }
        Instance = tmp;
        Instance.accessLogger = LogManager.getLogger(Instance.accessLoggerName);
        Instance.tokenHelper = new TokenHelper(Instance.tokenPwd);
    }

    public String      accessLoggerName;
    public String      tokenPwd;
    public String      staticSignPwd;
    public String      autogenPath;
    public String      cacheVersion;
    public CacheDBType cacheType;
    public String      connectString;
    public C3P0Config  c3p0config;

    @XmlTransient
    public TokenHelper tokenHelper;

    @XmlTransient
    public Logger      accessLogger;

    /**
     * 缓存实现机制
     * 
     * @author guankaiqiang
     */
    public enum CacheDBType {
        Redis, Memcache
    }
}
