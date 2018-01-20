package cz.vhromada.catalog.web.common;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cz.vhromada.catalog.entity.Program;
import cz.vhromada.catalog.web.fo.ProgramFO;

/**
 * A class represents utility class for programs.
 *
 * @author Vladimir Hromada
 */
public final class ProgramUtils {

    /**
     * Creates a new instance of ProgramUtils.
     */
    private ProgramUtils() {
    }

    /**
     * Returns FO for program.
     *
     * @return FO for program
     */
    public static ProgramFO getProgramFO() {
        final ProgramFO program = new ProgramFO();
        program.setId(CatalogUtils.ID);
        program.setName(CatalogUtils.NAME);
        program.setWikiEn(CatalogUtils.EN_WIKI);
        program.setWikiCz(CatalogUtils.CZ_WIKI);
        program.setMediaCount(Integer.toString(CatalogUtils.MEDIA));
        program.setCrack(true);
        program.setSerialKey(true);
        program.setOtherData("Other data");
        program.setNote(CatalogUtils.NOTE);
        program.setPosition(CatalogUtils.POSITION);

        return program;
    }

    /**
     * Returns program.
     *
     * @return program
     */
    public static Program getProgram() {
        final Program program = new Program();
        program.setId(CatalogUtils.ID);
        program.setName(CatalogUtils.NAME);
        program.setWikiEn(CatalogUtils.EN_WIKI);
        program.setWikiCz(CatalogUtils.CZ_WIKI);
        program.setMediaCount(CatalogUtils.MEDIA);
        program.setCrack(true);
        program.setSerialKey(true);
        program.setOtherData("Other data");
        program.setNote(CatalogUtils.NOTE);
        program.setPosition(CatalogUtils.POSITION);

        return program;
    }

    /**
     * Asserts program deep equals.
     *
     * @param expected expected FO for program
     * @param actual   actual program
     */
    public static void assertProgramDeepEquals(final ProgramFO expected, final Program actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(actual.getName()).isEqualTo(expected.getName());
            softly.assertThat(actual.getWikiEn()).isEqualTo(expected.getWikiEn());
            softly.assertThat(actual.getWikiCz()).isEqualTo(expected.getWikiCz());
            softly.assertThat(actual.getMediaCount()).isEqualTo(Integer.parseInt(expected.getMediaCount()));
            softly.assertThat(actual.getCrack()).isEqualTo(expected.getCrack());
            softly.assertThat(actual.getSerialKey()).isEqualTo(expected.getSerialKey());
            softly.assertThat(actual.getOtherData()).isEqualTo(expected.getOtherData());
            softly.assertThat(actual.getNote()).isEqualTo(expected.getNote());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
        });
    }

}
