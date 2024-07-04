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
                "    AND MEM_PASS =?\n" +
                "AND DELYN ='N'";

        return jdbc.selectOne(sql, param);
    }



    public Map<String, Object> login(int memNo) {

        String sql = "SELECT *\n" +
                "FROM JAVA_MEMBER\n" +
                "WHERE MEM_No ="+memNo;

        return jdbc.selectOne(sql);
    }

    public List<Map<String, Object>> memberList() {

        String sql = "SELECT MEM_NO, MEM_NAME, TO_CHAR(JOIN_DATE, 'YYYY/MM/DD') AS JOIN_DATE\n" +
                "FROM JAVA_MEMBER\n" +
                "WHERE DELYN='N'";
        return jdbc.selectList(sql);
    }

    public void memberUpdatePw(List<Object> param) {
        String sql = "UPDATE JAVA_MEMBER\n" +
                "SET MEM_PASS = ?\n" +
                "WHERE MEM_NO = ?";
        jdbc.update(sql, param);
    }

    public void memberUpdateId(List<Object> param) {
        String sql = "UPDATE JAVA_MEMBER\n" +
                "SET MEM_NAME = ?\n" +
                "WHERE MEM_NO = ?";
        jdbc.update(sql, param);
    }

    public void memberDelete(List<Object> param) {
        String sql = "UPDATE JAVA_MEMBER\n" +
                "SET DELYN = 'Y'\n" +
                "WHERE MEM_NO = ? ";
        jdbc.update(sql, param);
    }
}
