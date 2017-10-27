package tikape.runko;

import spark.*;
import java.util.*;
import spark.template.thymeleaf.*;
import java.sql.*;
import tikape.runko.database.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Database db = new Database("jdbc:sqlite:Aseluvat.db");

        AseDao aseDao = new AseDao(db);
        VarusmiesDao varusmiesDao = new VarusmiesDao(db);
        KayttooikeusDao kayttooikeusDao = new KayttooikeusDao(db);
        
        Spark.get("/aseet/:id", (req, res) -> { // ase-sivu
            int a = Integer.parseInt(req.params(":id"));
            Ase ase = aseDao.findOne(a);

            List<Varusmies> oikeudelliset = kayttooikeusDao.AseeseenOikeutetut(a);

            HashMap map = new HashMap<>();
            map.put("ase", ase);
            map.put("oikeudelliset", oikeudelliset);

            return new ModelAndView(map, "ase");
        }, new ThymeleafTemplateEngine());

        Spark.post("/aseet/:id", (req, res) -> { // käyttöoikeuden lisäys tietylle aseelle!
            HashMap map = new HashMap<>();
            List<Varusmies> varusmiehet = varusmiesDao.findAll();
            Integer aseId = Integer.parseInt(req.params(":id"));
            map.put("ase", aseDao.findOne(aseId));
            map.put("varusmiehet", varusmiehet);

            return new ModelAndView(map, "ase");
        });
        Spark.get("/aseet", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Ase> aseet = aseDao.findAll();
            map.put("aseet", aseet);
            return new ModelAndView(map, "aseet");
        }, new ThymeleafTemplateEngine());

        Spark.get("/varusmiehet/:id", (req, res) -> {
            Varusmies varusmies = varusmiesDao.findOne(req.params(":id"));
            List<Ase> aseetJoihinOikeus = kayttooikeusDao.AseetJoihinLupa(varusmies.getHetu());

            HashMap map = new HashMap<>();
            map.put("varusmies", varusmies);
            map.put("aseet", aseetJoihinOikeus);
            return new ModelAndView(map, "varusmies");
        }, new ThymeleafTemplateEngine());

        Spark.get("/varusmiehet", (req, res) -> {
            Map map = new HashMap();
            List<Varusmies> varusmiehet = varusmiesDao.findAll();
            map.put("varusmiehet", varusmiehet);
            return new ModelAndView(map, "varusmiehet");
        }, new ThymeleafTemplateEngine());

        Spark.get("/paasivu", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "paasivu");
        }, new ThymeleafTemplateEngine());

        Spark.get("/haku", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "etsinta");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.get("/aselisays", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "lisaaase");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.post("/aselisays", (req, res) -> {
            Ase lisattava = new Ase(req.queryParams("aeeenNimi"), Integer.parseInt(req.queryParams("aseenNumero")));
            Map map = new HashMap();
            return new ModelAndView(map, "lisaaase_ok");
        }, new ThymeleafTemplateEngine());
        Spark.post("/haku", (req, res) -> { // käyttöoikeuden lisäys tietylle aseelle!
            String hakutermi = req.queryParams("hakusana");
            Map map = new HashMap();
            List<Varusmies> varusmiehet = varusmiesDao.findAll();
            map.put("tulokset", varusmiehet);
            return new ModelAndView(map, "etsinta");
        }, new ThymeleafTemplateEngine());
        Spark.post("/varusmieslisays", (req, res) -> { // käyttöoikeuden lisäys tietylle aseelle!
            Varusmies lisattava = new Varusmies(req.queryParams("nimi"), req.queryParams("hetu"));
            Map map = new HashMap();
            return new ModelAndView(map, "lisaavarusmies_ok");
        }, new ThymeleafTemplateEngine());
        Spark.get("/varusmieslisays", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "lisaavarusmies");
        }, new ThymeleafTemplateEngine());
    }
}
