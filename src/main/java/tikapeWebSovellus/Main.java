package tikapeWebSovellus;

import tikape.runko.Ase;
import tikape.runko.Varusmies;
import spark.*;
import java.util.*;
import spark.template.thymeleaf.*;
import java.sql.*;
import tikape.runko.database.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Connection yhteys = DriverManager.getConnection("jdbc:sqlite:Aseluvat.db");
        Statement komento = yhteys.createStatement();
        ResultSet tulos = komento.executeQuery("SELECT 1");
        if (tulos.next()) {
            System.out.println("Tietokanta toimii!");
        } else {
            System.out.println("Tietokanta ei ole käytössä!");
        }
        
        
//        List<Ase> aseet = new ArrayList();
//        Map<Integer,Ase> aseetNumeroittain = new HashMap();
//        Ase ase1 = new Ase("aseennimi1", 1);
//        aseet.add(ase1);
//        aseetNumeroittain.put(ase1.getAsenumero(), ase1);
//        Ase ase2 = new Ase("aseennimi2", 2);
//        aseet.add(ase2);
//        aseetNumeroittain.put(ase2.getAsenumero(), ase2);
//        
//        Map<String,Varusmies> varusmiehetNumeroittain = new HashMap();
//        List<Varusmies> varusmiehet = new ArrayList();
//        Varusmies vm1 = new Varusmies("varusmies1", "1");
//        varusmiehet.add(vm1);
//        Varusmies vm2 = new Varusmies("varusmies2", "2");
//        varusmiehet.add(vm2);
//        Varusmies vm3 = new Varusmies("varusmies3", "3");
//        varusmiehet.add(vm3);
//        Varusmies vm4 = new Varusmies("varusmies4", "4");
//        varusmiehet.add(vm4);
//        varusmiehetNumeroittain.put(vm1.getHetu(), vm1);
//        varusmiehetNumeroittain.put(vm2.getHetu(), vm2);
//        varusmiehetNumeroittain.put(vm3.getHetu(), vm3);
//        varusmiehetNumeroittain.put(vm4.getHetu(), vm4);
//        
//        
//        Map<Ase,List<Varusmies>> kayttooikeutetut = new HashMap();
//        List<Varusmies> ko1 = new ArrayList();
//        ko1.add(varusmiehet.get(0));
//        ko1.add(varusmiehet.get(2));
//        
//        kayttooikeutetut.put(ase1,varusmiehet);
//        kayttooikeutetut.put(ase2,ko1);
        Database database = new Database("Aseluvat.db");
        VarusmiesDao varusmiesDao = new VarusmiesDao(database);
        AseDao aseDao = new AseDao(database);
        KayttooikeusDao kayttooikeusDao = new KayttooikeusDao(database);
        
        Spark.get("/ase/:id", (req, res) -> {
            Ase ase = aseDao.findOne(Integer.parseInt(":id"));
            List<Varusmies> varusmieslista = kayttooikeusDao.findKo(ase.getAsenumero());
            HashMap map = new HashMap<>();
            map.put("nimi",ase.getNimi());
            map.put("numero",ase.getAsenumero());
            map.put("kayttooikeutetut",varusmieslista);
            return new ModelAndView(map, "ase");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/aseet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aseet",aseDao.findAll());
            return new ModelAndView(map, "aseet");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/varusmies/:id", (req, res) -> {
            Varusmies vm = varusmiesDao.findOne(":id");
            HashMap map = new HashMap<>();
            map.put("aseet",aseDao.findAll());
            map.put("nimi",vm.getNimi());
            map.put("numero",vm.getHetu());
            return new ModelAndView(map, "varusmies");
        }, new ThymeleafTemplateEngine());
        Spark.post("/poistakayttooikeus/:id_ase/id_varusmies", (req, res) -> {
            Ase ase = aseDao.findOne(Integer.parseInt(":id_ase"));
            Varusmies vm = varusmiesDao.findOne(":id_varusmies");
            kayttooikeusDao.delete(Integer.parseInt(":id_ase"));
            res.redirect("/ase/"+ase.getAsenumero());
            return "";
        });
        
        
//
//        Spark.get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        Spark.get("/aseet", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("aseet",aseet);
//            System.out.println("Map aseet ok");
//            return new ModelAndView(map, "aseet");
//        }, new ThymeleafTemplateEngine());
//        Spark.get("/virhe", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("aseet",aseet);
//            System.out.println("Map aseet ok");
//            return new ModelAndView(map, "virhe");
//        }, new ThymeleafTemplateEngine());
//        
//        
//        Spark.post("/aseet", (req, res) -> {
//            String numero = req.queryParams("numero");
//            if (numero.equals("david")) {
//            res.redirect("/virhe");
//            }
//            String tyyppi = req.queryParams("tyyppi");
//            aseet.add(new Ase(numero).toString());
//            System.out.println(aseet.get(0));
//            System.out.println("Lisäys ok!");
//            res.redirect("/aseet");
//            System.out.println("Uudelleenohjaus ok!");
//            return "";
//        });
//        
//        Spark.get("/index", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "index");
//        }, new ThymeleafTemplateEngine());
//        
//        Spark.get("/haeOpiskelija", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "haeOpiskelija");
//        }, new ThymeleafTemplateEngine());
//        
//        Spark.get("/lisaaOpiskelija", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "lisaaOpiskelija");
//        }, new ThymeleafTemplateEngine());
//        
//        Spark.get("/lisaaKurssisuoritus", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "lisaaKurssisuoritus");
//        }, new ThymeleafTemplateEngine());
//        
//        
//        Spark.get("/lisaaKurssi", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "lisaaKurssi");
//        }, new ThymeleafTemplateEngine());
//        
//        
//        Spark.get("/listaaKurssinOpiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "listaaKurssinOpiskelijat");
//        }, new ThymeleafTemplateEngine());
//        
//        
//        Spark.get("/lisaaOpiskelijaKurssille", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "lisaaOpiskelijaKurssille");
//        }, new ThymeleafTemplateEngine());
//        
    }
}
