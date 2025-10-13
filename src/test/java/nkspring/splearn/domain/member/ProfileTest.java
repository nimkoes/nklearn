package nkspring.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileTest {

    @Test
    void profile() {
        new Profile("nk");
        new Profile("nk100");
        new Profile("12345");
        new Profile("");
    }

    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile("toolongtoolongtoolongtoolong")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        var profile = new Profile("nk");

        assertThat(profile.url()).isEqualTo("@nk");
    }
}
