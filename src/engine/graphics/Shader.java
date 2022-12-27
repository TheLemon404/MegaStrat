package engine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private String vertexSource, fragmentSource, path;
    public int programId, vertexId, fragmentId;

    public Shader(String path){
        this.path = path;
    }

    public void compile(){
        read();

        vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexId, vertexSource);
        glCompileShader(vertexId);
        if(glGetShaderi(vertexId, GL_COMPILE_STATUS) == GL_FALSE){
            System.out.println(glGetShaderInfoLog(vertexId));
            System.out.println("could not compile vertex shader");
            System.exit(-1);
        }

        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentId, fragmentSource);
        glCompileShader(fragmentId);
        if(glGetShaderi(fragmentId, GL_COMPILE_STATUS) == GL_FALSE){
            System.out.println(glGetShaderInfoLog(fragmentId));
            System.out.println("could not compile fragment shader");
            System.exit(-1);
        }

        programId = glCreateProgram();
        glAttachShader(programId, vertexId);
        glAttachShader(programId, fragmentId);
        glLinkProgram(programId);
        glValidateProgram(programId);

        bindAttributes();
    }

    private void bindAttributes(){
        bindAttribute(0, "a_position");
        bindAttribute(1, "a_uv");
        bindAttribute(2, "a_normal");
        bindAttribute(3, "a_color");
    }

    public void bindAttribute(int attribute, String var){
        glBindAttribLocation(programId, attribute, var);
    }

    public void uploadUniform(int val, String var){
        int location = glGetUniformLocation(programId, var);
        glUniform1i(location, val);
    }

    public void uploadUniform(float val, String var){
        int location = glGetUniformLocation(programId, var);
        glUniform1f(location, val);
    }

    public void uploadUniform(Vector2f val, String var){
        int location = glGetUniformLocation(programId, var);
        glUniform2f(location, val.x, val.y);
    }

    private FloatBuffer storeMatrix(Matrix4f matrix){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        buffer.put(matrix.m00());
        buffer.put(matrix.m01());
        buffer.put(matrix.m02());
        buffer.put(matrix.m03());

        buffer.put(matrix.m10());
        buffer.put(matrix.m11());
        buffer.put(matrix.m12());
        buffer.put(matrix.m13());

        buffer.put(matrix.m20());
        buffer.put(matrix.m21());
        buffer.put(matrix.m22());
        buffer.put(matrix.m23());

        buffer.put(matrix.m30());
        buffer.put(matrix.m31());
        buffer.put(matrix.m32());
        buffer.put(matrix.m33());

        buffer.flip();

        return buffer;
    }

    public void uploadUniform(Vector3f val, String var){
        int location = glGetUniformLocation(programId, var);
        glUniform3f(location, val.x, val.y, val.z);
    }

    public void uploadUniform(Vector3f[] vals, String var){
        int location = glGetUniformLocation(programId, var);
        float[] val = new float[vals.length * 3];
        int index = 0;
        for(int i = 0; i < val.length; i++){
            if(i % 3 == 0){
                val[i - 2] = vals[index].x;
                val[i - 1] = vals[index].y;
                val[i] = vals[index].z;
                index++;
            }
        }
        glUniform3fv(location, val);
    }

    public void uploadUniform(Matrix4f val, String var)
    {
        int location = glGetUniformLocation(programId, var);
        FloatBuffer buffer = storeMatrix(val);
        glUniformMatrix4fv(location, false, buffer);
    }


    public void read(){
        StringBuilder vb = new StringBuilder();
        StringBuilder fb = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            boolean f = false;
            boolean skip;
            while((line = reader.readLine()) != null){
                skip = false;

                if(line.contains("#split")){
                    f = true;
                    skip = true;
                }

                if(!f && !skip){
                    vb.append(line).append("\n");
                }
                else if(f && !skip){
                    fb.append(line).append("\n");
                }
            }

            vertexSource = vb.toString();
            fragmentSource = fb.toString();

            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void bind(){
        glUseProgram(programId);
    }

    public void unbind(){
        glUseProgram(0);
    }

    public void delete(){
        glDeleteShader(vertexId);
        glDeleteProgram(fragmentId);
        glDeleteProgram(programId);
    }

}
