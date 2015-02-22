package placemio;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class CreateDatabase {
/*
    public static void main(String args[]) {

        // simple DS for test (not for production!)
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUsername("sa");
        dataSource.setUrl("jdbc:h2:mem");
        dataSource.setPassword("");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        System.out.println("deleting stuff");
        jdbcTemplate.execute("drop table users if exists");
        jdbcTemplate.execute("drop table user_token if exists");
        jdbcTemplate.execute("drop table user_api if exists");
        jdbcTemplate.execute("drop table event if exists");
        jdbcTemplate.execute("drop table event_address if exists");
        jdbcTemplate.execute("drop table event_content if exists");
        System.out.println("done deleting");

        System.out.println("Start Creating");
        jdbcTemplate.execute("create table users(" +
                "id int(11) NOT NULL AUTO_INCREMENT, username varchar(255), password varchar(255), email varchar(255), " +
                "unique_id varchar(255), PRIMARY KEY (id))");

        jdbcTemplate.execute("create table user_token(" +
                "id int(11) NOT NULL AUTO_INCREMENT, username varchar(255), token varchar(255), expire_date TIMESTAMP, " +
                "user_id varchar(255), api_id varchar(255), PRIMARY KEY (id))");

        jdbcTemplate.execute("create table user_api(" +
                "id int(11) NOT NULL AUTO_INCREMENT, api_key varchar(255), api_secret varchar(255), " +
                "user_id varchar(255), PRIMARY KEY (id))");

        jdbcTemplate.execute("create table event(" +
                "id int(11) NOT NULL AUTO_INCREMENT, user_id int(11), creation_date TIMESTAMP,  " +
                "is_deleted int(2) DEFAULT 0, PRIMARY KEY(id))");


        jdbcTemplate.execute("create table event_address(" +
                "id int(11) NOT NULL AUTO_INCREMENT, street_address varchar(255), street_address2 varchar(255), city varchar(255), state varchar(255)," +
                "zip_code int(11), country varchar(255), longitude float, latitude float, event_id int(11), " +
                "PRIMARY KEY (id))");


        jdbcTemplate.execute("create table event_content(" +
                "id int(11) NOT NULL AUTO_INCREMENT, message varchar(255), title varchar(255), type varchar(255), " +
                "age_group varchar(255), event_id int(11), event_date TIMESTAMP, event_time TIME, PRIMARY KEY (id))");

        System.out.println("done creating");

    }
    */
}
