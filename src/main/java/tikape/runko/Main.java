package tikapeWebSovellus;

import spark.*;
import java.util.*;
import spark.template.thymeleaf.*;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Connection yhteys = DriverManager.getConnection("jdbc:sqlite:tietokanta.db");
        Statement komento = yhteys.createStatement();
        ResultSet tulos = komento.executeQuery("SELECT 1");
        if (tulos.next()) {
            System.out.println("Tietokanta toimii!");
        } else {
            System.out.println("Tietokanta ei ole käytössä!");
        }
        Spark.get("/sivu", (req, res) -> {
            HashMap map = new HashMap<>();

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());

        Spark.get("/kurssit", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "kurssit");
        }, new ThymeleafTemplateEngine());

        Spark.get("/index", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/haeOpiskelija", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "haeOpiskelija");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/lisaaOpiskelija", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "lisaaOpiskelija");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/lisaaKurssisuoritus", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "lisaaKurssisuoritus");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.get("/lisaaKurssi", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "lisaaKurssi");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.get("/listaaKurssinOpiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "listaaKurssinOpiskelijat");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.get("/lisaaOpiskelijaKurssille", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "lisaaOpiskelijaKurssille");
        }, new ThymeleafTemplateEngine());
        
    }
}
