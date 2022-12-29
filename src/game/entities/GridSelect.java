package game.entities;

import engine.core.Globals;
import engine.importer.EntityImporter;
import engine.importer.MeshImporter;
import engine.structure.Entity;
import engine.types.ImageTexture;

public class GridSelect extends Entity {
    @Override
    public void start() {
        super.id = 1;
        meshInstance = EntityImporter.loadMeshFromFile("src/resources/meshes/misc/quad.fbx", Globals.entityShader, this);
        meshInstance.meshes.get(0).material.texture = new ImageTexture("src/resources/textures/gameplay/grid_select.png");
        meshInstance.meshes.get(0).material.strength = 10;
        meshInstance.transform.position.y = 0.001f;
    }

    @Override
    public void update() {

    }

    @Override
    public void end() {

    }
}
