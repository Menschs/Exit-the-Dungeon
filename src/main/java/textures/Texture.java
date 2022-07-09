package textures;

import main.ExitTheDungeon;
import util.filelib.FileConfiguration;
import util.filelib.exceptions.FileIncompatibleException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class Texture {

    private static final TreeMap<String, Integer> imgIndexes = new TreeMap<>();
    private static final TreeMap<String, Texture> textures = new TreeMap<>();
    private static final Random random = new Random();
    private static boolean stopTicks = false;
    private final List<File> images = new ArrayList<>();
    private final List<Subscriber> subscribers = new ArrayList<>();
    private final TreeMap<String, List<Integer>> imgCluster = new TreeMap<>();
    private final TreeMap<String, Integer> statedAnimations = new TreeMap<>();
    private final File configFile;
    private final FileConfiguration config;
    private boolean animated = false;
    private boolean randomized = false;
    private int delay = 500;
    private TextureType type;
    private TreeMap<String, String> attributes;

    public Texture(String path) {
        configFile = new File(path);
        FileConfiguration temp;
        try {
            temp = new FileConfiguration(configFile, false);
        } catch (FileIncompatibleException | IOException e) {
            e.printStackTrace();
            config = null;
            return;
        }
        config = temp;
        init();
    }

    public Texture(File file) {
        configFile = file;
        FileConfiguration temp;
        try {
            temp = new FileConfiguration(configFile, false);
        } catch (FileIncompatibleException | IOException e) {
            e.printStackTrace();
            config = null;
            return;
        }
        config = temp;
        init();
    }

    public void subscribe(Subscriber s) {
        subscribers.add(s);
    }

    private void init() {
        textures.put(configFile.getPath().replace(".polly", "").replace("\\", ".").replace("assets.etd.textures.", ""), this);
        attributes = config.getLoaded();

        //attributes.forEach((s, s2) -> System.out.println(s + " " + s2));

        type = TextureType.get(attributes.getOrDefault("type", "unknown"));
        if(type == TextureType.unknown) return;

        for (int i = 0; i < attributes.size(); i++) {
            String attribute = "texture." + i;
            if(attributes.containsKey(attribute)) {
                String path = attributes.get(attribute);
                path += (path.endsWith(".png")) ? "" : ".png";
                File image = new File(configFile.getPath().replace(configFile.getName(), "") + "/" + path);
                images.add(image);
            } else if(i == 0) {
                type = TextureType.unknown;
                return;
            }
        }

        animated = attributes.containsKey("animation.delay");
        if(animated) {
            delay = Integer.parseInt(attributes.get("animation.delay"));
        }

        String attributeKey = "states.";
        attributes.forEach((s, s2) -> {
            if(s.startsWith(attributeKey)) {
                s = s.replace(attributeKey, "");
                String key = s.substring(0, s.indexOf("."));
                List<Integer> cluster = imgCluster.getOrDefault(key, new ArrayList<>());
                cluster.add(Integer.valueOf(s2));
                if(imgCluster.containsKey(key)) imgCluster.replace(key, cluster);
                    else {
                        imgCluster.put(key, cluster);
                        if(animated) statedAnimations.put(key, cluster.get(0));
                    }
            }
        });
        if(imgCluster.isEmpty()) {
            List<Integer> defaultCluster = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                defaultCluster.add(i);
            }
            imgCluster.put("default", defaultCluster);
        }

        if(attributes.containsKey("random")) {
            randomized = Boolean.parseBoolean(attributes.get("random"));
        }

        imgCluster.forEach((s, integers) -> {
            integers.forEach(integer -> {
                String path = images.get(integer).getPath();
                loadTexture(path);
            });
        });

        if(!animated) return;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stopTicks) {
                    statedAnimations.forEach((s, integer) -> {
                        integer++;
                        List<Integer> cluster = imgCluster.get(s);
                        if(integer > cluster.get(cluster.size() - 1)) integer = cluster.get(0);
                        statedAnimations.replace(s, integer);
                    });
                    subscribers.forEach(subscriber -> {
                        subscriber.update(get(subscriber.getState()));
                    });
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        });
        t.start();
    }

    public static Integer getTextureIndex(String texture) {
        return imgIndexes.get(texture);
    }

    public static void loadTexture(String path) {
        if(imgIndexes.containsKey(path)) return;
        ExitTheDungeon.getInstance().drawer.addTexture(path);
        imgIndexes.put(path, imgIndexes.size());
    }

    private static void load(File f) {
        if(f.isDirectory()) {
            for (File file : f.listFiles()) {
                load(file);
            }
        } else {
            if(FileConfiguration.isCompatible(f)) {
                new Texture(f);
            }
        }
    }

    public int get(String state) {
        if(!imgCluster.containsKey(state)) return 0;
        if(randomized) {
            int index = random.nextInt(imgCluster.get(state).size());
            System.out.println(index);
            return getTexture(index);
        }
        return getTexture(statedAnimations.get(state));
    }

    public int getTexture(int index) {
        return getTextureIndex(images.get(index).getPath());
    }

    public static Texture getTextureObject(String name) {
        return textures.get(name);
    }

    public static void loadTextures() {
        File f = new File("assets/etd/textures");
        if(!f.isDirectory()) return;

        for (File file : f.listFiles()) {
            load(file);
        }
        imgIndexes.forEach((s, integer) -> System.out.println(s + " " + integer));
    }

    public List<String> getStates() {
        return new ArrayList<>(imgCluster.keySet());
    }

    public boolean isAnimated() {
        return animated;
    }
}