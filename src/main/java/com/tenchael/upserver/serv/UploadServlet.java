package com.tenchael.upserver.serv;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if (isMultipart) {
			String realPath = request.getSession().getServletContext()
					.getRealPath("/files");
			p(realPath);
			File dir = new File(realPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString("UTF-8");
						p(name + " = " + value);
					} else {
						String fileName = item.getName();
						item.write(new File(dir, System.currentTimeMillis()
								+ fileName.substring(fileName.lastIndexOf("."))));
					}
				}
				out.print("文件上传成功");
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
				out.print("文件上传失败");
				out.flush();
			} finally {
				out.close();
			}
		}
	}

	private static void p(Object obj) {
		System.out.println(obj);
	}

}
