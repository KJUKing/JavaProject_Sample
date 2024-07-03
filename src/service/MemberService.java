package service;

import dao.MemberDao;
import util.JDBCUtil;

import java.util.List;

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
}
