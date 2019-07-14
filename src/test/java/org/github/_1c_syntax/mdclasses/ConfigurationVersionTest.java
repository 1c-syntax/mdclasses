package org.github._1c_syntax.mdclasses;

import org.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationVersionTest {

    @Test
    public void testClass() {

        String version8_3_10 = "Version_8_3_10";
        String versionDontUse = "DontUse";

        CompatibilityMode version;

        version = new CompatibilityMode(8, 3, 99);
        assertThat(version.getMajor()).isEqualTo(8);
        assertThat(version.getMinor()).isEqualTo(3);
        assertThat(version.getPatch()).isEqualTo(99);

        version = new CompatibilityMode(versionDontUse);
        assertThat(version.getMajor()).isEqualTo(8);
        assertThat(version.getMinor()).isEqualTo(3);
        assertThat(version.getPatch()).isEqualTo(99);

        version = new CompatibilityMode(version8_3_10);
        assertThat(version.getMajor()).isEqualTo(8);
        assertThat(version.getMinor()).isEqualTo(3);
        assertThat(version.getPatch()).isEqualTo(10);

    }

    @Test
    public void test_compareTo() {

        CompatibilityMode versionA = new CompatibilityMode(8, 3, 10);
        CompatibilityMode versionB = new CompatibilityMode(8, 3, 11);

        assertThat(CompatibilityMode.compareTo(versionA, versionB)).isEqualTo(1);
        assertThat(CompatibilityMode.compareTo(versionB, versionA)).isEqualTo(-1);
        assertThat(CompatibilityMode.compareTo(versionA, new CompatibilityMode(8,3,10))).isEqualTo(0);

    }

}
