package pha.java.util.warning;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import pha.java.util.CollectionUtil;
import pha.java.util.process.RuntimeUtil;

public class WarningUtil {
	
	private static Logger logger = Logger.getLogger(WarningUtil.class);
	
	private static final String SEND_MAIL_SCRIPT = "/data/apps/warning-framework/bin/send_mail.py";
	
	private static final String SEND_SMS_SCRIPT = "/data/apps/warning-framework/bin/send_sms.py";
	
	public static boolean sendMail(Set<String> users, String subject, String mailContent) {
		String user_list = CollectionUtil.formatCollection(users, ",");
		List<String> err = new ArrayList<String>();
		String [] cmd = {"python", SEND_MAIL_SCRIPT, user_list, subject, mailContent};
		boolean ret = RuntimeUtil.exec(cmd, null, err);
		if (!ret) {
			logger.warn(err.toString());
		}
		
		return ret;
	}
	
	public static boolean sendSMS(Set<String> phones, String content) {
		String smsContent = content.replaceAll("<br>", "\n").replaceAll("=", ":");
		String phoneList = CollectionUtil.formatCollection(phones, ",");
		String [] cmd = new String[]{"python", SEND_SMS_SCRIPT, phoneList, smsContent};
		List<String> err = new ArrayList<String>();
		boolean ret = RuntimeUtil.exec(cmd, null, err);
		if (!ret) {
			logger.warn(err.toString());
		}
		
		return ret;
	}

}
