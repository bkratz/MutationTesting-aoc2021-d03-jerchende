package net.erchen.adventofcode2021.day20;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.erchen.adventofcode2021.common.Matrix;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ImageEnhancer {

    private final Pixel[] algorithm;

    private Matrix<Pixel> image;

    private Pixel initPixel;

    public static ImageEnhancer fromInput(String input) {
        var inputs = input.split("\n\n");
        return ImageEnhancer.builder()
                .algorithm(inputs[0].trim().chars().mapToObj(Pixel::fromChar).toArray(Pixel[]::new))
                .image(Matrix.fromInput(inputs[1].trim(), "\n", "", s -> Pixel.fromChar(s.charAt(0))))
                .initPixel(Pixel.DARK)
                .build();
    }

    public void enhance(int steps) {
        for (int i = 0; i < steps; i++) {
            enhance();
        }
    }

    public void enhance() {
        image.addBorder(1, () -> initPixel);
        var newInitPixel = initPixel == Pixel.DARK ? algorithm[0] : algorithm[255];
        var newImage = Matrix.fromInitValue(image.dimension() + 2, () -> newInitPixel);
        image.allFields().forEach(field -> {
            var newPixel = algorithm[Stream.of(field.topLeft(), field.top(), field.topRight(), field.left(), Optional.of(field), field.right(), field.bottomLeft(), field.bottom(), field.bottomRight())
                    .mapToInt(optionalField -> optionalField.map(Matrix.Field::getValue).map(Pixel::getBinaryValue).orElse(initPixel.getBinaryValue()))
                    .reduce(0, (a, b) -> (a << 1) | b)];
            newImage.setFieldValue(field.getX() + 1, field.getY() + 1, newPixel);
        });
        initPixel = newInitPixel;
        image = newImage;
    }

    public long countLightPixels() {
        return getImage().allFields().filter(pixel -> pixel.getValue() == ImageEnhancer.Pixel.LIGHT).count();
    }

    @Getter
    @RequiredArgsConstructor
    public enum Pixel {
        DARK('.', 0), LIGHT('#', 1);

        private final char representation;
        private final int binaryValue;

        public static Pixel fromChar(int c) {
            return Arrays.stream(Pixel.values()).filter(pixel -> pixel.getRepresentation() == c).findAny().orElseThrow(() -> new IllegalArgumentException("Unknown Char: '" + (char) c + "'"));
        }

        @Override public String toString() {
            return String.valueOf(getRepresentation());
        }
    }

}
