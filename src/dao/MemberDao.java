package dao;

import util.JDBCUtil;

import java.util.List;
import java.util.Map;

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

    public Map<String, Object> login(List<Object> param) {

        String sql = "SELECT *\n" +
                "FROM JAVA_MEMBER\n" +
                "WHERE MEM_ID = ?\n" +
                "    AND MEM_PASS =?\n";

        return jdbc.selectOne(sql, param);
    }

    public List<Map<String, Object>> memberList() {

        String sql = "SELECT MEM_NO, MEM_NAME, TO_CHAR(JOIN_DATE, 'YYYY/MM/DD') AS JOIN_DATE\n" +
                "FROM JAVA_MEMBER\n" +
                "WHERE DELYN='N'";
        return jdbc.selectList(sql);
    }
}
