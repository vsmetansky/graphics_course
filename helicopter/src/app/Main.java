package app;

import javax.vecmath.*;

import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;

import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.utils.image.TextureLoader;

import javax.swing.JFrame;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;

import java.awt.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class Main extends JFrame {
    private Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Canvas3D myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        SimpleUniverse universe = new SimpleUniverse(myCanvas3D);
        universe.getViewingPlatform().setNominalViewingTransform();

        createSceneGraph(universe);
        addLight(universe);

        OrbitBehavior orbit = new OrbitBehavior(myCanvas3D);
        orbit.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE));
        universe.getViewingPlatform().setViewPlatformBehavior(orbit);

        setTitle("Helicopter");
        setSize(1000, 1000);
        getContentPane().add("Center", myCanvas3D);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    private void createSceneGraph(SimpleUniverse su) {
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        Scene widowScene = null;
        try {
            widowScene = f.load("models/helicopter.obj");
        } catch (Exception e) {
            System.out.println("File loading failed:" + e);
        }

        Transform3D scaling = new Transform3D();
        scaling.setScale(0.2);
        Transform3D tfHelicopter = new Transform3D();
        tfHelicopter.rotX(-Math.PI / 3);
        tfHelicopter.mul(scaling);
        TransformGroup tgHelicopter = new TransformGroup(tfHelicopter);
        TransformGroup sceneGroup = new TransformGroup();

        Hashtable helicopterNamedObjects = widowScene.getNamedObjects();

        Shape3D bigPropeller = (Shape3D) helicopterNamedObjects.get("big_propeller");
        Shape3D smallPropeller = (Shape3D) helicopterNamedObjects.get("small_propeller");
        Shape3D decal = (Shape3D) helicopterNamedObjects.get("decal");
        Shape3D glass = (Shape3D) helicopterNamedObjects.get("glass");
        Shape3D main_ = (Shape3D) helicopterNamedObjects.get("main_");
        Shape3D alpha = (Shape3D) helicopterNamedObjects.get("alpha");
        Shape3D missileGl = (Shape3D) helicopterNamedObjects.get("missile_gl");
        Shape3D missile1 = (Shape3D) helicopterNamedObjects.get("missile_1");
        Shape3D body = (Shape3D) helicopterNamedObjects.get("main_body_");

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        TransformGroup transformGroup = new TransformGroup();
        Transform3D bodyTransform = new Transform3D();
        bodyTransform.rotY(Math.PI * 0.85);
        transformGroup.setTransform(bodyTransform);
        transformGroup.addChild(body.cloneTree());


        TransformGroup bigPropellerGr = new TransformGroup();
        TransformGroup smallPropellerGr = new TransformGroup();
        TransformGroup missileGlGr = new TransformGroup();
        TransformGroup missile1Gr = new TransformGroup();
        TransformGroup decalGr = new TransformGroup();
        TransformGroup glassGr = new TransformGroup();
        TransformGroup mainGr = new TransformGroup();
        TransformGroup alphaGr = new TransformGroup();

        TransformGroup smallPropellerTrGr = new TransformGroup();
        smallPropellerTrGr.addChild(smallPropellerGr);
        Transform3D smallPropellerTranslation = new Transform3D();
        smallPropellerTranslation.setTranslation(new Vector3f(0.4f, 0, -1.7f));
        smallPropellerTrGr.setTransform(smallPropellerTranslation);

        bigPropellerGr.setTransform(bodyTransform);
        smallPropellerGr.setTransform(bodyTransform);
        missile1Gr.setTransform(bodyTransform);
        missileGlGr.setTransform(bodyTransform);
        decalGr.setTransform(bodyTransform);
        glassGr.setTransform(bodyTransform);
        mainGr.setTransform(bodyTransform);
        alphaGr.setTransform(bodyTransform);


        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.EAST);

        Background background = new Background(new Color3f(Color.RED));
        BoundingSphere boundsBack = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(boundsBack);
        BranchGroup back = new BranchGroup();
        back.addChild(background);
        su.addBranchGraph(back);

        bigPropellerGr.addChild(bigPropeller.cloneTree());
        smallPropellerGr.addChild(smallPropeller.cloneTree());
        missileGlGr.addChild(missileGl.cloneTree());
        missile1Gr.addChild(missile1.cloneTree());
        decalGr.addChild(decal.cloneTree());
        glassGr.addChild(glass.cloneTree());
        mainGr.addChild(main_.cloneTree());
        alphaGr.addChild(alpha.cloneTree());

        BoundingSphere bounds = new BoundingSphere(new Point3d(120.0, 250.0, 100.0), Double.MAX_VALUE);
        BranchGroup theScene = new BranchGroup();

        Transform3D tCrawl = new Transform3D();
        Transform3D tCrawl1 = new Transform3D();
        tCrawl.rotY(-90D);
        tCrawl1.rotX(-90D);
        long crawlTime = 10000;
        Alpha crawlAlpha = new Alpha(10,
                Alpha.INCREASING_ENABLE,
                0,
                0, crawlTime, 0, 0, 0, 0, 0);
        float crawlDistance = 3.0f;
        PositionInterpolator posICrawl = new PositionInterpolator(crawlAlpha,
                sceneGroup, tCrawl, -9.0f, crawlDistance);

        long crawlTime1 = 30000;
        Alpha crawlAlpha1 = new Alpha(1,
                Alpha.INCREASING_ENABLE,
                crawlTime / 10,
                0, crawlTime1, 0, 0, 0, 0, 0);
        float crawlDistance1 = 15.0f;
        PositionInterpolator posICrawl1 = new PositionInterpolator(crawlAlpha1,
                sceneGroup, tCrawl1, -20.0f, crawlDistance1);


        BoundingSphere bs = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE);
        posICrawl.setSchedulingBounds(bs);
        posICrawl1.setSchedulingBounds(bs);
        sceneGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        sceneGroup.addChild(posICrawl);

        int timeStart = 1;
        int timeRotationHour = 400;

        RotationInterpolator bigPropellerRotation = getBigRotation(bigPropellerGr, bounds, timeStart, timeRotationHour);
        RotationInterpolator smallPropellerRotation = getSmallRotation(smallPropellerGr, bounds, timeStart, timeRotationHour);

        bigPropellerGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        smallPropellerGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        bigPropellerGr.addChild(bigPropellerRotation);
        smallPropellerGr.addChild(smallPropellerRotation);

        sceneGroup.addChild(transformGroup);
        sceneGroup.addChild(bigPropellerGr);
        sceneGroup.addChild(smallPropellerTrGr);
        sceneGroup.addChild(missileGlGr);
        sceneGroup.addChild(missile1Gr);
        sceneGroup.addChild(decalGr);
        sceneGroup.addChild(glassGr);
        sceneGroup.addChild(mainGr);
        sceneGroup.addChild(alphaGr);
        tgHelicopter.addChild(sceneGroup);
        theScene.addChild(tgHelicopter);

        su.addBranchGraph(theScene);
    }


    private void addLight(SimpleUniverse su) {
        BranchGroup bgLight = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Color3f lightColour1 = new Color3f(0.5f, 1.0f, 1.0f);
        Vector3f lightDir1 = new Vector3f(-1.0f, 0.0f, -0.5f);
        DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
        light1.setInfluencingBounds(bounds);
        bgLight.addChild(light1);
        su.addBranchGraph(bgLight);
    }

    private RotationInterpolator getBigRotation(TransformGroup propeller, BoundingSphere bounds, int timeStart, int timeRotationHour) {
        Transform3D bigPropellerRotationAxis = new Transform3D();
        bigPropellerRotationAxis.rotY(Math.PI);
        Alpha bigPropellerRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, timeStart, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        RotationInterpolator bigPropellerRotation = new RotationInterpolator(bigPropellerRotationAlpha, propeller,
                bigPropellerRotationAxis, (float) Math.PI * 4, 0.0f);
        bigPropellerRotation.setSchedulingBounds(bounds);
        return bigPropellerRotation;
    }

    private RotationInterpolator getSmallRotation(TransformGroup propeller, BoundingSphere bounds, int timeStart, int timeRotationHour) {
        Transform3D smallPropellerRotationAxis = new Transform3D();
        smallPropellerRotationAxis.rotX(Math.PI / 2);
        Alpha smallPropellerRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        RotationInterpolator smallPropellerRotation = new RotationInterpolator(smallPropellerRotationAlpha, propeller,
                smallPropellerRotationAxis, (float) Math.PI * 6, 0.0f);
        smallPropellerRotation.setSchedulingBounds(bounds);
        return smallPropellerRotation;
    }
}



