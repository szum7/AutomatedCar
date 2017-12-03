package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.environment.detector.WindscreenCamera;
import hu.oe.nik.szfmv.environment.model.WorldObject;
import hu.oe.nik.szfmv.environment.model.WorldObjectCollection;
import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.factory.SensorObjectFactory;
import hu.oe.nik.szfmv.automatedcar.powertrainsystem.PorscheCharacteristics;
import hu.oe.nik.szfmv.environment.factory.ImageResource;
import hu.oe.nik.szfmv.environment.factory.WorldObjectFactory;
import hu.oe.nik.szfmv.environment.model.World;
import hu.oe.nik.szfmv.environment.object.Sensor;
import hu.oe.nik.szfmv.environment.util.ModelShape;
import hu.oe.nik.szfmv.environment.xml.XmlObject;
import hu.oe.nik.szfmv.environment.xml.XmlParser;
import hu.oe.nik.szfmv.visualisation.CourseDisplay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import hu.oe.nik.szfmv.environment.model.WorldObject;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    private static final int CYCLE_PERIOD = 40;
    private static CourseDisplay userInterFace;
    private static AutomatedCar playerCar;
    private static World world;

    public static void main(String[] args) {
        init();

        mainLoop();
    }

    private static void init() {
        logger.info("starting...");
        userInterFace = new CourseDisplay();

        // create the world
        List<XmlObject> xmlObjects = readXmlObjects();

        createWorld();

        populateWorld(xmlObjects, world);

        userInterFace.init(world);

        playerCar = new AutomatedCar(2500, 1500, 0f, ImageResource.getImageOf(ImageResource.WHITE_CAR_2_NAME),
                (int) new PorscheCharacteristics().getWeightOfCar(), ModelShape.RECTANGULAR);

        // add WindscreenCamera to the world
        WindscreenCamera windscreenCamera = new WindscreenCamera(playerCar, world.getWorldObjects());
        world.addObjectToWorld(playerCar);

        addSensorsToWorld(playerCar, world);
    }

    private static void addSensorsToWorld(AutomatedCar playerCar, World world) {
        List<Sensor> sensors = SensorObjectFactory.createAllSensor(playerCar);

        for (Sensor item : sensors) {
            world.addObjectToWorld(item);
        }
    }

    private static void mainLoop() {
        while (true) {
            try {
                playerCar.drive();
                userInterFace.refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private static void populateWorld(List<XmlObject> xmlObjects, World world) {
        for (XmlObject item : xmlObjects) {
            try {
                world.addObjectToWorld(WorldObjectFactory.createWorldObject(item));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<XmlObject> readXmlObjects() {
        List<XmlObject> xmlObjects = new ArrayList<>();
        try {
            xmlObjects = XmlParser.parse("test_world.xml");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return xmlObjects;
    }

    private static void createWorld() {
        int[] dimensions;
        try {
            dimensions = XmlParser.getWorldDimensions("test_world.xml");
            world = new World(dimensions[0], dimensions[1]);
        } catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

