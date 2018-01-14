/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.web.secure.jdbc;

import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Controller
public class SampleWebSecureJdbcApplication implements WebMvcConfigurer {

	@GetMapping("/")
	public String home(Map<String, Object> model) {
		model.put("message", "Hello World");
		model.put("title", "Hello Home");
		model.put("date", new Date());
		return "home";
	}

	@RequestMapping("/foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(SampleWebSecureJdbcApplication.class).run(args);
	}

	@Configuration
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/css/**").permitAll().anyRequest()
					.fullyAuthenticated().and().formLogin().loginPage("/login")
					.failureUrl("/login?error").permitAll().and().logout().permitAll();
		}

		@Bean
		public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
			JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
			jdbcUserDetailsManager.setDataSource(dataSource);

			jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select username,authority "
                    + "from authority " + "where username = ?");
			jdbcUserDetailsManager.setGroupAuthoritiesByUsernameQuery("select g.id, g.group_name, ga.authority "
                    + "from group g, group_member gm, group_authority ga "
                    + "where gm.username = ? " + "and g.id = ga.group_id "
                    + "and g.id = gm.group_id");
			jdbcUserDetailsManager.setUsersByUsernameQuery("select username,password,enabled "
                    + "from user " + "where username = ?");

			jdbcUserDetailsManager.setChangePasswordSql("update user set password = ? where username = ?");
			jdbcUserDetailsManager.setCreateAuthoritySql("insert into authority (username, authority) values (?,?)");
			jdbcUserDetailsManager.setCreateUserSql("insert into user (username, password, enabled) values (?,?,?)");

			jdbcUserDetailsManager.setDeleteGroupSql("delete from group where id = ?");
			jdbcUserDetailsManager.setDeleteGroupAuthoritiesSql("delete from group_authority where group_id = ?");
            jdbcUserDetailsManager.setDeleteGroupAuthoritySql("delete from group_authority where group_id = ? and authority = ?");
            jdbcUserDetailsManager.setDeleteGroupMemberSql("delete from group_member where group_id = ? and username = ?");
            jdbcUserDetailsManager.setDeleteGroupMembersSql("delete from group_member where group_id = ?");
            jdbcUserDetailsManager.setDeleteUserSql("delete from user where username = ?");
            jdbcUserDetailsManager.setDeleteUserAuthoritiesSql("delete from authority where username = ?");

            jdbcUserDetailsManager.setFindAllGroupsSql("select group_name from group");
            jdbcUserDetailsManager.setFindGroupIdSql("select id from group where group_name = ?");
            jdbcUserDetailsManager.setFindUsersInGroupSql("select username from group_member gm, group g "
                    + "where gm.group_id = g.id" + " and g.group_name = ?");

            jdbcUserDetailsManager.setGroupAuthoritiesSql("select g.id, g.group_name, ga.authority "
                    + "from group g, group_authority ga "
                    + "where g.group_name = ? "
                    + "and g.id = ga.group_id ");

            jdbcUserDetailsManager.setInsertGroupSql("insert into group (group_name) values (?)");
            jdbcUserDetailsManager.setInsertGroupAuthoritySql("insert into group_authority (group_id, authority) values (?,?)");
            jdbcUserDetailsManager.setInsertGroupMemberSql("insert into group_member (group_id, username) values (?,?)");

            jdbcUserDetailsManager.setRenameGroupSql("update group set group_name = ? where group_name = ?");

            jdbcUserDetailsManager.setUpdateUserSql("update user set password = ?, enabled = ? where username = ?");
            jdbcUserDetailsManager.setUserExistsSql("select username from user where username = ?");

			return jdbcUserDetailsManager;
		}

	}

}
