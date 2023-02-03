package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV05 ",10000);
        repository.save(member);

        Member findMember = repository.findById(member.getMemberId());
        System.out.println(findMember.getMemberId());
        Assertions.assertThat(findMember).isEqualTo(member);

        repository.update(member.getMemberId(),20000);
        Member findMember2 = repository.findById(member.getMemberId());
        System.out.println(findMember2.getMoney());
        Assertions.assertThat(findMember2.getMoney()).isEqualTo(20000);

        repository.delete(member.getMemberId());

    }



}