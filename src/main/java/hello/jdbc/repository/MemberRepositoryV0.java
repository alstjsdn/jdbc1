package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;

import java.sql.*;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.DBConnectionUtil.getConnection;

public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql ="insert into member(member_id,money) values(?,?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2,member.getMoney());
            pstmt.executeUpdate();
            return member;
        }catch (SQLException e) {
            throw e;
        }finally {
            close(con,pstmt,null);

        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id=?";

        Connection con =null;
        PreparedStatement pstmt = null;
        ResultSet rs =null;

        try {
            con =getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,memberId);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getNString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }else {
                throw new NoSuchElementException(memberId);
            }

        }catch (SQLException e) {
            throw e;
        }finally {
            close(con,pstmt,rs);
        }

    }
    public void update(String memberId,int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        Connection con =null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,money);
            pstmt.setString(2,memberId);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            throw e;
        }finally {
            close(con,pstmt,null);

        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";

        Connection con =null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,memberId);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            throw e;
        }finally {
            close(con,pstmt,null);

        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) throws SQLException{

        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {

            }
        }

        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {

            }
        }
        if(con != null) {
            try {
                con.close();

            }catch (SQLException e) {

            }
        }



    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
