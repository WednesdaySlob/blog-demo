package com.mszlu.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class DataSourceTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  public void testConnection() {
    Long count = jdbcTemplate.queryForObject("select count(*) from ms_sys_user", Long.class);
    System.out.println("记录数：" + count);
  }


//  @Autowired
//  private DataSource dataSource;
//
//  @Test
//  public void getConnection() throws SQLException {
//    System.out.println(dataSource.getConnection());
//  }
}
