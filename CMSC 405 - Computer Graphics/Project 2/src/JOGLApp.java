/*
 * Project 2
 *
 * Name: Luis Moreno
 * Class: CMSC 405 7380 Computer Graphics (2232)
 * Date: May 8, 2023
 */
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>This class displays a 3D animated scene in a window.</p>
 * <p>JOGL is used to draw the shapes and apply transformations.</p>
 * <p>A timer is used to loop the animation over 36 frames.</p>
 * @author Luis Moreno
 */
public class JOGLApp extends GLJPanel implements GLEventListener, ActionListener {

    public static void main(String[] args) {
        JFrame window = new JFrame("Project 2");
        window.setPreferredSize(new Dimension(640, 480));
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JOGLApp panel = new JOGLApp();
        window.setContentPane(panel);

        window.pack();
        window.setVisible(true);
    }

    private GLJPanel display;
    private Timer animationTimer;
    private int frameNumber = 0;

    public JOGLApp() {
        setPreferredSize(new Dimension(640, 480));
        GLCapabilities caps = new GLCapabilities(null);
        display = new GLJPanel(caps);
        display.addGLEventListener(this);
        setLayout(new BorderLayout());
        add(display, BorderLayout.CENTER);

        startAnimation();
    }

    private double rotate;
    private double scale;
    private double translate;

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1, 1, -1, 1, -2, 2);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        // loop transforms over 36 frames
        if (frameNumber == 0) {
            rotate = 0;
            scale = 0.5;
            translate = -0.5;
        } else if (frameNumber < 18) {
            rotate += 10;
            scale += 0.03;
            translate += 0.02;
        } else if (frameNumber > 18) {
            rotate += 10;
            scale -= 0.03;
            translate -= 0.02;
        }

        gl.glPushMatrix();
        gl.glTranslated(-0.5, 0.7, 0);
        gl.glRotated(rotate, 0.5, 1, 0);
        pyramid(gl, 0.25);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(0.6, -0.6, 0);
        gl.glScaled(scale, scale, scale);
        sphere(gl, 1, 0, 0, 0.15, 15, 15);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(translate, translate, 0);
        gl.glRotated(30, 0.5, 0.5, 0);
        cube(gl, 0.3);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(0.7, 0.7, 0);
        gl.glRotated(rotate, 1, 0, 0);
        triangularPrism(gl, 0.3);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(15, 1, 0, 0);
        gl.glTranslated(0, 0.3, -0.5);
        gl.glRotated(rotate / 2, 0, 0, 1);
        star(gl, 0.7);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(0.3, -0.6, 0);
        gl.glScaled(scale, scale, scale);
        gl.glRotated(rotate, 0.2, 1, 0.2);
        octagedron(gl, 0.3);
        gl.glPopMatrix();

        gl.glFlush();
    }

    /* ****************** Drawing methods ********************* */

    private void octagedron(GL2 gl, double size) {
        gl.glPushMatrix();
        gl.glScaled(size, size, size);

        pTriangle(gl, 0, 0, 1);

        gl.glPushMatrix();
        gl.glRotated(90, 0, 1, 0);
        pTriangle(gl, 0, 0.5, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(-90, 0, 1, 0);
        pTriangle(gl, 0, 0.5, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(180, 0, 1, 0);
        pTriangle(gl, 0, 0, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(180, 1, 0, 0);
        gl.glTranslated(0, 1, 0);
        pTriangle(gl, 0, 0.5, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(90, 0, 1, 0);
        gl.glRotated(180, 1, 0, 0);
        gl.glTranslated(0, 1, 0);
        pTriangle(gl, 0, 0, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(-90, 0, 1, 0);
        gl.glRotated(180, 1, 0, 0);
        gl.glTranslated(0, 1, 0);
        pTriangle(gl, 0, 0, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(180, 0, 1, 0);
        gl.glRotated(180, 1, 0, 0);
        gl.glTranslated(0, 1, 0);
        pTriangle(gl, 0, 0.5, 1);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    private void star(GL2 gl, double size) {
        gl.glPushMatrix();
        gl.glScaled(size, size, size);

        sTriangle(gl, 1, 0, 1);

        gl.glPushMatrix();
        gl.glScaled(-1, 1, 1);
        sTriangle(gl, 1, 0.5, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glScaled(1, -1, 1);
        sTriangle(gl, 1, 0.5, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glScaled(-1, -1, 1);
        sTriangle(gl, 1, 0, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(45, 0, 0, 1);
        gl.glScaled(-1, 1, 1);
        gl.glRotated(-45, 0, 0, 1);
        sTriangle(gl, 1, 0.5, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(45, 0, 0, 1);
        gl.glScaled(-1, 1, 1);
        gl.glRotated(-45, 0, 0, 1);
        gl.glScaled(-1, 1, 1);
        sTriangle(gl, 1, 0, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(-45, 0, 0, 1);
        gl.glScaled(-1, 1, 1);
        gl.glRotated(45, 0, 0, 1);
        sTriangle(gl, 1, 0.5, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(-45, 0, 0, 1);
        gl.glScaled(-1, 1, 1);
        gl.glRotated(45, 0, 0, 1);
        gl.glScaled(-1, 1, 1);
        sTriangle(gl, 1, 0, 1);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    private void triangularPrism(GL2 gl, double size) {
        gl.glPushMatrix();
        gl.glScaled(size, size, size);

        // front face
        gl.glPushMatrix();
        gl.glTranslated(0, 0, 0.5);
        triangle(gl, 0, 1, 1);
        gl.glPopMatrix();

        // back face
        gl.glPushMatrix();
        gl.glRotated(180, 0, 1, 0);
        gl.glTranslated(0, 0, 0.5);
        triangle(gl, 1, 1, 0);
        gl.glPopMatrix();

        // base
        gl.glPushMatrix();
        gl.glRotated(90, 1, 0, 0);
        square(gl, 0, 0, 1);
        gl.glPopMatrix();

        // right face
        gl.glPushMatrix();
        gl.glRotated(90, 0, 1, 0);
        tSquare(gl, 1, 0, 0);
        gl.glPopMatrix();

        // left face
        gl.glPushMatrix();
        gl.glRotated(-90, 0, 1, 0);
        tSquare(gl, 1, 0, 1);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    private void cube(GL2 gl, double size) {
        gl.glPushMatrix();
        gl.glScaled(size, size, size);

        square(gl, 1, 1, 0);

        gl.glPushMatrix();
        gl.glRotated(90, 0, 1, 0);
        square(gl, 0.9, 0.9, 0);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(-90, 0, 1, 0);
        square(gl, 0.8, 0.8, 0);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(180, 0, 1, 0);
        square(gl, 0.7, 0.7, 0);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(90, 1, 0, 0);
        square(gl, 0.6, 0.6, 0);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(-90, 1, 0, 0);
        square(gl, 0.5, 0.5, 0);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    private void sphere(GL2 gl, double r, double g, double b,
                        double radius, int slices, int stacks) {

        gl.glPushMatrix();
        gl.glColor3d(r, g, b);

        double sliceStep = 2 * Math.PI / slices;
        double stacksStep = Math.PI / stacks;

        for (int i = 0; i < slices; i++) {
            double theta1 = i * sliceStep;
            double theta2 = (i + 1) * sliceStep;

            for (int j = 0; j < stacks; j++) {
                double phi1 = j * stacksStep - Math.PI / 2;
                double phi2 = (j + 1) * stacksStep - Math.PI / 2;

                double x1 = radius * Math.cos(phi1) * Math.cos(theta1);
                double y1 = radius * Math.sin(phi1);
                double z1 = radius * Math.cos(phi1) * Math.sin(theta1);

                double x2 = radius * Math.cos(phi2) * Math.cos(theta1);
                double y2 = radius * Math.sin(phi2);
                double z2 = radius * Math.cos(phi2) * Math.sin(theta1);

                double x3 = radius * Math.cos(phi2) * Math.cos(theta2);
                double y3 = radius * Math.sin(phi2);
                double z3 = radius * Math.cos(phi2) * Math.sin(theta2);

                double x4 = radius * Math.cos(phi1) * Math.cos(theta2);
                double y4 = radius * Math.sin(phi1);
                double z4 = radius * Math.cos(phi1) * Math.sin(theta2);

                gl.glBegin(GL2.GL_TRIANGLES);

                gl.glNormal3d(x1, y1, z1);
                gl.glVertex3d(x1, y1, z1);

                gl.glNormal3d(x2, y2, z2);
                gl.glVertex3d(x2, y2, z2);

                gl.glNormal3d(x3, y3, z3);
                gl.glVertex3d(x3, y3, z3);

                gl.glNormal3d(x1, y1, z1);
                gl.glVertex3d(x1, y1, z1);

                gl.glNormal3d(x3, y3, z3);
                gl.glVertex3d(x3, y3, z3);

                gl.glNormal3d(x4, y4, z4);
                gl.glVertex3d(x4, y4, z4);

                gl.glEnd();
            }
        }

        gl.glPopMatrix();
    }

    private void pyramid(GL2 gl, double size) {
        gl.glPushMatrix();
        gl.glScaled(size, size, size);

        // base
        gl.glPushMatrix();
        gl.glRotated(90, 1, 0, 0);
        gl.glTranslated(0, 1, 0);
        square(gl, 1, 0, 0);
        gl.glPopMatrix();

        // front face
        gl.glPushMatrix();
        gl.glTranslated(0, 0, 1);
        pTriangle(gl, 0, 1, 0);
        gl.glPopMatrix();

        // left face
        gl.glPushMatrix();
        gl.glRotated(-90, 0, 1, 0);
        gl.glTranslated(1, 0, 0);
        pTriangle(gl, 0, 0, 1);
        gl.glPopMatrix();

        // right face
        gl.glPushMatrix();
        gl.glRotated(90, 0, 1, 0);
        gl.glTranslated(-1, 0, 0);
        pTriangle(gl, 1, 0, 1);
        gl.glPopMatrix();

        // back face
        gl.glPushMatrix();
        gl.glRotated(180, 0, 1, 0);
        gl.glTranslated(0, 0, -1);
        pTriangle(gl, 1, 1, 0);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    private void square(GL2 gl, double r, double g, double b) {
        gl.glColor3d(r, g, b);
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex3d(-0.5, -0.5, 0.5);
        gl.glVertex3d(0.5, -0.5, 0.5);
        gl.glVertex3d(0.5, 0.5, 0.5);
        gl.glVertex3d(-0.5, 0.5, 0.5);
        gl.glEnd();
    }

    // squares used for the triangular prism required different vertices than square()
    private void tSquare(GL2 gl, double r, double g, double b) {
        gl.glColor3d(r, g, b);
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex3d(-0.5, -0.5, 0.5);
        gl.glVertex3d(0.5, -0.5, 0.5);
        gl.glVertex3d(0.5, 0.5, 0);
        gl.glVertex3d(-0.5, 0.5, 0);
        gl.glEnd();
    }

    // triangles used for the pyramid and octahedron required different vertices than triangle()
    private void pTriangle(GL2 gl, double r, double g, double b) {
        gl.glColor3d(r, g, b);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3d(-0.5, -0.5, 0.5);
        gl.glVertex3d(0, 0.5, 0);
        gl.glVertex3d(0.5, -0.5, 0.5);
        gl.glEnd();
    }

    // triangles for the star required different vertices than triangle()
    private void sTriangle(GL2 gl, double r, double g, double b) {
        gl.glColor3d(r, g, b);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3d(0, 0, -0.5);
        gl.glVertex3d(-0.15, 0.15, 0);
        gl.glVertex3d(0, 0.75, 0);
        gl.glEnd();
    }

    private void triangle(GL2 gl, double r, double g, double b) {
        gl.glColor3d(r, g, b);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3d(-0.5, -0.5, 0);
        gl.glVertex3d(0, 0.5, 0);
        gl.glVertex3d(0.5, -0.5, 0);
        gl.glEnd();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 0);
        gl.glEnable(GL2.GL_DEPTH_TEST);

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);       // OpenGL will make all normal vectors into unit normals
        gl.glEnable(GL2.GL_COLOR_MATERIAL);  // Material ambient and diffuse colors can be set by glColor*
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    /* ********************** Animation ************************ */

    private boolean animating;

    private void updateFrame() {        // loop through 30 frames
        if (frameNumber < 36)
            frameNumber++;
        else
            frameNumber = 0;
    }

    /**
     * <p>Timer object is created and run on a delay of 100ms.</p>
     * <p>The frame number is updated for the animation after every 100ms interval.</p>
     * @author Luis Moreno
     */
    public void startAnimation() {
        if (!animating) {
            if (animationTimer == null) {
                animationTimer = new Timer(100, this);
            }
            animationTimer.start();
            animating = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateFrame();
        display.repaint();
    }
}
