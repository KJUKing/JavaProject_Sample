package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.MemberService;
import util.ScanUtil;
import view.View;

public class MainController{
	static public Map<String, Object> sessionStorage = new HashMap<>();

	MemberService memberService = MemberService.getInstance();

	public static void main(String[] args) {
		new MainController().start();
	}
	
	private void start() {
		View view = View.MAIN;
		while (true) {
			switch (view) {
				case MAIN:
					view = main();
					break;
				case LOGIN:
					view = login();
					break;
				case JOIN:
					view = join();
					break;
				default:
					break;

			}
		}
	}

	public View login() {
		System.out.println("로그인 페이지");

		return View.MAIN;
	}

	public View join() {
		System.out.println("회원가입 페이지");

		String id = ScanUtil.nextLine("Id : ");
		String pw = ScanUtil.nextLine("PW : ");
		String name = ScanUtil.nextLine("이름 : ");

		List<Object> param = new ArrayList<>();
		param.add(id);
		param.add(pw);
		param.add(name);

		memberService.insertMember(param);

		return View.MAIN;
	}

	public View main() {
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");

		int sel = ScanUtil.nextInt("메뉴 선택: ");
		switch (sel) {
			case 1:
				return View.LOGIN;
			case 2:
				return View.JOIN;
			default:
				return View.MAIN;
		}
	}

}
