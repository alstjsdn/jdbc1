package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV2 {

    private final MemberRepositoryV2 memberRepository;
    private final DataSource dataSource;

    public void accountTransfer(String fromId,String toId,int money) throws SQLException {
        Connection con = dataSource.getConnection();
        try {
            con.setAutoCommit(false);

            Member fromMember = memberRepository.findById(con,fromId);
            Member toMember = memberRepository.findById(con,toId);

            memberRepository.update(con,fromId, fromMember.getMoney() - money);

            validation(toMember);

            memberRepository.update(con,toId, toMember.getMoney()+money);
            con.commit();
        }catch (Exception e) {
            con.rollback();
            throw new IllegalStateException(e);
        }finally {
            if(con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                }catch (Exception e) {

                }
            }


        }



    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
