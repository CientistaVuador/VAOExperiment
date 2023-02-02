/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vaoexperiment;

import static org.lwjgl.opengl.GL33C.*;

/**
 *
 * @author Cien
 */
public class VBO {

    public static final float[][] TYPES = new float[][]{
        {
            0.0f, 0.5f, 0.0f, 1f, 0f, 0f,
            -0.5f, -0.5f, 0.0f, 0f, 1f, 0f,
            0.5f, -0.5f, 0.0f, 0f, 0f, 1f,
        },
        {
            0.0f, 0.5f, 0.0f, 0f, 0f, 1f,
            -0.5f, -0.5f, 0.0f, 1f, 0f, 0f,
            0.5f, -0.5f, 0.0f, 0f, 1f, 0f
        },
        {
            0.0f, 0.5f, 0.0f, 0f, 1f, 0f,
            -0.5f, -0.5f, 0.0f, 0f, 0f, 1f,
            0.5f, -0.5f, 0.0f, 1f, 0f, 0f
        }
    };

    public static final int[] VBOS = new int[]{
        glGenBuffers(),
        glGenBuffers(),
        glGenBuffers()
    };

    static {
        for (int i = 0; i < VBOS.length; i++) {
            glBindBuffer(GL_ARRAY_BUFFER, VBOS[i]);
            glBufferData(GL_ARRAY_BUFFER, TYPES[i], GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    private VBO() {

    }
}
