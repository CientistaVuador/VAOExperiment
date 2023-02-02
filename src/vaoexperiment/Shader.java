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
public class Shader {
    
    public static final String VERTEX_SHADER = 
            """
            #version 330 core
            
            layout (location = 0) in vec3 vertexPosition;
            layout (location = 1) in vec3 vertexColor;
            
            out vec3 inout_vertexColor;
            
            void main() {
                inout_vertexColor = vertexColor;
                gl_Position = vec4(vertexPosition, 1.0);
            }
            """;
    
    public static final String FRAGMENT_SHADER =
            """
            #version 330 core
            
            layout (location = 0) out vec4 fragColor;
            
            in vec3 inout_vertexColor;
            
            void main() {
                fragColor = vec4(inout_vertexColor, 1.0);
            }
            """;
    
    public static final int SHADER_PROGRAM;
    
    static {
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, VERTEX_SHADER);
        glCompileShader(vertexShader);
        
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, FRAGMENT_SHADER);
        glCompileShader(fragmentShader);
        
        int program = glCreateProgram();
        
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        
        glLinkProgram(program);
        
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        
        SHADER_PROGRAM = program;
    }
    
}
