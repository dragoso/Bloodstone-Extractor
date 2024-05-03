package he4ty.extractor;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final Map<Integer, Integer> positions = new HashMap<>();
    private static ByteBuffer spr;
    private static BufferedImage zeroImage;
    public static Properties properties = new Properties();

    public static void main(String[] args) throws IOException {

        FileInputStream fis = new FileInputStream("config.properties");
        properties = new Properties();
        properties.load(fis);
        fis.close();

        try {
            zeroImage = ImageIO.read(new File("zero.png"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(zeroImage, "jpg", baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<SpriteDTO> allObjects = new ArrayList<>();
        sprExtractor(Boolean.parseBoolean(properties.getProperty("allSprites")));
        ByteBuffer dat = ByteBuffer.wrap(Files.readAllBytes(Paths.get(properties.getProperty("bloodstonePath") + "\\data\\bloodstone.dat")));
        var version = dat.getInt();
        var objectsCount = dat.getInt();
        var stop = true;

        while (stop) {
            var objectId = dat.getInt();
            if (objectId == objectsCount) {
                stop = false;
            }
            int v58 = dat.get();
            var sp = SpriteDTO
                    .builder()
                    .id(objectId)
                    .type(v58)
                    .build();
            allObjects.add(sp);
            var v103 = dat.get();
            var v102 = dat.get();
            var v101 = dat.get();
            var v8Mais8 = dat.getInt();
            var v100 = dat.get();
            var v99 = dat.get();
            var v98 = dat.get();
            var v8Mis104 = dat.getInt();
            var Int = dat.getInt();
            var v10 = v58 == 84;
            var v104 = false;
            if (v10) {
                var v97 = dat.get();
                v104 = v97 != 0;
                var v96 = dat.get();
                var v95 = dat.get();
                var v94 = dat.get();
                var v93 = dat.get();
                var v92 = dat.get();
                var v91 = dat.get();
                var v90 = dat.get();
                var v89 = dat.get();
                var v8Mais24 = dat.getInt();
                var v8Mais28 = dat.getInt();
            }
            var v88 = dat.get();
            var v87 = dat.get();
            var v86 = dat.get();
            var v85 = dat.get();
            var v84 = dat.get();
            var v83 = dat.get();
            var v82 = dat.get();
            var v81 = dat.get();
            var v80 = dat.get();
            var v79 = dat.get();
            var v78 = dat.get();
            var v77 = dat.get();
            var v52 = dat.getShort();
            var v76 = dat.get();
            var v44 = dat.getShort();
            var v8Mais4 = dat.getInt();
            var v8Mais82 = dat.getInt();
            var v8Mais56 = dat.getInt();
            var v75 = dat.get();
            var v74 = dat.get();
            var v51 = dat.getShort();
            var v8Mais62 = dat.getInt();
            var v73 = dat.get();
            var v72 = dat.get();
            var v71 = dat.get();
            var v70 = dat.get();
            var v69 = dat.get();
            var v68 = dat.get();
            var v67 = dat.get();
            var v66 = dat.get();
            var v65 = dat.get();
            var v50 = dat.getShort();
            var v49 = dat.getShort();
            var v64 = dat.get();
            var v63 = dat.get();
            var spritesCount = dat.get();

            for (byte spCount = 0; spCount < spritesCount; spCount++) {
                var ob = ActionByPosition
                        .builder()
                        .id(objectId)
                        .v8Mais24(dat.getInt())
                        .directionEnum(v58 == 84 ? DirectionEnum.valueOf(dat.get()) : DirectionEnum.NON)
                        .actionEnum(v58 == 84 ? ActionEnum.valueOf(dat.get()) : ActionEnum.NON)
                        .build();
                sp.getActionByPositions().add(ob);
                var v21 = 0;
                var v22 = 0;
                var v47 = 0;
                do {
                    if (v21 == 1 && !v104)
                        break;
                    v22 = 1;
                    SpriteFullDTO spriteFullDTO = SpriteFullDTO.builder().build();
                    ob.getSpriteFullDTOS().add(spriteFullDTO);
                    for (int i = 0; i < v103; i++) {
                        var v23 = v102 == 0;
                        var v109 = 1;
                        SpritePartDTO spritePartDTO = SpritePartDTO
                                .builder()
                                .build();
                        spriteFullDTO.getHorizontais().add(spritePartDTO);
                        if (!v23) {
                            do {
                                var v25Mais12 = dat.getInt();
                                spritePartDTO.getSprite().add(v25Mais12);
                                spritePartDTO.getBuffer().add(getBuffer(v25Mais12));
                                v109++;
                            } while (v109 <= v102);
                            v22 = i;
                            v21 = v47;
                        }
                        ++v22;
                    }
                    v47 = ++v21;
                } while (v21 < 2);
            }
        }

        File basePath = new File("spritesObj");
        if (!basePath.exists() || basePath.isDirectory()) {
            basePath.mkdir();
        }

        allObjects
                .parallelStream()
                .collect(Collectors.groupingBy(SpriteDTO::getType))
                .forEach((key, value) -> {
                    File typePath = new File(basePath, String.valueOf(key));
                    if (!typePath.exists() || typePath.isDirectory()) {
                        typePath.mkdir();
                    }
                    value
                            .parallelStream()
                            .filter(ob -> !ob.getActionByPositions().isEmpty())
                            .forEach(ob -> ob
                                    .getActionByPositions()
                                    .parallelStream()
                                    .collect(Collectors.groupingBy(ActionByPosition::getActionEnum))
                                    .forEach((key2, value2) -> value2
                                            .parallelStream()
                                            .collect(Collectors.groupingBy(ActionByPosition::getDirectionEnum))
                                            .forEach((key1, value1) -> {
                                                var listOfList = value1
                                                        .parallelStream()
                                                        .map(spriteValue -> spriteValue
                                                                .getSpriteFullDTOS()
                                                                .parallelStream()
                                                                .filter(vertical -> !vertical.getHorizontais().isEmpty())
                                                                .map(vertical -> {
                                                                    var allHorizontals = vertical
                                                                            .getHorizontais()
                                                                            .parallelStream()
                                                                            .map(horizontal -> {
                                                                                var buffers = horizontal
                                                                                        .getBuffer()
                                                                                        .parallelStream()
                                                                                        .map(buff -> {
                                                                                            if (buff.length == 0) {
                                                                                                return zeroImage;
                                                                                            }
                                                                                            var im = getBufferedImage(buff);
                                                                                            if (im == null) {
                                                                                                return zeroImage;
                                                                                            }
                                                                                            return im;
                                                                                        })
                                                                                        .toList();

                                                                                return mergeImagesHorizontal(buffers);

                                                                            })
                                                                            .toList();

                                                                    return mergeImagesVertical(allHorizontals);
                                                                })
                                                                .toList())
                                                        .flatMap(List::stream)
                                                        .toList();

                                                if (!listOfList.isEmpty()) {

                                                    File objectPath = new File(typePath, String.valueOf(ob.getId()));
                                                    if (!objectPath.exists() || objectPath.isDirectory()) {
                                                        objectPath.mkdir();
                                                    }

                                                    File actionPath = new File(objectPath, String.valueOf(key2));
                                                    if (!actionPath.exists() || actionPath.isDirectory()) {
                                                        actionPath.mkdir();
                                                    }

                                                    File directionPath = new File(actionPath, String.valueOf(key1));
                                                    if (!directionPath.exists() || directionPath.isDirectory()) {
                                                        directionPath.mkdir();
                                                    }
                                                    createImageGrid(listOfList, directionPath);
                                                    createGif(listOfList, directionPath.getPath() + "/output.gif", 150);

                                                }

                                            })));

                });

    }

    public static void createGif(List<BufferedImage> imageList, String outputFilePath, int delayBetweenFrames) {
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(outputFilePath);
        encoder.setTransparent(new Color(0, 0, 0, 0));
        encoder.setDelay(delayBetweenFrames);
        encoder.setRepeat(0);
        encoder.setQuality(100);

        for (BufferedImage image : imageList) {
            encoder.addFrame(image);
        }

        encoder.finish();
    }

    public static void createImageGrid(List<BufferedImage> images, File file) {
        int count = images.size();
        int size = (int) Math.ceil(Math.sqrt(count));
        int width = images.get(0).getWidth();
        int height = images.get(0).getHeight();

        BufferedImage combined = new BufferedImage(size * width, size * height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = combined.createGraphics();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int index = i * size + j;
                if (index < count) {
                    BufferedImage image = images.get(index);
                    int xPos = j * width;
                    int yPos = i * height;
                    g2d.drawImage(image, xPos, yPos, null);
                }
            }
        }

        g2d.dispose();

        try {
            ImageIO.write(combined, "PNG", new File(file, "combined.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static BufferedImage getBufferedImage(byte[] spriteData) {
        try {
            return toBufferedImage(spriteData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] getBuffer(Integer spriteId) {
        if (spriteId == 0) {
            return new byte[]{};
        }
        Integer position = positions.get(spriteId);
        spr.position(position);
        int size = spr.getInt();
        byte[] spriteData = new byte[size];
        spr.get(spriteData);
        return spriteData;
    }

    public static BufferedImage mergeImagesVertical(List<BufferedImage> images) {
        int width = images.get(0).getWidth();
        int totalHeight = images.stream().mapToInt(BufferedImage::getHeight).sum();

        BufferedImage combinedImage = new BufferedImage(width, totalHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combinedImage.getGraphics();

        int height = 0;
        for (BufferedImage image : images) {
            g.drawImage(image, 0, height, null);
            height += image.getHeight();
        }

        return combinedImage;
    }

    public static BufferedImage mergeImagesHorizontal(List<BufferedImage> images) {
        int height = images.get(0).getHeight();
        int totalWidth = images.stream().mapToInt(BufferedImage::getWidth).sum();

        BufferedImage combinedImage = new BufferedImage(totalWidth, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combinedImage.getGraphics();

        int width = 0;
        for (BufferedImage image : images) {
            g.drawImage(image, width, 0, null);
            width += image.getWidth();
        }

        return combinedImage;
    }

    private static BufferedImage toBufferedImage(byte[] imageInByte) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageInByte);
        return ImageIO.read(bais);
    }

    public static void sprExtractor(boolean export) throws IOException {
        spr = ByteBuffer.wrap(Files.readAllBytes(Paths.get(properties.getProperty("bloodstonePath") + "\\data\\bloodstone.spr")));
        var version = spr.getInt();
        var spritesCount = spr.getInt();
        for (int i = 0; i < spritesCount; i++) {
            var id = spr.getInt();
            var offset = spr.getInt();
            positions.put(id, offset);
        }

        if (export) {

            File basePath = new File("sprites64");
            if (!basePath.exists() || basePath.isDirectory()) {
                basePath.mkdir();
            }
            positions
                    .entrySet()
                    .forEach(keySet -> {
                        try {
                            spr.position(keySet.getValue());
                            int size = spr.getInt();
                            byte[] spriteData = new byte[size];
                            spr.get(spriteData);

                            ByteArrayInputStream bis = new ByteArrayInputStream(spriteData);
                            BufferedImage image = ImageIO.read(bis);
                            bis.close();

                            File outputFile = new File("sprites64\\" + keySet.getKey() + ".png");
                            ImageIO.write(image, "png", outputFile);
                        } catch (Exception e) {
                            System.out.println("Error in sprite: " + keySet.getKey());
                        }
                    });
        }
    }
}