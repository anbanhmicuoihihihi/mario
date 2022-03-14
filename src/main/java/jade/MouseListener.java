package jade;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
  private static MouseListener mouseListener;
  private double scrollX, scrollY;
  private double posX, posY, lastX, lastY, worldX, worldY, lastWorldX, lastWorldY;
  private boolean mouseButtonPressed[] = new boolean[3];
  private boolean isDragging;

  private int mouseButtonDown = 0;

  private Vector2f gameViewportPos = new Vector2f();
  private Vector2f gameViewportSize = new Vector2f();

  private MouseListener() {
    this.scrollX = 0L;
    this.scrollY = 0L;
    this.posX = 0L;
    this.posY = 0L;
    this.lastX = 0L;
    this.lastY = 0L;
  }

  public static void endFrame() {
    get().scrollY = 0.0;
    get().scrollX = 0.0;
  }

  public static void clear() {
    get().scrollX = 0.0;
    get().scrollY = 0.0;
    get().posX = 0.0;
    get().posY = 0.0;
    get().lastX = 0.0;
    get().lastY = 0.0;
    get().mouseButtonDown = 0;
    get().isDragging = false;
    Arrays.fill(get().mouseButtonPressed, false);
  }

  public static MouseListener get() {
    if (MouseListener.mouseListener == null) {
      MouseListener.mouseListener = new MouseListener();
    }

    return MouseListener.mouseListener;
  }

  public static void mousePosCallback(long window, double pos_x, double pos_y) {
//    if (!Window.getImguiLayer().getGameViewWindow().getWantCaptureMouse()) {
//      clear();
//    }
    if (get().mouseButtonDown > 0) {
      get().isDragging = true;
    }

    get().lastX = get().posX;
    get().lastY = get().posY;
    get().posX = pos_x;
    get().posY = pos_y;
  }

  public static void mouseButtonCallback(long window, int button, int action, int mods) {
    if (action == GLFW_PRESS) {
      get().mouseButtonDown++;

      if (button < get().mouseButtonPressed.length) {
        get().mouseButtonPressed[button] = true;
      }
    } else if (action == GLFW_RELEASE) {
      get().mouseButtonDown--;

      if (button < get().mouseButtonPressed.length) {
        get().mouseButtonPressed[button] = false;
        get().isDragging = false;
      }
    }
  }

  public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
    get().scrollX = xOffset;
    get().scrollY = yOffset;
  }

  public static float getX() {
    return (float) get().posX;
  }

  public static float getY() {
    return (float) get().posY;
  }

  public static float getWorldDx() {
    return (float) (get().lastWorldX - get().worldX);
  }

  public static float getWorldDy() {
    return (float) (get().lastWorldY - get().worldY);
  }

  public static float getScrollX() {
    return (float) get().scrollX;
  }

  public static float getScrollY() {
    return (float) get().scrollY;
  }

  public static boolean isDragging() {
    return get().isDragging;
  }

  public static boolean mouseButtonDown(int button) {
    if (button < get().mouseButtonPressed.length) {
      return get().mouseButtonPressed[button];
    } else {
      return false;
    }
  }

//  public static float getWorldX() {
//    return getWorld().x;
//  }
//
//  public static float getWorldY() {
//    return getWorld().y;
//  }

//  public static Vector2f getWorld() {
//    float currentX = getX() - get().gameViewportPos.x;
//    currentX = (2.0f * (currentX / get().gameViewportSize.x)) - 1.0f;
//    float currentY = (getY() - get().gameViewportPos.y);
//    currentY = (2.0f * (1.0f - (currentY / get().gameViewportSize.y))) - 1;
//
//    Camera camera = Window.getScene().camera();
//    Vector4f tmp = new Vector4f(currentX, currentY, 0, 1);
//    Matrix4f inverseView = new Matrix4f(camera.getInverseView());
//    Matrix4f inverseProjection = new Matrix4f(camera.getInverseProjection());
//    tmp.mul(inverseView.mul(inverseProjection));
//    return new Vector2f(tmp.x, tmp.y);
//  }

//  public static Vector2f screenToWorld(Vector2f screenCoords) {
//    Vector2f normalizedScreenCords =
//        new Vector2f(screenCoords.x / Window.getWidth(), screenCoords.y / Window.getHeight());
//    normalizedScreenCords.mul(2.0f).sub(new Vector2f(1.0f, 1.0f));
//    Camera camera = Window.getScene().camera();
//    Vector4f tmp = new Vector4f(normalizedScreenCords.x, normalizedScreenCords.y, 0, 1);
//    Matrix4f inverseView = new Matrix4f(camera.getInverseView());
//    Matrix4f inverseProjection = new Matrix4f(camera.getInverseProjection());
//    tmp.mul(inverseView.mul(inverseProjection));
//    return new Vector2f(tmp.x, tmp.y);
//  }

//  public static Vector2f worldToScreen(Vector2f worldCoords) {
//    Camera camera = Window.getScene().camera();
//    Vector4f ndcSpacePos = new Vector4f(worldCoords.x, worldCoords.y, 0, 1);
//    Matrix4f view = new Matrix4f(camera.getViewMatrix());
//    Matrix4f projection = new Matrix4f(camera.getProjectionMatrix());
//    ndcSpacePos.mul(projection.mul(view));
//    Vector2f windowSpace = new Vector2f(ndcSpacePos.x, ndcSpacePos.y).mul(1.0f / ndcSpacePos.w);
//    windowSpace.add(new Vector2f(1.0f, 1.0f)).mul(0.5f);
//    windowSpace.mul(new Vector2f(Window.getWidth(), Window.getHeight()));
//
//    return windowSpace;
//  }

  public static float getScreenX() {
    return getScreen().x;
  }

  public static float getScreenY() {
    return getScreen().y;
  }

  public static Vector2f getScreen() {
    float currentX = getX() - get().gameViewportPos.x;
    currentX = (currentX / get().gameViewportSize.x) * 3840.0f;
    float currentY = (getY() - get().gameViewportPos.y);
    currentY = (1.0f - (currentY / get().gameViewportSize.y)) * 2160.0f;
    return new Vector2f(currentX, currentY);
  }

  public static void setGameViewportPos(Vector2f gameViewportPos) {
    get().gameViewportPos.set(gameViewportPos);
  }

  public static void setGameViewportSize(Vector2f gameViewportSize) {
    get().gameViewportSize.set(gameViewportSize);
  }
}
