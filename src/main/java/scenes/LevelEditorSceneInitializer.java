package scenes;

import jade.GameObject;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorSceneInitializer extends SceneInitializer {

  private GameObject levelEditorStuff;

  private float[] vertexArray = {
    100.5f, 0.5f, 0.0f,   1.0f, 0.0f, 0.0f, 1.0f,
    0.5f, 100.5f, 0.0f,   0.0f, 1.0f, 0.0f, 1.0f,
    100.5f, 100.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
    0.5f, 0.5f, 0.0f,     1.0f, 1.0f, 0.0f, 1.0f
  };

  private int[] elementArray = {
    2, 1, 0,
    0, 1, 3
  };

  private int vaoID, vboID, eboID;

  public LevelEditorSceneInitializer() {
    System.out.println("LevelEditorSceneInitializer");
  }

  @Override
  public void init(Scene scene) {
//    vaoID = glGenVertexArrays();
//    glBindVertexArray(vaoID);
//
//    FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
//    vertexBuffer.put(vertexArray).flip();
//
//    vboID = glGenBuffers();
//    glBindBuffer(GL_ARRAY_BUFFER, vboID);
//    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
//
//    IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
//    elementBuffer.put(elementArray).flip();
//
//    eboID = glGenBuffers();
//    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
//    glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
//
//    int positionsSize = 3, colorSize = 4, floatSizeBytes = 4;
//    int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
//    glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
//    glEnableVertexAttribArray(0);
//
//    glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
//    glEnableVertexAttribArray(1);
  }

  @Override
  public void loadResources(Scene scene) {
//    AssetPool.getShader("assets/shaders/default.glsl");
  }

  @Override
  public void imgui() {

  }
}
