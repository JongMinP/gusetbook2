package com.cafe24.guestbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.guestbook.dao.GuestBookDao;
import com.cafe24.guestbook.util.WebUtil;
import com.cafe24.guestbook.vo.GuestBookVo;

@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String actionName = request.getParameter("a");

		if ("form".equals(actionName)) {
			String no = request.getParameter("no");

			request.setAttribute("no", no);

			WebUtil.foward(request, response, "/WEB-INF/views/deleteform.jsp");

		} else if ("delete".equals(actionName)) {

			String password = request.getParameter("password");
			int no = Integer.parseInt(request.getParameter("no"));

			System.out.println(no);
			System.out.println(password);

			GuestBookDao dao = new GuestBookDao();
			GuestBookVo vo = dao.getGusetBook(no);
			System.out.println(vo.getPassword());
			if (password.equals(vo.getPassword())) {
				dao.delete(no);
			}
			WebUtil.redirect(request, response, "/guestbook2/gb");

		} else if ("add".equals(actionName)) {

			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");

			GuestBookDao dao = new GuestBookDao();

			GuestBookVo vo = new GuestBookVo();

			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);

			dao.insert(vo);

			WebUtil.redirect(request, response, "/guestbook2/gb");

		} else {
			GuestBookDao dao = new GuestBookDao();

			List<GuestBookVo> list = dao.getList();

			request.setAttribute("list", list);

			WebUtil.foward(request, response, "/WEB-INF/views/index.jsp");

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
