package engine.importer;

import engine.graphics.Mesh;
import engine.graphics.MasterMesh;
import engine.graphics.Shader;
import engine.structure.Entity;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.util.ArrayList;

import static org.lwjgl.assimp.Assimp.AI_MATKEY_COLOR_DIFFUSE;

public class EntityImporter {
    private static ArrayList<Float> positions = new ArrayList<>();
    private static ArrayList<Float> uvs = new ArrayList<>();
    private static ArrayList<Float> normals = new ArrayList<>();
    private static ArrayList<Float> colors = new ArrayList<>();

    private static ArrayList<Integer> indices = new ArrayList<>();
    public static MasterMesh loadMeshFromFile(String path, Shader shader, Entity entity) {
        AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate);

        PointerBuffer buffer = scene.mMeshes();
        PointerBuffer mbuffer = scene.mMaterials();

        MasterMesh meshes = new MasterMesh(shader, entity);

        for(int i = 0; i < buffer.limit(); i++) {
            AIMesh m = AIMesh.create(buffer.get(i));
            processMesh(m);

            Mesh mesh = new Mesh();
            mesh.vertices = toFloatArray(positions);
            mesh.indices = toIntArray(indices);
            mesh.normals = toFloatArray(normals);
            if(uvs.size() != 0) {
                mesh.uvs = toFloatArray(uvs);
            }
            if(colors.size() != 0) {
                mesh.colors = toFloatArray(colors);
            }

            AIMaterial mat = AIMaterial.create(mbuffer.get(m.mMaterialIndex()));
            AIColor4D color = AIColor4D.create();
            Assimp.aiGetMaterialColor(mat, AI_MATKEY_COLOR_DIFFUSE, 0, 0, color);
            mesh.material.color = new Vector3f(color.r(), color.g(), color.b());

            positions = new ArrayList<>();
            indices = new ArrayList<>();
            uvs = new ArrayList<>();
            normals = new ArrayList<>();
            colors = new ArrayList<>();

            meshes.addMesh(mesh);
        }

        return meshes;
    }

    public static float[] toFloatArray(ArrayList<Float> a){
        float[] res = new float[a.size()];
        for(int i = 0; i < res.length; i++){
            res[i] = a.get(i);
        }
        return res;
    }
    public static int[] toIntArray(ArrayList<Integer> a){
        int[] res = new int[a.size()];
        for(int i = 0; i < res.length; i++){
            res[i] = a.get(i);
        }
        return res;
    }

    private static void processMesh(AIMesh mesh) {
        AIVector3D.Buffer vectors = mesh.mVertices();

        for (int i = 0; i < vectors.limit(); i++) {
            AIVector3D vector = vectors.get(i);

            positions.add(vector.x());
            positions.add(vector.y());
            positions.add(vector.z());
        }

        AIVector3D.Buffer u = mesh.mTextureCoords(0);

        if(u != null) {
            for (int i = 0; i < u.limit(); i++) {
                AIVector3D vector = u.get(i);

                uvs.add(vector.x());
                uvs.add(vector.y());
            }
        }

        AIVector3D.Buffer norms = mesh.mNormals();

        for (int i = 0; i < norms.limit(); i++) {
            AIVector3D vector = norms.get(i);

            normals.add(vector.x());
            normals.add(vector.y());
            normals.add(vector.z());
        }

        AIColor4D.Buffer c = mesh.mColors(0);

        if(c != null) {
            for (int i = 0; i < c.limit(); i++) {
                AIColor4D vector = c.get(i);

                colors.add(vector.r());
                colors.add(vector.g());
                colors.add(vector.b());
            }
        }

        AIFace.Buffer inds = mesh.mFaces();

        for (int i = 0; i < inds.limit(); i++) {
            AIFace vector = inds.get(i);

            for (int j = 0; j < vector.mIndices().limit(); j++) {
                indices.add(vector.mIndices().get(j));
            }
        }
    }
}
