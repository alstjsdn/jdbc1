package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        //DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

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