package dao;

import util.JDBCUtil;

import java.util.List;

public class MemberDao {

    private static MemberDao instance;

    private MemberDao() {

    }

    public static MemberDao getInstance() {
        if (instance == null) {
            instance = new MemberDao();
        }
        return instance;
    }
    JDBCUtil jdbc = JDBCUtil.getInstance();

    public void insertMember(List<Object> param) {
        String sql ="INSERT INTO JAVA_MEMBER VALUES((SELECT NVL(MAX(MEM_NO),0)+1 FROM JAVA_MEMBER), ?, ?, ?, 'N', SYSDATE)";
        jdbc.update(sql, param);
    }
}
