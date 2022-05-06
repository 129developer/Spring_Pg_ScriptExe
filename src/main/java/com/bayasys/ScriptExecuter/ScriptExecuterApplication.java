package com.bayasys.ScriptExecuter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ScriptExecuterApplication implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ScriptExecuterApplication.class, args);
    }

    public void run(String... args) throws Exception {
        File in = new File(args[0]);
        BufferedReader fr = new BufferedReader(new FileReader(in));
        fr.lines().forEach(new Consumer<String>() {
            public void accept(String qry) {
                try {
                    Object ob = jdbcTemplate.queryForObject(qry, Object.class);
                    JSONArray result = new JSONArray(ob.toString());
                    System.out.println("result : " + result);
                    System.out.println("outputvalue : " + result.getJSONObject(0).getString("outputvalue"));
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(ScriptExecuterApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
