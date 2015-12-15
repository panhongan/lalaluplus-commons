package pha.java.util.db;

import org.apache.log4j.Logger;

import pha.java.util.StringUtil;
import pha.java.util.conf.Config;


public class MysqlConfig {
	
	private static Logger logger = Logger.getLogger(MysqlConfig.class);

	public String ip = "";
	
	public short port = 0;
	
	public String db = "";
	
	public String user = "";
	
	public String passwd = "";
	
	public String charset = "utf8";
	
	public MysqlConfig() {
		
	}
	
	public MysqlConfig(String ip, short port, String db,
			String user, String passwd, String charset) {
		this.ip = ip;
		this.port = port;
		this.db = db;
		this.user = user;
		this.passwd = passwd;
		this.charset = charset;
	}
	
	public boolean parse(String confFile) {
		Config config = new Config();
		boolean ret = config.parse(confFile);
		if (ret) {
			try {
				this.ip = config.get("ip");
				this.port = Short.valueOf(config.get("port")).shortValue();
				this.db = config.get("db");
				this.user = config.get("user");
				this.passwd = config.get("passwd");
				this.charset = config.get("charset");
			} catch (Exception e) {
				ret = false;
				logger.warn(e.getMessage());
			}
		}
		
		return ret;
	}
	
	public boolean isOK() {
		return (!StringUtil.isEmpty(this.ip) &&
				!StringUtil.isEmpty(this.db) &&
				!StringUtil.isEmpty(this.user) &&
				!StringUtil.isEmpty(this.passwd));
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("MysqlConfig: ip = ");
		sb.append(ip);
		sb.append(", port = ");
		sb.append(port);
		sb.append(", db = ");
		sb.append(db);
		sb.append(", user = ");
		sb.append(user);
		sb.append(", passwd = ");
		sb.append(passwd);
		sb.append(", charset = ");
		sb.append(charset);
		return sb.toString();
	}
	
}
