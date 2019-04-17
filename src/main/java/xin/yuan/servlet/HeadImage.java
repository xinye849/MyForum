package xin.yuan.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import xin.yuan.service.UserServise;


public class HeadImage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserServise servise = new UserServise();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unused")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8"); // �޸ı��� �����������

		response.setContentType("application/json; charset=UTF-8");

		Map<String, Object> map = new HashMap<String, Object>();
		PrintWriter out = response.getWriter();

		String photoName = "";
		String userID = "";

		String savePath = getServletContext().getRealPath("/images/head");
		File saveDir = new File(savePath);
		// ����Ŀ¼
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		// �����ļ��ϴ�������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// ���ñ���
		sfu.setHeaderEncoding("UTF-8");
		// �����ϴ��ĵ����ļ�������ֽ���Ϊ2M
		sfu.setFileSizeMax(1024 * 1024 * 2);
		// ����������������ֽ���Ϊ10M
		sfu.setSizeMax(1024 * 1024 * 10);

		try {
			// ���������
			List<FileItem> itemList = sfu.parseRequest(request);
			for (FileItem fileItem : itemList) {
				// ��Ӧ���еĿؼ���name
				String fieldName = fileItem.getFieldName();
				System.out.println("�ؼ����ƣ�" + fieldName);
				// �������ͨ���ؼ�
				if (fileItem.isFormField()) {
					String value = fileItem.getString();
					// ���±���,�������
					value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
					userID = value;
					System.out.println("��ͨ���ݣ�" + fieldName + "=" + value);
					// �ϴ��ļ�
				} else {
					// ����ļ���С
					Long size = fileItem.getSize();
					// ����ļ���
					String fileName = fileItem.getName();
					System.out.println("�ļ�����" + fileName + "\t��С��" + size
							+ "byte");
					photoName = fileName;
					// ���ļ����浽ָ����·��
					File file = new File(savePath, fileName);
					System.out.println(savePath);
					fileItem.write(file);
				}
			}

			// �������ݿ�
			String photoPath = savePath + "/" + photoName;
			String photoPathNew = photoPath.replaceAll("\\\\", "/");

			String rePath = photoPathNew.substring(46);

			int res = servise.updatePhoto(Integer.parseInt(userID), rePath);
			if (res == 1) {
				out.println("�ϴ��ɹ��� ͷ���Ѿ�����,�´ε�¼����.");
			} else {
				out.println("�ϴ�ʧ�ܣ�");
			}
			out.flush();
			out.close();
		} catch (FileSizeLimitExceededException e) {
			request.setAttribute("msg", "�ļ�̫��");
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
