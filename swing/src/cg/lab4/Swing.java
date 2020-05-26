package cg.lab4;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class Swing {
	private TransformGroup mainTransformGroup;
	private Transform3D mainTransform3D = new Transform3D();
	private float angle = 0;

	public BranchGroup createSceneGraph() {
		BranchGroup objRoot = new BranchGroup();

		mainTransformGroup = new TransformGroup();
		mainTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		buildObject();
		objRoot.addChild(mainTransformGroup);

		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
		Color3f light1Color = new Color3f(1.0f, 1f, 1f);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
		light1.setInfluencingBounds(bounds);
		objRoot.addChild(light1);

		Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
		AmbientLight ambientLightNode = new AmbientLight(ambientColor);
		ambientLightNode.setInfluencingBounds(bounds);
		objRoot.addChild(ambientLightNode);

		return objRoot;
	}

	private void buildObject() {
		// Top
		buildMetalPos(0, 0, Math.PI/2, 0f, 0.5f, 0, 0.02f, 1f);
		// Supports
		buildMetalPos(+Math.PI/8, 0, 0, +0.5f, 0.02f, -0.2f, 0.02f, 1f);
		buildMetalPos(+Math.PI/8, 0, 0, -0.5f, 0.02f, -0.2f, 0.02f, 1f);
		buildMetalPos(-Math.PI/8, 0, 0, +0.5f, 0.02f, +0.2f, 0.02f, 1f);
		buildMetalPos(-Math.PI/8, 0, 0, -0.5f, 0.02f, +0.2f, 0.02f, 1f);

		// Brackets
		buildMetalPos(0, 0, 0, +0.16f, 0.185f, 0, 0.005f, 0.6f);
		buildMetalPos(0, 0, 0, -0.16f, 0.185f, 0, 0.005f, 0.6f);

		// Saddle
		buildPlacticPlate(0, 0, Math.PI/2, 0, -0.25f, 0, 0.01f, 0.12f, 0.06f);
		buildPlacticPlate(0, 0, Math.PI/2 + Math.PI/2.5, +0.14f, -0.18f, 0, 0.01f, 0.07f, 0.06f);
		buildPlacticPlate(0, 0, Math.PI/2 - Math.PI/2.5, -0.14f, -0.18f, 0, 0.01f, 0.07f, 0.06f);
	}

	public void rotate() {
		mainTransform3D.rotY(angle);
		angle += 0.05;
		mainTransformGroup.setTransform(mainTransform3D);
	}

	public void buildMetalPos(double rotX, double rotY, double rotZ, float x, float y, float z,
	                              float radius, float length) {
		Appearance ap = new Appearance();

		Color3f emissive = new Color3f(0f, 0f, 0f);
		Color3f ambient = new Color3f(0.1f, 0.1f, 0.1f);
		Color3f diffuse = new Color3f(.4f, .4f, .4f);
		Color3f specular = new Color3f(.4f, .4f, .4f);
		ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1f));

		int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

		Cylinder cylinder = new Cylinder(radius, length, primflags, ap);

		Transform3D transform3D = new Transform3D();
		TransformGroup transformGroup = new TransformGroup();
		if(rotX != 0) transform3D.rotX(rotX);
		if(rotY != 0) transform3D.rotY(rotY);
		if(rotZ != 0) transform3D.rotZ(rotZ);
		transform3D.setTranslation(new Vector3f(x, y, z));
		transformGroup.setTransform(transform3D);
		transformGroup.addChild(cylinder);
		mainTransformGroup.addChild(transformGroup);
	}

	public void buildPlacticPlate(double rotX, double rotY, double rotZ, float x, float y, float z,
	                          float size1, float size2, float size3) {

		Appearance ap = new Appearance();

		Color3f emissive = new Color3f(0.00f, 0.00f, 0.00f);
		Color3f ambient = new Color3f(0.05f, 0.05f, 0.05f);
		Color3f diffuse = new Color3f(0.15f, 0.15f, .15f);
		Color3f specular = new Color3f(0.2f, 0.2f, 0.2f);
		ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));

		int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

		Box box = new Box(size1, size2, size3, primflags, ap);

		Transform3D transform3D = new Transform3D();
		TransformGroup transformGroup = new TransformGroup();
		if(rotX != 0) transform3D.rotX(rotX);
		if(rotY != 0) transform3D.rotY(rotY);
		if(rotZ != 0) transform3D.rotZ(rotZ);
		transform3D.setTranslation(new Vector3f(x, y, z));
		transformGroup.setTransform(transform3D);
		transformGroup.addChild(box);
		mainTransformGroup.addChild(transformGroup);
	}
}
