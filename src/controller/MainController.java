package controller;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLOutput;
import java.sql.Timestamp;
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
				case MEMBER_MAIN:
					view = memberMain();
					break;
				case MEMBER_LIST:
					view = memberList();
					break;
				case MEMBER_UPDATE:
					view = memberUpdate();
					break;
				case MEMBER_DELETE:
					view = memberDelete();
					break;
				default:
					break;

			}
		}
	}

	private View memberUpdate() {
		System.out.println("회원 수정 페이지");

		System.out.println("1. 비밀번호 수정");
		System.out.println("2. 이름 수정");
		System.out.println("3. 메일 메뉴");

		int sel = ScanUtil.menu();
//		Map<String, Object> member = (Map<String, Object>) sessionStorage.get("member");
		Map<String, Object> member = (Map<String, Object>) sessionStorage.get("member");
		BigDecimal no = (BigDecimal) member.get("MEM_NO");
		int memNo = no.intValue();

		if (sel == 1) {
			List<Object> param = new ArrayList<>();
			String pass = ScanUtil.nextLine("PW : ");
			param.add(pass);
			param.add(memNo);

			memberService.memberUpdatePw(param);

			return View.MEMBER_MAIN;

		} else if (sel == 2) {

			List<Object> param = new ArrayList<>();
			String name = ScanUtil.nextLine("NAME : ");
			param.add(name);
			param.add(memNo);

			memberService.memberUpdateID(param);
			memberService.login(memNo);
			member = (Map<String, Object>) sessionStorage.get("member");
			System.out.println(member.get("MEM_NAME")+"님 정보가 수정되었습니다.");
			return View.MEMBER_MAIN;

		} else if (sel == 3) {
			return View.MEMBER_MAIN;
		} else {
			return View.MEMBER_MAIN;
		}
	}

	private View memberDelete() {
		System.out.println("회원 탈퇴");
		System.out.println("1. 회원 탈퇴");
		System.out.println("2. 메인 메뉴");
		int sel = ScanUtil.menu();
		switch (sel) {
			case 1:
				Map<String, Object> member = (Map<String, Object>) sessionStorage.get("member");
				BigDecimal no = (BigDecimal) member.get("MEM_NO");
				List<Object> param = new ArrayList<>();
				int memNo = no.intValue();
				param.add(memNo);
				memberService.delete(param);
				sessionStorage.remove("member");
				System.out.println(member.get("MEM_NAME")+"님이 탈퇴 되었습니다.");
				return  View.MAIN;
			case 2:
				return  View.MEMBER_MAIN;
			default:
				return  View.MEMBER_DELETE;
		}
	}

	private View memberList() {
		System.out.println("회원 리스트 페이지");
		List<Map<String, Object>> memberList = memberService.memberList();
		for (Map<String, Object> map : memberList) {
			BigDecimal no = (BigDecimal) map.get("MEM_NO");
//			int num = no.intValue();
//			long num = no.longValue();
//			double num = no.doubleValue();
			String name = (String) map.get("MEM_NAME");
//			Timestamp date = (Timestamp) map.get("JOIN_DATE");
			String date = (String) map.get("JOIN_DATE");
			//쿼리를통해 date타입에서 String타입으로 형변환도가능
			System.out.println("번호 : "+no+" | 이름: "+name+" | 가입일자 : "+date);
		}
		return View.MEMBER_MAIN;
	}

	private View memberMain() {
		System.out.println("회원 메인 페이지");
		System.out.println("1. 전체 회원 리스트 출력");
		System.out.println("2. 회원정보 수정");
		System.out.println("3. 회원탈퇴");
		System.out.println("4. 로그아웃");

		int sel = ScanUtil.menu();
		switch (sel) {
			case 1:
				return View.MEMBER_LIST;
			case 2:
				return View.MEMBER_UPDATE;
			case 3:
				return View.MEMBER_DELETE;
			case 4:
				Map member = (Map) sessionStorage.remove("member");
				System.out.println(member.get("MEM_NAME")+"님 로그아웃 하셨습니다.");
				return View.MAIN;

			default:
				return View.MEMBER_MAIN;
		}
	}

	public View login() {
		System.out.println("로그인 페이지");
		String id = ScanUtil.nextLine("ID : ");
		String pw = ScanUtil.nextLine("pw : ");
		List<Object> param = new ArrayList<>();
		param.add(id);
		param.add(pw);
		boolean pass = memberService.login(param);
		if (!pass) {
			System.out.println("로그인에 실패하였습니다");
			return View.LOGIN;
		}
		Map<String, Object> member = (Map<String, Object>) sessionStorage.get("member");
		String memName = (String) member.get("MEM_NAME");
		System.out.println(memName +"님 환영합니다");
		return View.MEMBER_MAIN;
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
