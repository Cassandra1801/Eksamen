import com.example.Eksamen.models.Skadesrapport;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SkadesrapportTestTest {

    @Test
    void setPris_happyFlow() {
        // Arrange
        Skadesrapport skade = new Skadesrapport();

        // Act
        skade.setPris(BigDecimal.valueOf(1500.0));

        // Assert
        assertEquals(BigDecimal.valueOf(1500.0), skade.getPris());
    }

    @Test
    void setPris_exceptionFlow() {
        // Arrange
        Skadesrapport skade = new Skadesrapport();

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            skade.setPris(BigDecimal.valueOf(-100.0));
        });
    }
}