package org.languagetool.rules.ca;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Catalan;
import org.languagetool.rules.RuleMatch;

public class PronomFebleDuplicateRuleTest_Purified {

    private PronomFebleDuplicateRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new PronomFebleDuplicateRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(Catalan.getInstance());
    }

    private void assertCorrect(String sentence) throws IOException {
        final RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrect("N'hi ha d'haver.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertCorrect("Hi podria haver un error.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertCorrect("Es divertien llançant-se pedres.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertCorrect("Es recomana tapar-se la boca.");
    }

    @Test
    public void testRule_5() throws IOException {
        assertCorrect("S'ordena dutxar-se cada dia.");
    }

    @Test
    public void testRule_6() throws IOException {
        assertCorrect("Es va quedar barallant-se amb el seu amic.");
    }

    @Test
    public void testRule_7() throws IOException {
        assertCorrect("Es va quedar se");
    }

    @Test
    public void testRule_8() throws IOException {
        assertCorrect("M’encantava enfilar-me");
    }

    @Test
    public void testRule_9() throws IOException {
        assertCorrect("t'obliguen a penjar-te");
    }

    @Test
    public void testRule_10() throws IOException {
        assertCorrect("ens permeten endinsar-nos");
    }

    @Test
    public void testRule_11() throws IOException {
        assertCorrect("els llepaven fins a donar-los");
    }

    @Test
    public void testRule_12() throws IOException {
        assertCorrect("em fa doblegar fins a tocar-me");
    }

    @Test
    public void testRule_13() throws IOException {
        assertCorrect("la batalla per defensar-la");
    }

    @Test
    public void testRule_14() throws IOException {
        assertCorrect("ens convida a treure'ns-la");
    }

    @Test
    public void testRule_15() throws IOException {
        assertCorrect("ens ve a buscar per ajudar-nos");
    }

    @Test
    public void testRule_16() throws IOException {
        assertCorrect("et fan adonar-te");
    }

    @Test
    public void testRule_17() throws IOException {
        assertCorrect("m'agrada enfonsar-me");
    }

    @Test
    public void testRule_18() throws IOException {
        assertCorrect("em dedico a fer-me");
    }

    @Test
    public void testRule_19() throws IOException {
        assertCorrect("la mira sense veure-la");
    }

    @Test
    public void testRule_20() throws IOException {
        assertCorrect("l'havia podat fins a a deixar-lo");
    }

    @Test
    public void testRule_21() throws IOException {
        assertCorrect("em costava deixar-me anar");
    }

    @Test
    public void testRule_22() throws IOException {
        assertCorrect("m'obliga a allunyar-me");
    }

    @Test
    public void testRule_23() throws IOException {
        assertCorrect("el papà havia de canviar-lo");
    }

    @Test
    public void testRule_24() throws IOException {
        assertCorrect("ens congregava per assabentar-nos");
    }

    @Test
    public void testRule_25() throws IOException {
        assertCorrect("es podia morir de taponar-se-li");
    }

    @Test
    public void testRule_26() throws IOException {
        assertCorrect("l’hagin preservada sense tocar-la");
    }

    @Test
    public void testRule_27() throws IOException {
        assertCorrect("li impedeixi aconseguir-la");
    }

    @Test
    public void testRule_28() throws IOException {
        assertCorrect("us he fet venir per llevar-vos");
    }

    @Test
    public void testRule_29() throws IOException {
        assertCorrect("ajuda'm a alçar-me");
    }

    @Test
    public void testRule_30() throws IOException {
        assertCorrect("l'esperava per agrair-li");
    }

    @Test
    public void testRule_31() throws IOException {
        assertCorrect("els va empènyer a adreçar-li");
    }

    @Test
    public void testRule_32() throws IOException {
        assertCorrect("em vaig oblidar de rentar-me");
    }

    @Test
    public void testRule_33() throws IOException {
        assertCorrect("ens ajudà a animar-nos");
    }

    @Test
    public void testRule_34() throws IOException {
        assertCorrect("l'encalçava sense poder atrapar-la");
    }

    @Test
    public void testRule_35() throws IOException {
        assertCorrect("em manava barrejar-me");
    }

    @Test
    public void testRule_36() throws IOException {
        assertCorrect("el convidà a obrir-los");
    }

    @Test
    public void testRule_37() throws IOException {
        assertCorrect("es disposava a despullar-se");
    }

    @Test
    public void testRule_38() throws IOException {
        assertCorrect("es mudà per dirigir-se");
    }

    @Test
    public void testRule_39() throws IOException {
        assertCorrect("li va costar d'aconseguir tenir-lo");
    }

    @Test
    public void testRule_40() throws IOException {
        assertCorrect("es va poder estar d'atansar-s'hi");
    }

    @Test
    public void testRule_41() throws IOException {
        assertCorrect("el dissuadeixi de matar-lo");
    }

    @Test
    public void testRule_42() throws IOException {
        assertCorrect("la va festejar per engalipar-la");
    }

    @Test
    public void testRule_43() throws IOException {
        assertCorrect("s'havia negat a casar-s'hi");
    }

    @Test
    public void testRule_44() throws IOException {
        assertCorrect("es disposaven a envolar-se");
    }

    @Test
    public void testRule_45() throws IOException {
        assertCorrect("li sabia d'haver-la repudiada");
    }

    @Test
    public void testRule_46() throws IOException {
        assertCorrect("li sabia greu d'haver-la repudiada");
    }

    @Test
    public void testRule_47() throws IOException {
        assertCorrect("el féu acostar per besar-li");
    }

    @Test
    public void testRule_48() throws IOException {
        assertCorrect("En acostar-se va fer-se això.");
    }

    @Test
    public void testRule_49() throws IOException {
        assertCorrect("Quan em va veure se'n va anar corrent.");
    }

    @Test
    public void testRule_50() throws IOException {
        assertCorrect("Li hauria agradat poder tenir-hi una conversa");
    }

    @Test
    public void testRule_51() throws IOException {
        assertCorrect("perquè els molts ulls que les volien veure poguessin saciar-se");
    }

    @Test
    public void testRule_52() throws IOException {
        assertCorrect("El pare el va fer anar a rentar-se la sang.");
    }

    @Test
    public void testRule_53() throws IOException {
        assertCorrect("se n'anà a veure'l");
    }

    @Test
    public void testRule_54() throws IOException {
        assertCorrect("Me n'aniria a queixar-me.");
    }

    @Test
    public void testRule_55() throws IOException {
        assertCorrect("se n’aniran a viure-hi");
    }

    @Test
    public void testRule_56() throws IOException {
        assertCorrect("Als exemples d'excepció que s'han presentat s'hi poden afegir per causes similars");
    }

    @Test
    public void testRule_57() throws IOException {
        assertCorrect("els nous materials que es vagin dipositant poden veure's encara afectats per forces");
    }

    @Test
    public void testRule_58() throws IOException {
        assertCorrect("i pensant que algú l'havia engaltada s'hi atansà");
    }

    @Test
    public void testRule_59() throws IOException {
        assertCorrect("hi anava a prendre'n possessió");
    }

    @Test
    public void testRule_60() throws IOException {
        assertCorrect("hi anava a veure'l cada dia");
    }

    @Test
    public void testRule_61() throws IOException {
        assertCorrect("se'n va a salvar-se");
    }

    @Test
    public void testRule_62_testMerged_62() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("S'ha de fer-se"));
        assertEquals(1, matches.length);
        assertEquals("Ha de fer-se", matches[0].getSuggestedReplacements().get(0));
        assertEquals("S'ha de fer", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("Ell, en voldrà donar-nos-en més?"));
        assertEquals("voldrà donar-nos-en", matches[0].getSuggestedReplacements().get(0));
        assertEquals("en voldrà donar", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("N'ha d'haver-hi"));
        assertEquals("N'hi ha d'haver", matches[0].getSuggestedReplacements().get(0));
        assertEquals("Ha d'haver-n'hi", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("Li ha de fer-se-li."));
        assertEquals("Ha de fer-se-li", matches[0].getSuggestedReplacements().get(0));
        assertEquals("Li ha de fer", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("n'hi continuà havent-hi"));
        assertEquals("n'hi continuà havent", matches[0].getSuggestedReplacements().get(0));
        assertEquals("continuà havent-n'hi", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("Hi ha d'haver-ne"));
        matches = rule.match(lt.getAnalyzedSentence("Hi continuarà havent-hi"));
        assertEquals("Continuarà havent-hi", matches[0].getSuggestedReplacements().get(0));
        assertEquals("Hi continuarà havent", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("En continuarà havent-hi"));
        assertEquals("N'hi continuarà havent", matches[0].getSuggestedReplacements().get(0));
        assertEquals("Continuarà havent-n'hi", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("Es va continuar barallant-se amb el seu amic."));
        assertEquals("Va continuar barallant-se", matches[0].getSuggestedReplacements().get(0));
        assertEquals("Es va continuar barallant", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("Hi podria haver-hi"));
        assertEquals("Podria haver-hi", matches[0].getSuggestedReplacements().get(0));
        assertEquals("Hi podria haver", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("N'hi podria haver-n'hi"));
        assertEquals("Podria haver-n'hi", matches[0].getSuggestedReplacements().get(0));
        assertEquals("N'hi podria haver", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("ho puc arreglar-ho"));
        assertEquals("puc arreglar-ho", matches[0].getSuggestedReplacements().get(0));
        assertEquals("ho puc arreglar", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("La volia veure-la."));
        assertEquals("Volia veure-la", matches[0].getSuggestedReplacements().get(0));
        assertEquals("La volia veure", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("En vaig portar-ne quatre."));
        assertEquals("Vaig portar-ne", matches[0].getSuggestedReplacements().get(0));
        assertEquals("En vaig portar", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("Ho hem hagut de fer-ho."));
        assertEquals("Hem hagut de fer-ho", matches[0].getSuggestedReplacements().get(0));
        assertEquals("Ho hem hagut de fer", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("Hi hem hagut de continuar anant-hi."));
        assertEquals("Hem hagut de continuar anant-hi", matches[0].getSuggestedReplacements().get(0));
        assertEquals("Hi hem hagut de continuar anant", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("M'he de rentar-me les dents."));
        assertEquals("He de rentar-me", matches[0].getSuggestedReplacements().get(0));
        assertEquals("M'he de rentar", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("Li ho hem hagut de continuar dient-li-ho."));
        assertEquals("Hem hagut de continuar dient-li-ho", matches[0].getSuggestedReplacements().get(0));
        assertEquals("Li ho hem hagut de continuar dient", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("Et deu enganyar-te."));
        matches = rule.match(lt.getAnalyzedSentence("Et deu voler enganyar-te."));
        matches = rule.match(lt.getAnalyzedSentence("Et deu haver de dir-te."));
        matches = rule.match(lt.getAnalyzedSentence("Ho deu continuar dient-ho."));
        matches = rule.match(lt.getAnalyzedSentence("S'està rebel·lant-se."));
        matches = rule.match(lt.getAnalyzedSentence("Li va començar a dur-li problemes."));
        matches = rule.match(lt.getAnalyzedSentence("S'acabarà carregant-se."));
        matches = rule.match(lt.getAnalyzedSentence("Jo ho vaig ser-hi."));
        assertEquals("vaig ser-hi", matches[0].getSuggestedReplacements().get(0));
        assertEquals("ho vaig ser", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("Jo hi vaig ser-ho."));
        assertEquals("vaig ser-ho", matches[0].getSuggestedReplacements().get(0));
        assertEquals("hi vaig ser", matches[0].getSuggestedReplacements().get(1));
    }
}
