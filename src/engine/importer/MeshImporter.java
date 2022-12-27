package engine.importer;

import engine.graphics.Mesh;
import engine.graphics.MeshInstance;
import engine.graphics.Shader;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.util.ArrayList;

public class MeshImporter {
    private static ArrayList<Float> positions = new ArrayList<>();
    private static ArrayList<Float> uvs = new ArrayList<>();
    private static ArrayList<Float> normals = new ArrayList<>();
    private static ArrayList<Integer> indices = new ArrayList<>();
    public static MeshInstance loadMeshFromFile(String path, Shader shader) {
        AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate);

        PointerBuffer buffer = scene.mMeshes();

        MeshInstance meshes = new MeshInstance(shader);

        for(int i = 0; i < buffer.limit(); i++) {

            AIMesh m = AIMesh.create(buffer.get(i));
            processMesh(m);

            Mesh mesh = new Mesh();
            mesh.vertices = toFloatArray(positions);
            mesh.indices = toIntArray(indices);
            mesh.normals = toFloatArray(normals);

            positions = new ArrayList<>();
            indices = new ArrayList<>();
            uvs = new ArrayList<>();
            normals = new ArrayList<>();

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

        for (int i = 0; i < u.limit(); i++) {
            AIVector3D vector = u.get(i);

            uvs.add(vector.x());
            uvs.add(vector.y());
        }

        AIVector3D.Buffer norms = mesh.mNormals();

        for (int i = 0; i < norms.limit(); i++) {
            AIVector3D vector = norms.get(i);

            normals.add(vector.x());
            normals.add(vector.y());
            normals.add(vector.z());
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
