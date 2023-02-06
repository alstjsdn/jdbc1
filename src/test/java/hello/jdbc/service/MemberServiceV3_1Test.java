package hello.jdbc.service;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import hello.jdbc.repository.MemberRepositoryV2;
import hello.jdbc.repository.MemberRepositoryV3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceV3_1Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";

    public static final String EX = "ex";

    private MemberRepositoryV3 memberRepository;
    private MemberServiceV3_1 memberService;


    @BeforeEach
    void before(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
        memberRepository = new MemberRepositoryV3(dataSource);
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);

        memberService = new MemberServiceV3_1(memberRepository,transactionManager);
    }

    @AfterEach
    void after() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(EX);
    }

    @Test
    void accountTransfer() throws SQLException {
        Member memberA =new Member(MEMBER_A,10000);
        Member memberB =new Member(MEMBER_B,10000);
        Member memberEX =new Member(EX,10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberRepository.save(memberEX);

        memberService.accountTransfer(memberA.getMemberId(),memberB.getMemberId(),2000);

        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberB.getMemberId());
        Member findMemberEX = memberRepository.findById(memberEX.getMemberId());

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12000);


    }

    @Test
    void accountTransfer2() throws SQLException {
        Member memberA =new Member(MEMBER_A,10000);
        Member memberB =new Member(MEMBER_B,10000);
        Member memberEX =new Member(EX,10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberRepository.save(memberEX);

        Assertions.assertThatThrownBy(() ->
                        memberService.accountTransfer(memberA.getMemberId(), memberEX.getMemberId(),
                                2000))
                .isInstanceOf(IllegalStateException.class);

        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberB.getMemberId());
        Member findMemberEX = memberRepository.findById(memberEX.getMemberId());

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(10000);
        Assertions.assertThat(findMemberEX.getMoney()).isEqualTo(10000);


    }

}