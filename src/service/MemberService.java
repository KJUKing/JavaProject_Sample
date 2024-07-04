package service;

import controller.MainController;
import dao.MemberDao;
import util.JDBCUtil;

import java.util.List;
import java.util.Map;

public class MemberService {
    private static MemberService instance;

    private MemberService() {

    }

    public static MemberService getInstance() {
        if (instance == null) {
            instance = new MemberService();
        }
        return instance;
    }

    MemberDao dao = MemberDao.getInstance();

    public void insertMember(List<Object> param) {
        dao.insertMember(param);
    }

    public boolean login(List<Object> param) {
        boolean pass = true;
        Map<String, Object> map = dao.login(param);
        if (map == null) {
            return false;

        }
        MainController.sessionStorage.put("member", map);
        return pass;
    }

    public List<Map<String, Object>> memberList() {

        return dao.memberList();
    }
}
