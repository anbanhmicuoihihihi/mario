package scenes;

import jade.Camera;
import jade.Window;
import org.joml.Vector2f;

public class Scene {
//  private Renderer renderer;
  private Camera camera;
//  private boolean isRunning;
//  private List<GameObject> gameObjects;
//  private List<GameObject> pendingObjects;
//  private Physics2D physics2D;
  private SceneInitializer sceneInitializer;

  public Scene(SceneInitializer sceneInitializer) {
    this.sceneInitializer = sceneInitializer;
//    this.physics2D = new Physics2D();
//    this.renderer = new Renderer();
//    this.gameObjects = new ArrayList<>();
//    this.pendingObjects = new ArrayList<>();
//    this.isRunning = false;
  }

  public void update(float dt) {
    Window.get().r = Math.max(Window.get().r - 0.01f, 0);
    Window.get().g = Math.max(Window.get().r - 0.01f, 0);
    Window.get().b = Math.max(Window.get().r - 0.01f, 0);
    Window.get().a = Math.max(Window.get().r - 0.01f, 0);


//    this.camera.adjustProjection();
//    this.physics2D.update(dt);
//
//    for (int i=0; i < gameObjects.size(); i++) {
//      GameObject go = gameObjects.get(i);
//      go.update(dt);
//
//      if (go.isDead()) {
//        gameObjects.remove(i);
//        this.renderer.destroyGameObject(go);
//        this.physics2D.destroyGameObject(go);
//        i--;
//      }
//    }
//
//    for (GameObject go : pendingObjects) {
//      gameObjects.add(go);
//      go.start();
//      this.renderer.add(go);
//      this.physics2D.add(go);
//    }
//    pendingObjects.clear();
  }

  public void init() {
    this.camera = new Camera(new Vector2f(0, 0));
    this.sceneInitializer.loadResources(this);
    this.sceneInitializer.init(this);
  }

  public void start() {
//    for (int i=0; i < gameObjects.size(); i++) {
//      GameObject go = gameObjects.get(i);
//      go.start();
//      this.renderer.add(go);
//      this.physics2D.add(go);
//    }
//    isRunning = true;
  }

  public void destroy() {
//    for (GameObject go : gameObjects) {
//      go.destroy();
//    }
  }

//  public void render() {
//    this.renderer.render();
//  }

  public Camera camera() {
    return this.camera;
  }

//  public void imgui() {
//    this.sceneInitializer.imgui();
//  }

//  public GameObject createGameObject(String name) {
//    GameObject go = new GameObject(name);
//    go.addComponent(new Transform());
//    go.transform = go.getComponent(Transform.class);
//    return go;
//  }

  public void save() {
//    Gson gson = new GsonBuilder()
//            .setPrettyPrinting()
//            .registerTypeAdapter(Component.class, new ComponentDeserializer())
//            .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
//            .enableComplexMapKeySerialization()
//            .create();
//
//    try {
//      FileWriter writer = new FileWriter("level.txt");
//      List<GameObject> objsToSerialize = new ArrayList<>();
//      for (GameObject obj : this.gameObjects) {
//        if (obj.doSerialization()) {
//          objsToSerialize.add(obj);
//        }
//      }
//      writer.write(gson.toJson(objsToSerialize));
//      writer.close();
//    } catch(IOException e) {
//      e.printStackTrace();
//    }
  }

  public void load() {
//    Gson gson = new GsonBuilder()
//            .setPrettyPrinting()
//            .registerTypeAdapter(Component.class, new ComponentDeserializer())
//            .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
//            .enableComplexMapKeySerialization()
//            .create();
//
//    String inFile = "";
//    try {
//      inFile = new String(Files.readAllBytes(Paths.get("level.txt")));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    if (!inFile.equals("")) {
//      int maxGoId = -1;
//      int maxCompId = -1;
//      GameObject[] objs = gson.fromJson(inFile, GameObject[].class);
//      for (int i=0; i < objs.length; i++) {
//        addGameObjectToScene(objs[i]);
//
//        for (Component c : objs[i].getAllComponents()) {
//          if (c.getUid() > maxCompId) {
//            maxCompId = c.getUid();
//          }
//        }
//        if (objs[i].getUid() > maxGoId) {
//          maxGoId = objs[i].getUid();
//        }
//      }
//
//      maxGoId++;
//      maxCompId++;
//      GameObject.init(maxGoId);
//      Component.init(maxCompId);
//    }
  }
}
