package jade;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import renderer.Renderer;
import renderer.Shader;
import scenes.LevelEditorSceneInitializer;
import scenes.LevelSceneInitializer;
import scenes.Scene;
import scenes.SceneInitializer;
import util.AssetPool;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
  private int width, height;
  private String title;
  private long glfwWindow;
  private ImGuiLayer imguiLayer;

  private static Window window = null;
  private static Scene currentScene;

  public float r, g, b, a;
  private boolean fadeToBlack;

  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.title = "Mario";
    this.glfwWindow = 0L;
    this.r = 1;
    this.g = 1;
    this.b = 1;
    this.a = 1;
  }

  public static void changeScene(SceneInitializer sceneInitializer) {
    if (currentScene != null) {
      currentScene.destroy();
    }

//    getImguiLayer().getPropertiesWindow().setActiveGameObject(null);
    currentScene = new Scene(sceneInitializer);
//    currentScene.load();
    currentScene.init();
//    currentScene.start();
  }

  public static Window get() {
    if (Window.window == null) {
      Window.window = new Window();
    }

    return Window.window;
  }

  public void run() {
    System.out.println("Hello LWJGL " + Version.getVersion() + "!");

    init();
    loop();

    // Free the window callbacks and destroy the window
    glfwFreeCallbacks(glfwWindow);
    glfwDestroyWindow(glfwWindow);

    // Terminate GLFW and free the error callback
    glfwTerminate();
    glfwSetErrorCallback(null).free();
  }

  private void init() {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

    // Configure GLFW
    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

    // Create the window
    glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
    if (glfwWindow == NULL) throw new RuntimeException("Failed to create the GLFW window");

    glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
    glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
    glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(
        glfwWindow,
        (window, key, scancode, action, mods) -> {
          if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
          else KeyListener.keyCallback(window, key, scancode, action, mods);
        });

    // Get the thread stack and push a new frame
    try (MemoryStack stack = stackPush()) {
      IntBuffer pWidth = stack.mallocInt(1); // int*
      IntBuffer pHeight = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(glfwWindow, pWidth, pHeight);

      // Get the resolution of the primary monitor
      GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      // Center the window
      glfwSetWindowPos(
              glfwWindow, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
    } // the stack frame is popped automatically

    // Make the OpenGL context current
    glfwMakeContextCurrent(glfwWindow);
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(glfwWindow);

    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities();

    Window.changeScene(new LevelEditorSceneInitializer());
  }

  private void loop() {
    float beginTime = (float)glfwGetTime();
    float endTime;
    float dt = -1.0f;

    Shader defaultShader = AssetPool.getShader("assets/shaders/default.glsl");
    Shader pickingShader = AssetPool.getShader("assets/shaders/pickingShader.glsl");

    // Run the rendering loop until the user has attempted to close
    // the window or has pressed the ESCAPE key.
    while (!glfwWindowShouldClose(glfwWindow)) {
      // Set the clear color
      glClearColor(r, g, b, a);
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

      if (fadeToBlack) {
        if (dt >= 0) {
          Renderer.bindShader(defaultShader);
          Renderer.getBoundShader().update();
//          if (runtimePlaying) {
            currentScene.update(dt);
//          } else {
//            currentScene.editorUpdate(dt);
//          }
//          currentScene.render();
//          DebugDraw.draw();
        }
      }

      glfwSwapBuffers(glfwWindow); // swap the color buffers

      // Poll for window events. The key callback above will only be
      // invoked during this call.
      glfwPollEvents();

      if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
        fadeToBlack = true;
      }

      KeyListener.endFrame();
      MouseListener.endFrame();

      endTime = (float)glfwGetTime();
      dt = endTime - beginTime;
      beginTime = endTime;
    }
  }

  public static ImGuiLayer getImguiLayer() {
    return get().imguiLayer;
  }
}
