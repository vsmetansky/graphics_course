package app;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.awt.*;

import static java.awt.Color.lightGray;

public class Swing {
    private TransformGroup mainTransformGroup;
    private final Transform3D mainTransform3D = new Transform3D();
    private float angle = 0;

    public BranchGroup createSceneGraph() {
        BranchGroup root = new BranchGroup();

        mainTransformGroup = new TransformGroup();
        mainTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        buildObject();
        root.addChild(mainTransformGroup);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Color3f lightColor = new Color3f(1.0f, 1f, 1f);
        Vector3f lightDirection = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(bounds);
        root.addChild(light);

        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        root.addChild(ambientLightNode);

        Background background = new Background(new Color3f(0.01f, 0.01f, 0.2f));
        background.setApplicationBounds(bounds);
        root.addChild(background);

        return root;
    }

    private void buildObject() {
        drawTop();
        drawLegs();
        drawRopes();
        drawSaddle();
    }

    public void rotate() {
        mainTransform3D.rotY(angle);
        angle += 0.1;
        mainTransformGroup.setTransform(mainTransform3D);
    }

    public void cyllinder(double rotX, double rotY, double rotZ, float x, float y, float z,
                          float radius, float length, Color c) {
        Appearance ap = new Appearance();

        Color3f color = new Color3f(c);
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        ap.setMaterial(new Material(color, black, color, white, 1f));

        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

        Cylinder cylinder = new Cylinder(radius, length, primflags, ap);

        Transform3D transform3D = new Transform3D();
        TransformGroup transformGroup = new TransformGroup();
        if (rotX != 0) transform3D.rotX(rotX);
        if (rotY != 0) transform3D.rotY(rotY);
        if (rotZ != 0) transform3D.rotZ(rotZ);
        transform3D.setTranslation(new Vector3f(x, y, z));
        transformGroup.setTransform(transform3D);
        transformGroup.addChild(cylinder);
        mainTransformGroup.addChild(transformGroup);
    }

    public void plate(double rotX, double rotY, double rotZ, float x, float y, float z,
                      float size1, float size2, float size3, Color c) {

        Appearance ap = new Appearance();
        Color3f color = new Color3f(c);
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

        ap.setMaterial(new Material(color, black, color, white, 1f));

        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

        Box box = new Box(size1, size2, size3, primflags, ap);

        Transform3D transform3D = new Transform3D();
        TransformGroup transformGroup = new TransformGroup();
        if (rotX != 0) transform3D.rotX(rotX);
        if (rotY != 0) transform3D.rotY(rotY);
        if (rotZ != 0) transform3D.rotZ(rotZ);
        transform3D.setTranslation(new Vector3f(x, y, z));
        transformGroup.setTransform(transform3D);
        transformGroup.addChild(box);
        mainTransformGroup.addChild(transformGroup);
    }

    private void drawSaddle() {
        plate(0, 0, Math.PI / 2, 0, -0.3f, 0, 0.01f, 0.2f, 0.06f, Color.BLUE);
    }

    private void drawRopes() {
        cyllinder(0, 0, 0, +0.16f, 0.1f, 0, 0.005f, 0.8f, Color.GREEN);
        cyllinder(0, 0, 0, -0.16f, 0.1f, 0, 0.005f, 0.8f, Color.GREEN);
    }

    private void drawLegs() {
        cyllinder(0, 0, 0, +0.5f, 0.02f, 0, 0.02f, 1f, Color.RED);
        cyllinder(0, 0, 0, -0.5f, 0.02f, 0, 0.02f, 1f, Color.RED);
    }

    private void drawTop() {
        cyllinder(0, 0, Math.PI / 2, 0f, 0.5f, 0, 0.02f, 1f, Color.RED);
    }
}
