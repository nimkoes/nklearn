package nkspring.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void equality() {
        var email1 = new Email("nk@splearn.app");
        var email2 = new Email("nk@splearn.app");

        Assertions.assertThat(email1).isEqualTo(email2);
    }
}
