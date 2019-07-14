package org.github._1c_syntax.mdclasses;

import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationVersion;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationVersionTest {

    @Test
    public void testClass() {

        String version8_3_10 = "Version_8_3_10";
        String versionDontUse = "DontUse";

        ConfigurationVersion version;

        version = new ConfigurationVersion(8, 3, 99);
        assertThat(version.getMajor()).isEqualTo(8);
        assertThat(version.getMinor()).isEqualTo(3);
        assertThat(version.getPatch()).isEqualTo(99);

        version = new ConfigurationVersion(versionDontUse);
        assertThat(version.getMajor()).isEqualTo(8);
        assertThat(version.getMinor()).isEqualTo(3);
        assertThat(version.getPatch()).isEqualTo(99);

        version = new ConfigurationVersion(version8_3_10);
        assertThat(version.getMajor()).isEqualTo(8);
        assertThat(version.getMinor()).isEqualTo(3);
        assertThat(version.getPatch()).isEqualTo(10);

    }

    @Test
    public void test_compareTo() {

        ConfigurationVersion versionA = new ConfigurationVersion(8, 3, 10);
        ConfigurationVersion versionB = new ConfigurationVersion(8, 3, 11);

        assertThat(ConfigurationVersion.compareTo(versionA, versionB)).isEqualTo(1);
        assertThat(ConfigurationVersion.compareTo(versionB, versionA)).isEqualTo(-1);
        assertThat(ConfigurationVersion.compareTo(versionA, new ConfigurationVersion(8,3,10))).isEqualTo(0);

    }

}
