package xin.yuan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SysUtil {

	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * ���ܣ�ƴ�ӷ�ҳʱ���html���� author:hujun
	 * 
	 * @param allCount
	 *            �ܵļ�¼��
	 * @param currentPage
	 *            ��ǰҳ��
	 * @param limit
	 *            ÿҳ��ʾ��¼��
	 * @param url
	 *            û�з�ҳ�ǵĲ�ѯ����
	 * @return ƴ�Ӻ��˵�html����
	 */
	public static String createPage(int allCount, int currentPage, int limit,
			String url) {
		String pageStr = "";// �洢����ƴ�Ӻõ�html

		if (allCount == 0) {
			return "";
		}

		synchronized (pageStr) {
			int allPage = (int) Math.ceil((allCount * 1.0) / limit);// ��������ж���ҳ
			int i, j;// ��ҳ����
			String pageCnfig = url;
			int index = pageCnfig.indexOf("?");// �ж�ԭ����url�����Ƿ��в������в�������ԭ���Ļ�������ӣ�û�оͼ����

			for (i = currentPage - 1; i >= currentPage - 4; i--) {
				pageCnfig = url;
				if (i >= 1) {
					if (index > 0) {
						pageCnfig += "&currentPage=" + i + "&limit=" + limit;
					} else {
						pageCnfig += "?currentPage=" + i + "&limit=" + limit;
					}
					pageStr = "<span><a href='" + pageCnfig + "'>" + i
							+ "</a></span>" + pageStr;
				} else {
					break;
				}
			}

			if (currentPage != 1) {
				pageCnfig = url;
				if (index > 0) {
					pageCnfig += "&currentPage=" + (currentPage - 1)
							+ "&limit=" + limit;
				} else {
					pageCnfig += "?currentPage=" + (currentPage - 1)
							+ "&limit=" + limit;
				}
				pageStr = "<span><a href='" + pageCnfig + "'>��һҳ</a></span>"
						+ pageStr;
			}

			for (j = currentPage; j <= currentPage + 4; j++) {
				pageCnfig = url;
				if (j <= allPage) {
					if (index > 0) {
						pageCnfig += "&currentPage=" + j + "&limit=" + limit;
					} else {
						pageCnfig += "?currentPage=" + j + "&limit=" + limit;
					}
					if (currentPage == j) {
						pageStr = pageStr + "<span><a href='" + pageCnfig
								+ "' class='currentPage'>" + j + "</a></span>";
					} else {
						pageStr = pageStr + "<span><a href='" + pageCnfig
								+ "'>" + j + "</a></span>";
					}
				} else {
					break;
				}

			}

			if (currentPage != allPage) {
				pageCnfig = url;
				if (index > 0) {
					pageCnfig += "&currentPage=" + (currentPage + 1)
							+ "&limit=" + limit;
				} else {
					pageCnfig += "?currentPage=" + (currentPage + 1)
							+ "&limit=" + limit;
				}
				pageStr += "<span><a href='" + pageCnfig + "'>��һҳ</a></span>";
			}
		}

		return pageStr;

	}

	public static String formatDate(Date date) {
		return format.format(date);
	}

	public static Date praseDate(String dateStr) throws ParseException {
		return format.parse(dateStr);
	}

	/*public static void main(String[] args) throws ParseException {
		System.out.println(praseDate("2014-01-14 00:00:06"));
	}*/
	
	// ���ص�ǰʱ����ַ���
	public static String getCurrentTime() {
		String time = "";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String strY = "" + year;
		
		int month = now.get(Calendar.MONTH) + 1;
		String strM = checkSmallTen(month);
		
		int day = now.get(Calendar.DAY_OF_MONTH);
		String strD = checkSmallTen(day);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		String strH = "";
		if(hour < 10) {
			strH = " : 0" + hour;
		}else {
			strH = " : " + hour; 
		}
		int min = now.get(Calendar.MINUTE);
		String strMin = checkSmallTen(min);
		int second = now.get(Calendar.SECOND);
		String strSecond = checkSmallTen(second);
		// ��ǰʱ����ַ���
		time = strY + strM + strD + strH + strMin + strSecond;
		return time;
	}
	
	// �ж��Ƿ�С��10
	public static String checkSmallTen(int num) {
		String str = "";
		if(num < 10) {
			str = "-0" + num;
		}else {
			str = "-" + num;
		}
		return str;
	}
}
