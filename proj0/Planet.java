public class Planet {
    // G constant
    public static final double G = 6.67e-11; 
    // Its current x position
    public double xxPos;
    // Its current y position
    public double yyPos;
    // Its current velocity in the x direction
    public double xxVel;
    // Its current velocity in the y direction
    public double yyVel;
    // Its mass
    public double mass;
    //: The name of the file that corresponds
    // to the image that depicts the planet (for example, jupiter.gif)
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet other) {
        double xD = other.xxPos - xxPos;
        double yD = other.yyPos - yyPos;
        double r = Math.sqrt(xD*xD + yD*yD);
        return r;
    }

    public double calcForceExertedBy(Planet other){
        double distance = calcDistance(other);
        double force = (G * mass * other.mass) / (distance * distance);
        return force;
    }

    public double calcForceExertedByX(Planet other) {
        double xD = other.xxPos - xxPos;
        double r = calcDistance(other);
        double force = calcForceExertedBy(other);
        return (xD / r) * force;
    }
    public double calcForceExertedByY(Planet other) {
        double yD = other.yyPos - yyPos;
        double r = calcDistance(other);
        double force = calcForceExertedBy(other);
        return (yD / r) * force;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double netForceByX = 0;
        for (Planet p : allPlanets) {
            if (!p.equals(this)) {
                netForceByX += calcForceExertedByX(p);
            }
        }
        return netForceByX;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double netForceByY = 0;
        for (Planet p : allPlanets) {
            if (!p.equals(this)) {
                netForceByY += calcForceExertedByY(p);
            }
        }
        return netForceByY;
    }
}
