package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cz.vhromada.catalog.facade.to.ProgramTO;
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
        program.setId(TestConstants.ID);
        program.setName(TestConstants.NAME);
        program.setWikiEn(TestConstants.EN_WIKI);
        program.setWikiCz(TestConstants.CZ_WIKI);
        program.setMediaCount(Integer.toString(TestConstants.MEDIA));
        program.setCrack(true);
        program.setSerialKey(true);
        program.setOtherData("Other data");
        program.setNote(TestConstants.NOTE);
        program.setPosition(TestConstants.POSITION);

        return program;
    }

    /**
     * Returns TO for program.
     *
     * @return TO for program
     */
    public static ProgramTO getProgramTO() {
        final ProgramTO program = new ProgramTO();
        program.setId(TestConstants.ID);
        program.setName(TestConstants.NAME);
        program.setWikiEn(TestConstants.EN_WIKI);
        program.setWikiCz(TestConstants.CZ_WIKI);
        program.setMediaCount(TestConstants.MEDIA);
        program.setCrack(true);
        program.setSerialKey(true);
        program.setOtherData("Other data");
        program.setNote(TestConstants.NOTE);
        program.setPosition(TestConstants.POSITION);

        return program;
    }

    /**
     * Asserts program deep equals.
     *
     * @param expected expected FO for program
     * @param actual   actual TO for program
     */
    public static void assertProgramDeepEquals(final ProgramFO expected, final ProgramTO actual) {
        assertNotNull(actual);
        assertNotNull(actual.getMediaCount());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getWikiEn(), actual.getWikiEn());
        assertEquals(expected.getWikiCz(), actual.getWikiCz());
        assertEquals(expected.getMediaCount(), Integer.toString(actual.getMediaCount()));
        assertEquals(expected.getCrack(), actual.getCrack());
        assertEquals(expected.getSerialKey(), actual.getSerialKey());
        assertEquals(expected.getOtherData(), actual.getOtherData());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
