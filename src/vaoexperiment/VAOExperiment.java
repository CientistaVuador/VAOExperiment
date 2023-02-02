/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vaoexperiment;

import static org.lwjgl.opengl.GL33C.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 *
 * @author Cien
 */
public class VAOExperiment {

    private static int currentVbo = 0;
    private static int[] staticVaos;
    private static int dynamicVao;
    private static int singleUseVao;
    private static int singleUseVbo;
    private static int currentTest = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        glfwInit();

        glfwWindowHint(GLFW_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_VERSION_MINOR, 3);

        long window = glfwCreateWindow(800, 600, "Experiment", NULL, NULL);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glfwSwapInterval(0);

        staticVaos = new int[VBO.VBOS.length];
        for (int i = 0; i < staticVaos.length; i++) {
            int vao = glGenVertexArrays();
            glBindBuffer(GL_ARRAY_BUFFER, VBO.VBOS[i]);
            glBindVertexArray(vao);
            setupVao();
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
            staticVaos[i] = vao;
        }

        dynamicVao = glGenVertexArrays();

        int frames = 0;
        long next = System.currentTimeMillis() + 1000;

        long[] framesStorage = new long[4];

        boolean cycleCompleted = true;
        boolean timeTick = true;

        glUseProgram(Shader.SHADER_PROGRAM);
        while (true) {
            if (glfwWindowShouldClose(window) && cycleCompleted) {
                break;
            }

            if (timeTick) {
                switch (currentTest) {
                    case 0 ->
                        System.out.print("Testing... (Static VAOs)");
                    case 1 ->
                        System.out.print("Testing... (Dynamic VAO)");
                    case 2 ->
                        System.out.print("Testing... (Single-use VAO)");
                    case 3 ->
                        System.out.print("Testing... (Single-use VAO/VBO)");
                }
            }

            glfwPollEvents();

            switch (currentTest) {
                case 0 ->
                    staticVao();
                case 1 ->
                    dynamicVao();
                case 2 ->
                    singleUseVao();
                case 3 ->
                    singleUseVaoVbo();
            }

            glDrawArrays(GL_TRIANGLES, 0, 3);
            glBindVertexArray(0);

            if (currentTest >= 2) {
                glDeleteVertexArrays(singleUseVao);
            }
            if (currentTest == 3) {
                glDeleteBuffers(singleUseVbo);
            }

            glfwSwapBuffers(window);

            currentVbo++;
            if (currentVbo == VBO.VBOS.length) {
                currentVbo = 0;
            }
            frames++;

            if (System.currentTimeMillis() > next) {
                next = System.currentTimeMillis() + 1000;
                System.out.println(", " + frames + " Frames (1s)");
                framesStorage[currentTest] += frames;
                frames = 0;
                currentTest++;
                if (currentTest == 4) {
                    currentTest = 0;
                    cycleCompleted = true;
                }
                timeTick = true;
            } else {
                cycleCompleted = false;
                timeTick = false;
            }
        }
        System.out.println(";");

        System.out.println("Results:");
        
        long biggest = 0;
        for (int i = 0; i < framesStorage.length; i++) {
            if (framesStorage[i] > biggest) {
                biggest = framesStorage[i];
            }
        }
        
        double[] norm = new double[framesStorage.length];
        for (int i = 0; i < norm.length; i++) {
            norm[i] = framesStorage[i] / (double)biggest;
        }
        
        System.out.println("Static VAO: "+String.format("%.4f", norm[0]));
        System.out.println("Dynamic VAO: "+String.format("%.4f", norm[1]));
        System.out.println("Single-use VAO: "+String.format("%.4f", norm[2]));
        System.out.println("Single-use VAO/VBO: "+String.format("%.4f", norm[3]));
        
        glfwTerminate();
    }

    public static void setupVao() {
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, (6 * Float.BYTES), 0);

        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, (6 * Float.BYTES), (3 * Float.BYTES));
    }

    public static void staticVao() {
        glBindVertexArray(staticVaos[currentVbo]);
    }

    public static void dynamicVao() {
        glBindVertexArray(dynamicVao);

        glBindBuffer(GL_ARRAY_BUFFER, VBO.VBOS[currentVbo]);
        setupVao();
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public static void singleUseVao() {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, VBO.VBOS[currentVbo]);
        setupVao();
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        singleUseVao = vao;
    }

    public static void singleUseVaoVbo() {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, VBO.TYPES[currentVbo], GL_STATIC_DRAW);

        setupVao();
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        singleUseVao = vao;
        singleUseVbo = vbo;
    }

}
