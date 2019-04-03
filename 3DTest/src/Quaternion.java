

/**
 *
 * @author Mike
 *
public class Quaternion {
    public float w;
    public Vector3 point = new Vector3(0, 0, 0);
    
    public Quaternion(Vector3 point, float angle) {
        w = (float)Math.cos(angle / 2);
        this.point.x = point.x * (float) Math.sin(angle / 2);
        this.point.y = point.y * (float) Math.sin(angle / 2);
        this.point.z = point.z * (float) Math.sin(angle / 2);
    }
    
    public Quaternion(float x, float y, float z, float angle) {
        w = (float)Math.cos(angle / 2);
        point.x = x * (float) Math.sin(angle / 2);
        point.y = y * (float) Math.sin(angle / 2);
        point.z = z * (float) Math.sin(angle / 2);
    }
    
    public Quaternion multiply(Quaternion q2) {
        Quaternion output = new Quaternion(0, 0, 0, 0);
        output.w = (w * q2.w - point.x * q2.point.x - point.y * q2.point.y - point.z * q2.point.z);
        output.point.x = (w * q2.point.x + point.x * q2.w + point.y * q2.point.z - point.z * q2.point.y);
        output.point.y = (w * q2.point.y + point.x * q2.point.z + point.y * q2.w - point.z * q2.point.x);
        output.point.z = (w * q2.point.z + point.x * q2.point.y + point.y * q2.point.x - point.z * q2.w);
        
        return output;
    }
    
    public Vector3 matrixMultiply(Vector3 point) {
        return point;
    }
    
    public Quaternion inverse() {
        float d = w * w + point.x * point.x + point.y * point.y + point.z * point.z;
        return new Quaternion(-point.x / d, -point.y / d, -point.z / d, w / d);
    }
}*/
